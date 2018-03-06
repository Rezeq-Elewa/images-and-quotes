package com.quotation.quo.quot;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.Collections;
import java.util.Comparator;

public class NewMainActivity extends AppCompatActivity implements LoadMoreListener {

    ImageView ivMenu, ivList, ivGrid;
    CustomTextView tvTitle, tvRandom, tvOldest, tvNewest;
    LinearLayout llMenu, llRate, llOtherApps, llCategories;
    RecyclerView rvImages , rvCategories;
    AdView adView;
    private InterstitialAd mInterstitialAd;
    String oldestPostId ;
    ArrayList<Image> images;
    ArrayList<App> apps;
    ArrayList<Section> sections;
    ProgressBar loadMoreProgressBar;

    DatabaseReference imagesDatabaseReference;
    DatabaseReference appsDatabaseReference;
    DatabaseReference sectionsDatabaseReference;

    String category = "0";

    boolean isLoading = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);



        ivMenu = findViewById(R.id.iv_menu);
        ivList = findViewById(R.id.display_list);
        ivGrid = findViewById(R.id.display_grid);
        tvTitle = findViewById(R.id.tv_title);
        tvRandom = findViewById(R.id.tv_random);
        tvOldest = findViewById(R.id.tv_oldest);
        tvNewest = findViewById(R.id.tv_newest);
        adView = findViewById(R.id.adView);
        llMenu = findViewById(R.id.ll_menu);
        llRate = findViewById(R.id.ll_rate);
        llOtherApps = findViewById(R.id.ll_other_apps);
        llCategories = findViewById(R.id.ll_categories);
        rvImages = findViewById(R.id.rv_list);
        rvCategories = findViewById(R.id.rv_categories);
        loadMoreProgressBar = findViewById(R.id.load_more_progress_bar);


        tvTitle.setText(getString(R.string.app_name));
        llMenu.setVisibility(View.GONE);
        loadMoreProgressBar.setVisibility(View.GONE);

        images = new ArrayList<>();
        apps = new ArrayList<>();
        sections = new ArrayList<>();

        StaggeredGridLayoutManager layoutManager =  new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ImagesAdapter adapter = new ImagesAdapter(images , this, "grid");
        adapter.setLoadMoreListener(this);
        rvImages.setLayoutManager(layoutManager);
        rvImages.setAdapter(adapter);
        rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(llMenu.getVisibility() == View.VISIBLE){
                    llMenu.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        rvCategories.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvCategories.setAdapter(new CategoriesAdapter(sections,this));



        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMenu();
            }
        });

        llRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + NewMainActivity.this.getPackageName());
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
                            Uri.parse("http://play.google.com/store/apps/details?id=" + NewMainActivity.this.getPackageName())));
                }
                llMenu.setVisibility(View.GONE);
            }
        });

        llOtherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewMainActivity.this, AppsActivity.class);
                intent.putExtra("apps", apps);
                startActivity(intent);
                llMenu.setVisibility(View.GONE);
            }
        });

        llCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvCategories.getVisibility() == View.VISIBLE){
                    rvCategories.setVisibility(View.GONE);
                } else {
                    rvCategories.setVisibility(View.VISIBLE);
                }
            }
        });

        ivGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvImages.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                ImagesAdapter adapter = new ImagesAdapter(images , NewMainActivity.this, "grid");
                adapter.setLoadMoreListener(NewMainActivity.this);
                rvImages.setAdapter(adapter);
                rvImages.getLayoutManager().smoothScrollToPosition(rvImages, null , 0);
            }
        });

        ivList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvImages.setLayoutManager(new LinearLayoutManager(NewMainActivity.this,LinearLayout.VERTICAL,false));
                ImagesAdapter adapter = new ImagesAdapter(images , NewMainActivity.this, "list");
                adapter.setLoadMoreListener(NewMainActivity.this);
                rvImages.setAdapter(adapter);
                rvImages.getLayoutManager().smoothScrollToPosition(rvImages, null , 0);
            }
        });

        tvNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(images, new Comparator<Image>() {
                    @Override
                    public int compare(Image image1, Image image2) {
                        return image2.getId() - image1.getId();
                    }
                });
                rvImages.getAdapter().notifyDataSetChanged();
                rvImages.getLayoutManager().smoothScrollToPosition(rvImages, null , 0);
            }
        });

        tvOldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(images, new Comparator<Image>() {
                    @Override
                    public int compare(Image image1, Image image2) {
                        return image1.getId() - image2.getId();
                    }
                });
                rvImages.getAdapter().notifyDataSetChanged();
                rvImages.getLayoutManager().smoothScrollToPosition(rvImages, null , 0);
            }
        });

        tvRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(images);
                rvImages.getAdapter().notifyDataSetChanged();
                rvImages.getLayoutManager().smoothScrollToPosition(rvImages, null , 0);
            }
        });

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        adView.loadAd(new AdRequest.Builder().build());

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        imagesDatabaseReference = FirebaseDatabase.getInstance().getReference("/images/"+category);
        appsDatabaseReference = FirebaseDatabase.getInstance().getReference("/apps");
        sectionsDatabaseReference = FirebaseDatabase.getInstance().getReference("/sections");

        new Thread(new Runnable() {
            @Override
            public void run() {
                appsDatabaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            App app = child.getValue(App.class);
                            if (app != null) {
                                apps.add(app);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        isLoading = false;
                    }
                });
            }
        }).start();

        sectionsDatabaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Section section = child.getValue(Section.class);
                    if (section != null) {
                        section.setId(Integer.parseInt(child.getKey()));
                        sections.add(section);
                    }
                }
                rvCategories.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                isLoading = false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLoading = true;
        readFromDBFirstTime();
    }

    @Override
    public void loadMore() {
        if(images.size() % 20 != 0 || isLoading)
            return;

        isLoading = true;
        loadMoreProgressBar.setVisibility(View.VISIBLE);
        if(imagesDatabaseReference != null){
            String index = String.valueOf(Integer.parseInt(oldestPostId) + 1);
            imagesDatabaseReference.orderByKey().startAt(index).limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        oldestPostId = child.getKey(); ////HERE WE ARE SAVING THE LAST POST_ID FROM URS POST

                        Image image = child.getValue(Image.class);
                        if (image != null) {
                            image.setId(Integer.parseInt(child.getKey()));
                        }
                        images.add(image);
                    }
                    isLoading = false;
                    rvImages.getAdapter().notifyDataSetChanged();
                    loadMoreProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    isLoading = false;
                    loadMoreProgressBar.setVisibility(View.GONE);
                }
            });
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    protected void onResume() {
        super.onResume();
        adView.loadAd(new AdRequest.Builder().build());
    }

    private void toggleMenu(){
        if(llMenu.getVisibility() == View.GONE){
            llMenu.setVisibility(View.VISIBLE);
        }else{
            llMenu.setVisibility(View.GONE);
        }
    }

    public void imageClicked(int index){
        if(llMenu.getVisibility() == View.VISIBLE){
            llMenu.setVisibility(View.GONE);
            return;
        }
        Intent intent = new Intent(NewMainActivity.this, ImageDisplayActivity.class);
        intent.putExtra("images",images);
        intent.putExtra("index",index);
        startActivity(intent);
        mInterstitialAd.show();
    }

    public void sectionClicked(int index){
        category = String.valueOf(sections.get(index).getId());
        imagesDatabaseReference = FirebaseDatabase.getInstance().getReference("/images/"+category);
        readFromDBFirstTime();
        llCategories.performClick();
        ivMenu.performClick();
        rvImages.getLayoutManager().smoothScrollToPosition(rvImages, null, 0);
    }

    public void readFromDBFirstTime(){
        imagesDatabaseReference.orderByKey().startAt("0").limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isLoading = false;
                images.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    oldestPostId = child.getKey();

                    Image image = child.getValue(Image.class);
                    if (image != null) {
                        image.setId(Integer.parseInt(child.getKey()));
                        images.add(image);
                    }
                }
                rvImages.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                isLoading = false;
            }
        });
    }

}

