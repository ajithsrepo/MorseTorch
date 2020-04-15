package com.fictionstudios.morsetorch;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.fragment.app.Fragment;
import androidx.appcompat.view.ContextThemeWrapper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import io.ghyeok.stickyswitch.widget.StickySwitch;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {

    private boolean useDarkTheme;
    private StickySwitch stickySwitch;
    private int stickyColor;
    private int stickyBackColor;
    private int theme;
    private CameraManager cameraManager;
    private FlashLight torch;
    private View view;
    private static final String TAG = "MainFragment";

    private AdView mAdView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        changeTheme();

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), theme);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        view = localInflater.inflate(R.layout.fragment_main, container, false);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        onStartUp();
        initWidgets();

        return view;
    }

    private void changeTheme() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        useDarkTheme = pref.getBoolean("theme_pref", false);

        if (useDarkTheme) {
            theme = R.style.DarkTheme;
            stickyColor = 0xFFFFFFFF;   //white
            stickyBackColor = 0xFF000000;   //black
        } else {
            theme = R.style.AppTheme;
            stickyColor = 0xFFCD4844;   //redtheme
            stickyBackColor = 0xFFC0C0C0;   //silver
        }
    }

    private void onStartUp() {
        cameraManager = (CameraManager) this.getActivity().getSystemService(Context.CAMERA_SERVICE);
        torch = new FlashLight(cameraManager, getContext());
    }

    private void initWidgets() {
        stickySwitch = view.findViewById(R.id.sticky_switch);
        stickySwitch.setTextColor(stickyColor);
        stickySwitch.setSliderBackgroundColor(stickyBackColor);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                if (direction == StickySwitch.Direction.RIGHT) {
                    torch.flashLightOn();
                } else if (direction != StickySwitch.Direction.RIGHT) {
                    torch.flashLightOff();
                } else {
                    Utils.showToast("Torch failed.", Toast.LENGTH_LONG, getContext());
                }
            }
        });
    }
}
