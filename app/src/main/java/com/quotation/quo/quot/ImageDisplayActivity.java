package com.quotation.quo.quot;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ImageDisplayActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    int index, page, category;
    String order;
    ArrayList<Image> images;
    ImageView ivImage, ivBack, ivNext;
    CustomTextView tvDescription;
    AdView adView;
    FloatingActionMenu menu;
    Api api;
    com.github.clans.fab.FloatingActionButton fabDownload, fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_desplay);

        Bundle args = getIntent().getExtras();
        images = NewMainActivity.images;
        page = NewMainActivity.page;
        category = NewMainActivity.category;
        order = NewMainActivity.order;
        if (args != null) {
            index = args.getInt("index", 0);
        }
        ivImage = findViewById(R.id.iv_image);
        ivBack = findViewById(R.id.iv_back);
        ivNext = findViewById(R.id.iv_next);
        tvDescription = findViewById(R.id.tv_description);
        adView = findViewById(R.id.adView);
        menu = findViewById(R.id.menu);
        fabDownload = findViewById(R.id.fab_download);
        fabShare = findViewById(R.id.fab_share);

        Log.i("text", tvDescription.getText().toString());

        menu.setClosedOnTouchOutside(true);

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        adView.loadAd(new AdRequest.Builder().build());

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivNext.setEnabled(false);
                index++;
                Intent intent = new Intent(ImageDisplayActivity.this, ImageDisplayActivity.class);
                intent.putExtra("index",index);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ImageDisplayActivity.this.finish();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivBack.setEnabled(false);
                index--;
                Intent intent = new Intent(ImageDisplayActivity.this, ImageDisplayActivity.class);
                intent.putExtra("index",index);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ImageDisplayActivity.this.finish();
            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close(true);
                Picasso.with(ImageDisplayActivity.this)
                        .load(MyApplication.BASE_URL + images.get(index).getImageUrl())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                saveImage(bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Toast.makeText(ImageDisplayActivity.this, R.string.error_downloading_image,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close(true);
                Picasso.with(ImageDisplayActivity.this)
                        .load(MyApplication.BASE_URL + images.get(index).getImageUrl())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                final String path = saveImageForShare(bitmap);
                                if ( ! path.equalsIgnoreCase("cancel")){
                                    shareImage(path);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            deleteImage(path);
                                        }
                                    },120000);
                                }
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Toast.makeText(ImageDisplayActivity.this, R.string.error_sharing_image,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
            }
        });

        checkLoadMore();
    }

    private void checkLoadMore() {
        if (index == images.size()-2 ){
            if (images.size() % 20 != 0)
                return;
            api = Api.getInstance();
            page++;
            if (category == 0) {
                api.getImagesPage(page, order, new ApiCallback() {
                    @Override
                    public void onSuccess(Object responseObject) {
                        ImagesResponse response = (ImagesResponse) responseObject;
                        if (response.isStatus()) {
                            images.addAll(response.getData());
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            } else {
                api.getCategoryImagesPage(category, page, order, new ApiCallback() {
                    @Override
                    public void onSuccess(Object responseObject) {
                        ImagesResponse response = (ImagesResponse) responseObject;
                        if (response.isStatus()) {
                            images.addAll(response.getData());
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
        setNavigationArrowsVisibility();
        adView.loadAd(new AdRequest.Builder().build());
    }

    private void loadImage(){

        RequestOptions options = new RequestOptions()
                .placeholder(R.color.white)
                .error(R.color.cardview_dark_background)
                .override(ivImage.getWidth(),0);

        Glide.with(this)
                .load(MyApplication.BASE_URL + images.get(index).getImageUrl())
                .apply(options)
                .into(ivImage);

        tvDescription.setText(images.get(index).getFullText());
    }

    private void setNavigationArrowsVisibility(){
        if(index <= 0){
            ivBack.setVisibility(View.GONE);
        } else {
            ivBack.setVisibility(View.VISIBLE);
        }

        if(index >= images.size()-1){
            ivNext.setVisibility(View.GONE);
        } else {
            ivNext.setVisibility(View.VISIBLE);
        }
    }

    private void saveImage(Bitmap image) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 112);
                return ;
            }
        }
        String savedImagePath;
        Calendar calendar = Calendar.getInstance();
        String imageFileName = "JPEG_" + String.valueOf(calendar.getTimeInMillis()) + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/"+getString(R.string.app_name));
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(ImageDisplayActivity.this, R.string.image_saved, Toast.LENGTH_LONG).show();
        }
    }

    private String saveImageForShare(Bitmap image) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 113);
                return "cancel";
            }
        }
        String savedImagePath = null;
        Calendar calendar = Calendar.getInstance();
        String imageFileName = "TEMP_" + String.valueOf(calendar.getTimeInMillis()) + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/"+getString(R.string.app_name));
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void shareImage(String imagePath){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image)));
    }

    private void deleteImage(String path) {
        File file = new File(path);
        if (file.exists())
            file.delete();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 112){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                fabDownload.performClick();
            }
        }else if (requestCode == 113){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                fabShare.performClick();
            }
        }
    }
}
