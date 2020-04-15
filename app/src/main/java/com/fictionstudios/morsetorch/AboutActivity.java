package com.fictionstudios.morsetorch;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.franmontiel.attributionpresenter.AttributionPresenter;
import com.franmontiel.attributionpresenter.entities.Attribution;
import com.franmontiel.attributionpresenter.entities.License;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AdView mAdView;
    TextView versionView;   //shows the version
    private final String APP_VERSION_RELEASE = "Version " + Utils.getAppVersion();   //contains Version + the version number
    private final String APP_VERSION_DEBUG = "Version " + Utils.getAppVersion() + "-debug";   //contains Version + the version number + debug

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar_about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void changeTheme() {
        // Use the chosen theme
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = preferences.getBoolean("theme_pref", false);

        if (useDarkTheme) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    private void initUI() {
        versionView = findViewById(R.id.text_version);

        if (BuildConfig.DEBUG) {
            versionView.setText(APP_VERSION_DEBUG);
        } else {
            versionView.setText(APP_VERSION_RELEASE);
        }

    }

    public void replayIntro(View v) {
        //navigate to intro class (replay the intro)
        startActivity(Utils.navIntent(this, MainIntroActivity.class));
    }

    public void showLibraries(View v) {

        //Library builder
        //Library title, copyright notice, license type, website url

        AttributionPresenter attributionPresenter = new AttributionPresenter.Builder(this)
                .addAttributions(
                        new Attribution.Builder("AttributionPresenter")
                                .addCopyrightNotice("Copyright 2017 Francisco José Montiel Navarro")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/franmontiel/AttributionPresenter")
                                .build()
                )

                .addAttributions(
                        new Attribution.Builder("material-intro")
                                .addCopyrightNotice("Copyright 2017 Jan Heinrich Reimer")
                                .addLicense(License.MIT)
                                .setWebsite("https://github.com/heinrichreimer/material-intro")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Android Open Source Project")
                                .addCopyrightNotice("Copyright 2016 The Android Open Source Project")
                                .addLicense(License.APACHE)
                                .setWebsite("http://developer.android.com/tools/support-library/index.html")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Android Support Libraries")
                                .addCopyrightNotice("Copyright 2016 The Android Open Source Project")
                                .addLicense(License.APACHE)
                                .setWebsite("http://developer.android.com/tools/support-library/index.html")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("CardView")
                                .addCopyrightNotice("Copyright 2016 The Android Open Source Project")
                                .addLicense(License.APACHE)
                                .setWebsite("https://developer.android.com/reference/android/support/v7/widget/CardView.html")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Material Ripple Layout")
                                .addCopyrightNotice("Copyright 2015 Balys Valentukevicius")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/balysv/material-ripple")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("StickySwitch")
                                .addCopyrightNotice("Copyright 2017 GwonHyeok")
                                .addLicense(License.MIT)
                                .setWebsite("https://github.com/GwonHyeok/StickySwitch")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Android-RateThisApp")
                                .addCopyrightNotice("Copyright 2013-2017 Keisuke Kobayashi")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/kobakei/Android-RateThisApp")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Dachshund Tab Layout")
                                .addCopyrightNotice("Copyright 2017 Andrii")
                                .addLicense(License.MIT)
                                .setWebsite("https://github.com/Andy671/Dachshund-Tab-Layout")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("MaterialSeekBarPreference")
                                .addCopyrightNotice("Copyright MrBIMC")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/MrBIMC/MaterialSeekBarPreference")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("CircleImageView")
                                .addCopyrightNotice("Copyright Henning Dodenhof")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/hdodenhof/CircleImageView")
                                .build()
                )
                .build();

        //show license dialogue
        attributionPresenter.showDialog("Open Source Libraries");
    }

    @Override
    public void onBackPressed() {
        startActivity(Utils.navIntent(getApplicationContext(), MainActivity.class));
    }

}