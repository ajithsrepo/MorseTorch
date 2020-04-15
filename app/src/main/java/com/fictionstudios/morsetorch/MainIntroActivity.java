package com.fictionstudios.morsetorch;

import android.Manifest;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainIntroActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setFullscreen(true);
        super.onCreate(savedInstanceState);

        addSlide(new SimpleSlide.Builder()
                .title(R.string.app_name)
                .description(R.string.description__intro)
                .image(R.mipmap.ic_launcher)
                .background(R.color.redPrimary)
                .backgroundDark(R.color.redPrimaryDark)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_morse)
                .description(R.string.description__morse)
                .image(R.drawable.morse_pic)
                .background(R.color.redPrimary)
                .backgroundDark(R.color.redPrimaryDark)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_permission)
                .description(R.string.description__permission)
                .image(R.drawable.permissions_pic)
                .background(R.color.redPrimary)
                .backgroundDark(R.color.redPrimaryDark)
                .scrollable(false)
                .permission(Manifest.permission.CAMERA)
                .build());
        // Add slides, edit configuration...
    }
}