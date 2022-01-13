package com.flight.flightsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class MainActivity extends AppCompatActivity {
    private AdView adView;
    private WebView myWebView;
    private InterstitialAd mInterstitialAd;
    int count=3;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        this.myWebView = (WebView) findViewById(R.id.WebView);
        this.adView =  findViewById(R.id.adView);
        this.myWebView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.loadUrl("https://www.google.com/flights");
        this.myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showAd();
            }
        });
        this.adView.loadAd(new AdRequest.Builder().build());
        loadAd();
    }

    private void showAd()
    {

        if(count==3)
        {
            if(mInterstitialAd!=null)
            {
                mInterstitialAd.show(this);
                loadAd();
                count=0;
            }
        }
        else
        {
            count++;
        }

    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        if (this.myWebView.canGoBack()) {
            this.myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public  void  loadAd()
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.admob_inter_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }
}