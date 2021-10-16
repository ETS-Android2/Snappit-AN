package com.example.snappit_an;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {
    AmazonRekognitionClient amazonRekognitionClient;
    private final int REQUEST_CODE = 100;


    private static final String IMDB_TOKEN = "k_l1oxwwl6";
    private static String stSummary;
    private Activity activity;

    public void getCredential() {
        amazonRekognitionClient = new AmazonRekognitionClient(new AWSUtil());
        amazonRekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    private final void openGalleryForImage() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(intent, this.REQUEST_CODE);

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
            UploadActivity.this.openGalleryForImage();

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

            if (data != null) {
                Uri uri = data.getData();
                ImageView imageView = (ImageView) this.findViewById(R.id.imageBox);
                imageView.setImageURI(uri);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    findCelebrity(ByteBuffer.wrap(baos.toByteArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void buttonClick() {
        ImageView imageView = (ImageView) this.findViewById(R.id.imageBox);

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            findCelebrity(ByteBuffer.wrap(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findCelebrity(ByteBuffer imageBytes) {

        if (imageBytes != null && imageBytes.hasArray()) {
            RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                    .withImage(new Image().withBytes(imageBytes));


            RecognizeCelebritiesResult result = amazonRekognitionClient.recognizeCelebrities(request);

            //Display recognized celebrity information
            List<Celebrity> celebs = result.getCelebrityFaces();

            Log.e(String.valueOf(celebs.size()), " celebrity(s) were recognized.\n");

            for (Celebrity celebrity : celebs) {

                Log.i("Celebrity recognized: ", celebrity.getName());
                //show in app
                TextView textView = (TextView) findViewById(R.id.nameHere);
                textView.append(celebrity.getName());

                Log.i("Celebrity ID: ", celebrity.getId());
                BoundingBox boundingBox = celebrity.getFace().getBoundingBox();
                Log.i("position: ",
                        boundingBox.getLeft().toString() + " " +
                                boundingBox.getTop().toString());
                Log.i("more info", "Further information (if available):");
                for (String url : celebrity.getUrls()) {
                    Log.i(url, "url");

                    if (url.contains("imdb")) {
                        String[] parts = url.split("/");
                        String nameID = parts[parts.length - 1];
                        requestIMDB(nameID);

                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
    }

    public void requestIMDB(String nameID) {

        if (nameID == null || nameID.isEmpty()) {
            return;
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://imdb-api.com/en/API/Name/" + IMDB_TOKEN + "/" + nameID)
                .method("GET", null)
                .build();
        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response != null && response.body() != null) {
                Log.i("Response: ", response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

