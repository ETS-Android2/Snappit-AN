package com.example.snappit_an;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.utils.Utils;

public class Onboarding extends AppCompatActivity {

    private static final String PREFERENCES_FILE = null;
    private static final String PREF_USER_FIRST_TIME = null;
    ImageView logo, appname, splashImg;
    LottieAnimationView lottieAnimationView;

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePageAdapter pagerAdapter;

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//
        logo = findViewById(R.id.logo);
        appname = findViewById(R.id.app_name);
        splashImg = findViewById(R.id.imgOnboarding);
        lottieAnimationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        lottieAnimationView.playAnimation();
        splashImg.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.cancelAnimation();
        hideSystemUI();

//        ImageButton mFinishBtn = (ImageButton) findViewById(R.id.backHome);
//        mFinishBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finish();
//                //  update 1st time pref
//                saveSharedSetting(Onboarding.this, Onboarding.PREF_USER_FIRST_TIME, "false");
//
//            }
//        });

    }

    public void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private class ScreenSlidePageAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePageAdapter(@NonNull FragmentManager fragment) {
            super(fragment);
        }

        @NonNull
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                OnboaridingFragment1 tab1 = new OnboaridingFragment1();
                return tab1;

                case 1:
                    OnboardingFragment2 tab2 = new OnboardingFragment2();
                    return tab2;

                case 2:
                    OnboardingFragment3 tab3 = new OnboardingFragment3();
                    return tab3;

            }
            return null;

        }

        public int getCount() {
            return NUM_PAGES;
        }

    }



}
