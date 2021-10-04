package com.example.snappit_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.snappit_an.Util.setImage;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView imgPreview;
    private TextView resultTextView;
    private TextView resultTextView2;
    private ImageView imgResult;
    private String refImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        imgResult = (ImageView) findViewById(R.id.imgResult);
        resultTextView = (TextView) findViewById(R.id.chargesText);
        resultTextView2 = (TextView) findViewById(R.id.chargesText2);

        // init aws
//        AWSUtil.init(getApplicationContext());

        // Receiving the data from previous activity
        Intent i = getIntent();

        // image or video path that is captured in previous activity
        String filePath = i.getStringExtra("filePath");

        if (filePath != null) {
            // Displaying the image or video on the screen
            imgPreview.setVisibility(View.VISIBLE);
            setImage(filePath, imgPreview);

        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

    }
}

