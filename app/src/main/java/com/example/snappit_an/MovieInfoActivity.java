package com.example.snappit_an;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieInfoActivity extends AppCompatActivity {

    private static final String IMDB_TOKEN = "k_l1oxwwl6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //create page
            super.onCreate(savedInstanceState);
            setContentView(R.layout.movies_layout);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            String movieId = getIntent().getStringExtra("id");

            try {
                requestIMDB(movieId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("JSON: ","jsonBody");

        }

        ImageButton goBack = (ImageButton) findViewById(R.id.goBackToResults);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToResults = new Intent(MovieInfoActivity.this, MainActivity.class);
                startActivity(backToResults);
            }
        });
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

    @SuppressLint("ResourceType")
    public void requestIMDB(String movieId) throws JSONException {
        if (movieId == null || movieId.isEmpty()) {
            return;
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://imdb-api.com/en/API/YouTubeTrailer/" + IMDB_TOKEN + "/" + movieId)
                .method("GET", null)
                .build();
        Response response = null;


        try {
            response = client.newCall(request).execute();

            if (response != null && response.body() != null) {

                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);

                String movieID = Jobject.getString("videoId");

                String frameVideo = "<html><iframe height=\"300\" width=\"400\"  src= \"https://www.youtube.com/embed/" + movieID + "\" frameborder=\"0\" allowfullscreen></iframe></html>";
                WebView displayYoutubeVideo = (WebView) findViewById(R.id.webView);
                displayYoutubeVideo.setBackgroundColor(Color.BLACK);

                displayYoutubeVideo.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
                WebSettings webSettings = displayYoutubeVideo.getSettings();
                webSettings.setJavaScriptEnabled(true);
                displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");

                TextView mTextView = (TextView) findViewById(R.id.movieName);
                mTextView.setText(Jobject.getString("fullTitle"));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
