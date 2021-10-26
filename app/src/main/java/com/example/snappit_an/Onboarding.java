package com.example.snappit_an;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class Onboarding extends AppCompatActivity {

    ImageView logo, appname, splashImg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        logo = findViewById(R.id.logo);
        appname = findViewById(R.id.app_name);
        splashImg = findViewById(R.id.imgOnboarding);
        lottieAnimationView = findViewById(R.id.lottie);

        lottieAnimationView.playAnimation();
        splashImg.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.cancelAnimation();

    }
}
