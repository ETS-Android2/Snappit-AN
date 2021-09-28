package com.example.snappit_an;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AmazonRekognitionClient amazonRekognitionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCredential();
        Log.i("Credentials", "Credentials gathered succesfully");
    }

    public void getCredential(){
        amazonRekognitionClient = new AmazonRekognitionClient(new AWSCredentialProvider());
        amazonRekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_2));
    }

    public List<String> findCelebrity() {
        String photo = "https://mtv.mtvnimages.com/uri/mgid:ao:image:mtv.com:671939?quality=0.8&format=jpg";
        ByteBuffer imageBytes = null;

        try {
            InputStream inputStream = new FileInputStream(new File(photo));
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));

        } catch (Exception e) {
            System.out.println("Failed to load file " + photo);
            System.exit(1);
        }

        RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                .withImage(new Image().withBytes(imageBytes));

        System.out.println("Looking for celebrities in image " + photo + "\n");

        RecognizeCelebritiesResult result = amazonRekognitionClient.recognizeCelebrities(request);

        //Display recognized celebrity information
        List<Celebrity> celebs = result.getCelebrityFaces();
        System.out.println(celebs.size() + " celebrity(s) were recognized.\n");
        List<String> metadata = new ArrayList<String>();

        for (Celebrity celebrity: celebs) {
            // Name
            Log.i("Celebrity recognized: ", "celebrity.getName()");
            metadata.add("Celebrity recognized: " + celebrity.getName());

            // Id
            System.out.println("Celebrity ID: " + celebrity.getId());
            metadata.add("Celebrity ID: " + celebrity.getId());

            // Position
            BoundingBox boundingBox = celebrity.getFace().getBoundingBox();
            System.out.println("position: " + boundingBox.getLeft().toString() + " " + boundingBox.getTop().toString());
            metadata.add("position: " + boundingBox.getLeft().toString() + " " + boundingBox.getTop().toString());

            // Extra info
            System.out.println("Further information (if available):");
            metadata.add("Further information (if available):");

            for (String url: celebrity.getUrls()){
                System.out.println(url);
                metadata.add(url);
            }
            System.out.println();
        }

        System.out.println(result.getUnrecognizedFaces().size() + " face(s) were unrecognized.");
        metadata.add(result.getUnrecognizedFaces().size() + " face(s) were unrecognized.");

        return metadata;
    }
}