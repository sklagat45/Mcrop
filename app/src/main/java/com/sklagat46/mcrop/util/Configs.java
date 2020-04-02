package com.sklagat46.mcrop.util;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Configs {
    public static final String PREFS_NAME = "lukafriscoutaAppPrefs";
    public static final String PREFS_USER_LOGGED_IN = "PS_USER_LOGGED_IN";
    public static final String PREFS_TEMP_IMAGE_PATH = "TEMP_IMAGE_PATH";
    public static final String IMAGES_DIRECTORY = "/Mcrop/images/";
    public static final long UPDATE_INTERVAL = 10000;
    public static final String SIMPLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final long FASTEST_UPDATE_INTERVAL =
            UPDATE_INTERVAL / 2;

    public static String getCurrentDateTime() {
        SimpleDateFormat mSDF = new SimpleDateFormat(SIMPLE_DATE_TIME_FORMAT,
                Locale.getDefault());
        return mSDF.format(new Date());
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
}
