package com.quotation.quo.quot;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class AppsActivity extends AppCompatActivity {
    CustomTextView tvTitle;
    RecyclerView rvApps;
    AdView adView;

    ArrayList<App> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        tvTitle = findViewById(R.id.tv_title);
        rvApps = findViewById(R.id.rv_apps);
        adView = findViewById(R.id.adView);

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
                    Uri.parse("http://play.google.com/store/apps/details?id=" + apps.get(index).getUrl())));
        }
    }
}
