package com.example.snappit_an;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static AmazonRekognition rekogClient;
//    private static final String poolId = "us-east-1:e8a685f7-17c3-4426-8682-7f964bad8f16";
//    private static final Regions region = Regions.US_EAST_1;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private Uri fileUri;

    AmazonRekognitionClient amazonRekognitionClient;

//    public static void init(Context context) {
//        if (rekogClient != null) {
//            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                    context,
//                    poolId,
//                    region
//            );
//            rekogClient = new AmazonRekognitionClient(credentialsProvider);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            getCredential();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Opening Camera . . .", Snackbar.LENGTH_LONG)
                        .setAction("Open", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                            }
                        }).show();

            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(myintent);
            }
        };

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab2);
        button.setOnClickListener(onClickListener);

        try {
            findCelebrity();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCredential(){
        amazonRekognitionClient = new AmazonRekognitionClient(new AWSUtil());
        amazonRekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_1));
    }
    public void findCelebrity() {

        String photo = "sansaGOT.png";
        String bucket = "snappitbucket";


        RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                        .withName(photo).withBucket(bucket)));

        Log.e("Looking for celebs in ", photo + "\n");
        RecognizeCelebritiesResult
                result=amazonRekognitionClient.recognizeCelebrities(request);
        //Display recognized celebrity information
        List<Celebrity> celebs=result.getCelebrityFaces();
        Log.e(String.valueOf(celebs.size()), " celebrity(s) were recognized.\n");
        for (Celebrity celebrity: celebs) {
            Log.e("Celebrity recognized: ", celebrity.getName());
            Log.e("Celebrity ID: ", celebrity.getId());
            BoundingBox boundingBox=celebrity.getFace().getBoundingBox();
            Log.e("position: ",
                    boundingBox.getLeft().toString() + " " +
                            boundingBox.getTop().toString());
            Log.e("more info", "Further information (if available):");
            for (String url: celebrity.getUrls()){
                Log.e(url, "url");
            }
        }
    }
}
