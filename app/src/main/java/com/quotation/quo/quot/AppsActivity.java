package com.quotation.quo.quot;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class AppsActivity extends AppCompatActivity {
    ImageView ivMenu;
    CustomTextView tvTitle;
    LinearLayout llMenu, llRate, llOtherApps;
    RecyclerView rvApps;
    AdView adView;

    ArrayList<App> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        ivMenu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tv_title);
        llMenu = findViewById(R.id.ll_menu);
        llRate = findViewById(R.id.ll_rate);
        llOtherApps = findViewById(R.id.ll_other_apps);
        rvApps = findViewById(R.id.rv_apps);
        adView = findViewById(R.id.adView);

        llMenu.setVisibility(View.GONE);

        apps = new ArrayList<>();
        if (getIntent().getExtras() != null){
            apps = getIntent().getExtras().getParcelableArrayList("apps");
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AppsAdapter adapter = new AppsAdapter(apps, this);
        rvApps.setLayoutManager(layoutManager);
        rvApps.setAdapter(adapter);

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        adView.loadAd(new AdRequest.Builder().build());

        rvApps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(llMenu.getVisibility() == View.VISIBLE){
                    toggleMenu();
                    return true;
                }
                return false;
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMenu();
            }
        });

        llRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + AppsActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                } else {
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                }
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + AppsActivity.this.getPackageName())));
                }
                llMenu.setVisibility(View.GONE);
            }
        });

        llOtherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppsActivity.this, AppsActivity.class);
                intent.putExtra("apps", apps);
                startActivity(intent);
                llMenu.setVisibility(View.GONE);
            }
        });
    }

    public void appClicked(int index){
        Uri uri = Uri.parse("market://details?id=" + apps.get(index).getPackageId());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + apps.get(index).getPackageId())));
        }
    }

    private void toggleMenu(){
        if(llMenu.getVisibility() == View.GONE){
            Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            slideDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    llMenu.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            llMenu.startAnimation(slideDown);
        }else{
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llMenu.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            llMenu.startAnimation(slideUp);
        }
    }
}
