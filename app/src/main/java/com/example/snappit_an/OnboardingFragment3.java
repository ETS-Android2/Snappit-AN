package com.example.snappit_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnboardingFragment3 extends Fragment {

    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding3, container, false);

        return root;
    }

}
