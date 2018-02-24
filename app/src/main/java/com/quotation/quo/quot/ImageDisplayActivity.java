package com.quotation.quo.quot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

public class ImageDisplayActivity extends AppCompatActivity {

    int index;
    ArrayList<Image> images;
    ImageView ivImage, ivBack, ivNext, ivMenu;
    CustomTextView tvTitle;
    AdView adView;
    FloatingActionMenu menu;
    com.github.clans.fab.FloatingActionButton fabDownload, fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_desplay);

        Bundle args = getIntent().getExtras();
        images = new ArrayList<>();
        if (args != null) {
            images = args.getParcelableArrayList("images");
            index = args.getInt("index", 0);
        }
        ivMenu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tv_title);
        ivImage = findViewById(R.id.iv_image);
        ivBack = findViewById(R.id.iv_back);
        ivNext = findViewById(R.id.iv_next);
//        fabDownload = v.findViewById(R.id.fab_download);
        adView = findViewById(R.id.adView);
        menu = findViewById(R.id.menu);
        fabDownload = findViewById(R.id.fab_download);
        fabShare = findViewById(R.id.fab_share);

        tvTitle.setText(getString(R.string.app_name));
        ivMenu.setVisibility(View.GONE);
        menu.setClosedOnTouchOutside(true);

        MobileAds.initialize(this, "ca-app-pub-4123514517726578~5581013211");
        adView.loadAd(new AdRequest.Builder().build());

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                setNavigationArrowsVisibility();
                loadImage();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                setNavigationArrowsVisibility();
                loadImage();
            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close(true);
                //TODO add image download code
                Picasso.with(ImageDisplayActivity.this)
                        .load(images.get(index).getImageURL())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                saveImage(bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Toast.makeText(ImageDisplayActivity.this,"Error while downloading the image !",Toast.LENGTH_LONG).show();
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
                //TODO add image share code
            }
        });

        loadImage();
        setNavigationArrowsVisibility();
    }

    private void loadImage(){
        Picasso.with(this)
                .load(images.get(index).getImageURL())
                .placeholder(R.color.cardview_dark_background)
                .into(ivImage);
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
            Toast.makeText(ImageDisplayActivity.this, "Image saved", Toast.LENGTH_LONG).show();
        }
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}
