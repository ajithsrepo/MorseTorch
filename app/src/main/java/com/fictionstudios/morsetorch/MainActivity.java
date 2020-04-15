package com.fictionstudios.morsetorch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraManager;
import android.preference.PreferenceManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.LineMoveIndicator;
import com.kobakei.ratethisapp.RateThisApp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean useDarkTheme;
    private DachshundTabLayout tabLayout;
    private ViewPager viewPager;
    private CameraManager cameraManager;
    private FlashLight torch;
    private int tabColor;
    private int tabTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        MobileAds.initialize(this, "ca-app-pub-8453607245436940~5422620719");

        onStartUp();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (DachshundTabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(tabColor);

        LineMoveIndicator indicator = new LineMoveIndicator(tabLayout);
        tabLayout.setAnimatedIndicator(indicator);

        // Custom condition: 5 days and 5 launches
        RateThisApp.Config config = new RateThisApp.Config(5, 5);
        RateThisApp.init(config);
        // Monitor launch times and interval from installation
        RateThisApp.onCreate(this);
        // If the condition is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    private void changeTheme() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        useDarkTheme = pref.getBoolean("theme_pref", false);

        if (useDarkTheme) {
            setTheme(R.style.DarkTheme);
            tabColor = getResources().getColor(R.color.black);
        } else {
            setTheme(R.style.AppTheme);
            tabColor = getResources().getColor(R.color.redPrimary);
        }
    }

    private void onStartUp() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = prefs.getBoolean(Utils.FIRST_INSTALL, true);
        if (isFirstRun) {
            startActivity(new Intent(this, MainIntroActivity.class));
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(Utils.FIRST_INSTALL, false);
            editor.commit();
        }

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        torch = new FlashLight(cameraManager, getApplicationContext());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Torch");
        adapter.addFragment(new MorseFragment(), "Morse");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void flashSOS() {
        final String sosMorse = "... --- ...";
        Utils.updateSpeed(this);
        torch.morseToFlash(sosMorse);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(Utils.navIntent(this, AboutActivity.class));
                return true;
            case R.id.action_sos:
                flashSOS();
                return true;
            case R.id.action_settings:
                startActivity(Utils.navIntent(this, MyPreferencesActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
