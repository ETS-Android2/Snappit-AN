package com.example.snappit_an;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    AmazonRekognitionClient amazonRekognitionClient;
    private final int GALLERY_REQ = 100;
    private final int TAKE_PHOTO_REQ = 200;


    private static final String IMDB_TOKEN = "k_l1oxwwl6";

    public void getCredential() {
        amazonRekognitionClient = new AmazonRekognitionClient(new AWSUtil());
        amazonRekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    private void openGalleryForImage() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");

        this.startActivityForResult(intent, this.GALLERY_REQ);
    }

    private void openCameraForImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent, this.TAKE_PHOTO_REQ);
    }

    public void showLoadingDialog() {
        Loader fragment = (Loader) getSupportFragmentManager().findFragmentByTag(Loader.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new Loader();
            fragment.setCancelable(false);
            getSupportFragmentManager().beginTransaction()
                    .add(fragment, Loader.FRAGMENT_TAG)
                    .commitAllowingStateLoss();

//             fragment.show(getSupportFragmentManager().beginTransaction(), Loader.FRAGMENT_TAG);
        }

    }

    public void hideLoadingDialog() {
        Loader fragment = (Loader) getSupportFragmentManager().findFragmentByTag(Loader.FRAGMENT_TAG);
        if (fragment != null) {
//             fragment.dismissAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showLoadingDialog();
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            getCredential();

            String action = getIntent().getStringExtra("ACTION");


            if (action != null && action.equals("CAMERA")) {
                openCameraForImage();
            } else {
                openGalleryForImage();
            }
        }


        ImageButton button = (ImageButton) findViewById(R.id.goBackHome);
        button.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Intent myintent = new Intent(UploadActivity.this, MainActivity.class);
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


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            showLoadingDialog();

            if (requestCode == GALLERY_REQ) {
                Uri uri = data.getData();
                ImageView imageView = (ImageView) this.findViewById(R.id.imageBox);
                imageView.setImageURI(uri);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                new ImageAsyncTask().execute(bitmap);
            } else if (requestCode == TAKE_PHOTO_REQ) {
                Bitmap srcBmp = (Bitmap) data.getExtras().get("data");
                new ImageAsyncTask().execute(srcBmp);
            }
        }
    }


    public void findCelebrity(ByteBuffer imageBytes) throws IOException, JSONException {

        if (imageBytes != null && imageBytes.hasArray()) {
            RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                    .withImage(new Image().withBytes(imageBytes));


            RecognizeCelebritiesResult result = amazonRekognitionClient.recognizeCelebrities(request);

            //Display recognized celebrity information
            List<Celebrity> celebs = result.getCelebrityFaces();

            Log.e(String.valueOf(celebs.size()), " celebrity(s) were recognized.\n");

            for ( Celebrity celebrity : celebs ) {

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

                hideLoadingDialog();
                for ( String url : celebrity.getUrls() ) {
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

    @Override
    public void onClick(View view) {
        Intent movieInfoIntent = new Intent(UploadActivity.this, MovieInfoActivity.class);
        String id = view.getTag().toString();
        movieInfoIntent.putExtra("id", id);
        startActivity(movieInfoIntent);
    }

    public void requestIMDB(String nameID) throws JSONException {
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
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            response = client.newCall(request).execute();

            if (response != null && response.body() != null) {

                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);

                TextView mTextView = (TextView) findViewById(R.id.awardsText);
                mTextView.setText(Jobject.getString("role"));

                TextView summaryTextView = (TextView) findViewById(R.id.summaryText);
                summaryTextView.setText(Jobject.getString("summary"));

                TextView awardsTextView = (TextView) findViewById(R.id.movie_title);
                awardsTextView.setText(Jobject.getString("awards"));

                ImageView actorView = (ImageView) findViewById(R.id.actorImage);
                Picasso.get().load(Uri.parse(Jobject.getString("image"))).into(actorView);


                JSONArray Jarray = Jobject.getJSONArray("knownFor");
                LinearLayout moviesLayout = (LinearLayout) findViewById(R.id.linearLayout);


                for ( int i = 0; i < Jarray.length(); i++ ) {
                    JSONObject jsonObject = Jarray.getJSONObject(i);

                    View inflatedView = inflater.inflate(R.layout.image_item, null);

                    ImageView imgView = (ImageView) inflatedView.findViewById(R.id.imageView);
                    Picasso.get().load(Uri.parse(jsonObject.getString("image"))).into(imgView);
                    imgView.setTag(jsonObject.getString("id"));
                    imgView.setOnClickListener(this);
                    moviesLayout.addView(imgView);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class ImageAsyncTask extends AsyncTask<Bitmap, Void, byte[]> {

        @Override
        protected byte[] doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap = bitmaps[0];
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);

            try {
                findCelebrity(ByteBuffer.wrap(bytes));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            hideLoadingDialog();
        }
    }
}

