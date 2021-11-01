package com.example.snappit_an;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_snappit);

        ImageButton aboutHome = (ImageButton) findViewById(R.id.aboutback);
        aboutHome.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Intent myHomeIntent = new Intent(AboutPage.this, SettingsActivity.class);
                startActivity(myHomeIntent);
                finish();
            }
        }));

        String githubLink = "https://github.com/ClaudiaAF/Snappit-AN.git";
        ImageButton github = (ImageButton) findViewById(R.id.github);
        github.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(githubLink));
                startActivity(i);
            }
        }));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        hideSystemUI();

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

}