package com.quotation.quo.quot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewMainActivity extends AppCompatActivity {

    ImageView ivMenu;
    CustomTextView tvTitle, tvRandom, tvOldest, tvNewest;
    LinearLayout llMenu, llRate, llOtherApps;
    RecyclerView rvImages;
    AdView adView;
    private InterstitialAd mInterstitialAd;
    String oldestPostId ;
    ArrayList<Image> images;
    int clickedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);



        ivMenu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tv_title);
        tvRandom = findViewById(R.id.tv_random);
        tvOldest = findViewById(R.id.tv_oldest);
        tvNewest = findViewById(R.id.tv_newest);
        adView = findViewById(R.id.adView);
        llMenu = findViewById(R.id.ll_menu);
        llRate = findViewById(R.id.ll_rate);
        llOtherApps = findViewById(R.id.ll_other_apps);
        rvImages = findViewById(R.id.rv_list);


        tvTitle.setText(getString(R.string.app_name));
        llMenu.setVisibility(View.GONE);

        images = new ArrayList<>();
        images.add(new Image(1,"https://assets.vogue.com/photos/5891602d8c64075803acfcbb/master/w_780,c_limit/jennifer-lawrence.jpg","صورة 1","Jennifer Lawrence"));
        images.add(new Image(2,"http://themepack.me/i/c/749x468/media/g/343/emma-stone-theme-1.jpg","صورة 2","Emma Stone"));
        images.add(new Image(3,"https://data.whicdn.com/images/123636297/large.png","صورة 3","Angelina Jolie"));
        images.add(new Image(4,"https://photos.vanityfair.com/2014/04/21/53556883158726f16b740128_scarlett-johansson-vanity-fair-ss05.jpg","صورة 4","Scarlett Johansson"));
        images.add(new Image(5,"http://lagrande.emisorasunidas.com/sites/default/files/images/Sofia-Vergara1.jpg","صورة 5","Sofia Vergara"));


        StaggeredGridLayoutManager layoutManager =  new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ImagesAdapter adapter = new ImagesAdapter(images , this);
        rvImages.setLayoutManager(layoutManager);
        rvImages.setAdapter(adapter);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMenu();
            }
        });

        llRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMainActivity.this,"Rate",Toast.LENGTH_LONG).show();
                llMenu.setVisibility(View.GONE);
            }
        });

        llOtherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMainActivity.this,"Other Apps",Toast.LENGTH_LONG).show();
                llMenu.setVisibility(View.GONE);
            }
        });

        tvNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMainActivity.this,"Newest",Toast.LENGTH_LONG).show();
            }
        });

        tvOldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMainActivity.this,"Oldest",Toast.LENGTH_LONG).show();
            }
        });

        tvRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMainActivity.this,"Random",Toast.LENGTH_LONG).show();
            }
        });

        MobileAds.initialize(this, "ca-app-pub-4123514517726578~5581013211");
        adView.loadAd(new AdRequest.Builder().build());

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Intent intent = new Intent(NewMainActivity.this, ImageDisplayActivity.class);
                intent.putExtra("image",images.get(clickedIndex));
                startActivity(intent);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    oldestPostId = child.getKey(); ////HERE WE ARE SAVING THE LAST POST_ID FROM URS POST

                    dataSnapshot.getChildrenCount();
//                    String event_id = dataSnapshot.child("details").child("event_id").getValue().toString();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_help:
//                Toast.makeText(this, "This is teh option help", Toast.LENGTH_LONG).show();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    private void toggleMenu(){
        if(llMenu.getVisibility() == View.GONE){
            llMenu.setVisibility(View.VISIBLE);
        }else{
            llMenu.setVisibility(View.GONE);
        }
    }

    public void imageClicked(int index){
        clickedIndex = index;
        Toast.makeText(NewMainActivity.this,"image "+index,Toast.LENGTH_LONG).show();
        mInterstitialAd.show();
    }
}
