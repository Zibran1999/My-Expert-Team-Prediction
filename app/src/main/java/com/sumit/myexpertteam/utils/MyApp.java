package com.sumit.myexpertteam.utils;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;
import com.sumit.myexpertteam.activities.SplashActivity;
import com.sumit.myexpertteam.models.AdsModel;
import com.sumit.myexpertteam.models.AdsModelList;
import com.sumit.myexpertteam.models.MatchDetailModels.ApiInterface;
import com.sumit.myexpertteam.models.MatchDetailModels.WebServices;

import java.util.Objects;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApp extends Application {
    private static final String ONESIGNAL_APP_ID = "452632c3-18f4-4e3a-b63a-3f866ac82186";
    public static InterstitialAd interstitialAd;

    public static MyApp mInstance;
    ApiInterface apiInterface;


    public MyApp() {

        mInstance = this;
    }

    public static void showInterstitialAd(Activity context) {

        String id = Paper.book().read(Prevalent.interstitialAds);

        Log.d("admobAdInter", id);

        interstitialAd = new InterstitialAd(context, id);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public static void showBannerAd(Context context, RelativeLayout container) {
        String id = Paper.book().read(Prevalent.bannerAds);
        Log.d("facebookBannerId", id);
        AdView adView = new AdView(context, id, AdSize.BANNER_HEIGHT_50);
        container.addView(adView);
        adView.loadAd();
        container.setVisibility(View.VISIBLE);


//        MobileAds.initialize(context);
//        Log.d("admobAdBan", id);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        AdView adView = new AdView(context);
//        container.addView(adView);
//        adView.setAdUnitId(id);
//        adView.setAdSize(AdSize.BANNER);
//        adView.loadAd(adRequest);
//        container.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Paper.init(mInstance);
        fetchAds();
        AudienceNetworkAds.initialize(mInstance);
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    private void fetchAds() {

        apiInterface = WebServices.getInterface();
        Call<AdsModelList> call = apiInterface.fetchAds("My Expert Team");
        call.enqueue(new Callback<AdsModelList>() {
            @Override
            public void onResponse(@NonNull Call<AdsModelList> call, @NonNull Response<AdsModelList> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getData() != null) {
                        for (AdsModel ads : response.body().getData()) {
                            Log.d("admobAdId", ads.getBanner() + " " + ads.getInterstitial() + " " + ads.getNativeADs());
                            Paper.book().write(Prevalent.bannerAds, ads.getBanner().trim());
                            Paper.book().write(Prevalent.interstitialAds, ads.getInterstitial().trim());
                            Paper.book().write(Prevalent.nativeAds, ads.getNativeADs().trim());
                            Paper.book().write(Prevalent.openAppAds, ads.getAppOpen());
                            MobileAds.initialize(mInstance);
//                            appOpenManager = new AppOpenManager(mInstance, Paper.book().read(Prevalent.openAppAds), getApplicationContext());
//                            Log.d("is showing", String.valueOf(AppOpenManager.isIsShowingAd));
                        }
                    }
                } else {
                    Log.d("adsError", response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<AdsModelList> call, @NonNull Throwable t) {
                Log.d("adsError", t.getMessage());
            }
        });
    }

//    public void intent() {
//        if (!AppOpenManager.isIsShowingAd) {
//            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            AppOpenManager.isIsShowingAd = false;
//        }
//    }


    private class ExampleNotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            Intent intent = new Intent(MyApp.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
