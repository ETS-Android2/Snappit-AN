package com.example.snappit_an;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class UploadActivity extends AppCompatActivity {
    AmazonRekognitionClient amazonRekognitionClient;


    private final int REQUEST_CODE = 100;



    public void getCredential(){
        amazonRekognitionClient = new AmazonRekognitionClient(new AWSUtil());
        amazonRekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    private final void openGalleryForImage() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(intent, this.REQUEST_CODE);

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
            //show in app
            TextView textView = (TextView) findViewById(R.id.nameHere);
            textView.append(celebrity.getName());

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

            setContentView(R.layout.upload_activity);

            getCredential();
            findCelebrity();
            UploadActivity.this.openGalleryForImage();

        }


//        run celebrity recogniser
        try {
            findCelebrity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageButton button = (ImageButton) findViewById(R.id.goBackHome);
        button.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Intent myintent = new Intent(UploadActivity.this, MainActivity.class);
                startActivity(myintent);
            }
        }));
    }




    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == this.REQUEST_CODE) {
            ((ImageView) this.findViewById(R.id.imageBox)).setImageURI(data != null ? data.getData() : null);
        }
    }

}

