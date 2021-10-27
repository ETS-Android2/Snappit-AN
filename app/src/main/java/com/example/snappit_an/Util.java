package com.example.snappit_an;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Util {
    public static void setImage(String path, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }

    public static boolean readSharedSetting(Onboarding onboarding, String prefUserFirstTime, String aTrue) {
        return false;
    }
}
