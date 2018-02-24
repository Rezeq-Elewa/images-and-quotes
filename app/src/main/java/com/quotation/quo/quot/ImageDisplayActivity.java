package com.quotation.quo.quot;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    Image image;
    ImageView ivImage, ivBack, ivNext, ivMenu;
    CustomTextView tvTitle;
    FloatingActionButton fabDownload;
    AdView adView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_desplay);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            image = (Image) args.getSerializable("image");
        }
        ivMenu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tv_title);
        ivImage = findViewById(R.id.iv_image);
        ivBack = findViewById(R.id.iv_back);
        ivNext = findViewById(R.id.iv_next);
//        fabDownload = v.findViewById(R.id.fab_download);
        adView = findViewById(R.id.adView);

        tvTitle.setText(getString(R.string.app_name));
        ivMenu.setVisibility(View.GONE);

        MobileAds.initialize(this, "ca-app-pub-4123514517726578~5581013211");
        adView.loadAd(new AdRequest.Builder().build());

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//        });
//        mInterstitialAd.show();

        Picasso.with(this)
                .load(image.getImageURL())
                .placeholder(R.color.cardview_dark_background)
                .into(ivImage);
    }
}
