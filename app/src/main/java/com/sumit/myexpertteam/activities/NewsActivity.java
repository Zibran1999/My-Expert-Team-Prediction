package com.sumit.myexpertteam.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sumit.myexpertteam.R;
import com.sumit.myexpertteam.utils.MyApp;


public class NewsActivity extends AppCompatActivity {

    int pos = -1;
    ImageView newsImg, backIcon;
    TextView newsTitle, newsDesc;
    String img, desc, title;
    RelativeLayout adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsImg = findViewById(R.id.newsImg);
        newsTitle = findViewById(R.id.newsTitle);
        newsDesc = findViewById(R.id.newsDesc);
        backIcon = findViewById(R.id.back_icon);
        adView = findViewById(R.id.adView);

        img = getIntent().getStringExtra("img");
        desc = getIntent().getStringExtra("desc");
        title = getIntent().getStringExtra("title");
        pos = getIntent().getIntExtra("newsPos", 0);
//        if (pos != 0) {
//            new Handler().postDelayed(() -> {
//                MyApp.showInterstitialAd(this);
//            }, 1000);
//        }

        Glide.with(this).load("https://softwaresreviewguides.com/dreamteam11/APIs/Cricket_News_Images/" + img).into(newsImg);
        newsTitle.setText(title);
        newsDesc.setText(desc);
        MyApp.showBannerAd(this,adView);
        backIcon.setOnClickListener(v -> onBackPressed());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
    }

}