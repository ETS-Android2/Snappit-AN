package com.example.snappit_an;

import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IMDbActivity {

    String title;
    String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public IMDbActivity(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public IMDbActivity() {

    }



}
