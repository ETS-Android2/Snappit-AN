package com.example.snappit_an;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Camera activity request codes
    private final int REQUEST_CODE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Camera camera;
    private Uri fileUri;
    private static final int CAMERA_REQUEST = 1888;
    private HashMap _$_findViewCache;
    SharedPreferences onBoardingScreen;



    AmazonRekognitionClient amazonRekognitionClient;



    public void getCredential(){
        amazonRekognitionClient = new AmazonRekognitionClient(new AWSUtil());
        amazonRekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_1));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //fix networking operation
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

      //create page
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            getCredential();

        }


        //fab buttons
        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File destination = new File(Environment.getExternalStorageDirectory(),
//                        System.currentTimeMillis() + ".jpg");
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
//                intent.putExtra("MyImagePath", destination.getAbsoluteFile());
//                startActivity(intent);
            }

        });


        ImageButton button = (ImageButton) findViewById(R.id.fab2);
        button.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Intent myintent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(myintent);
            }

        }));

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
