package com.sklagat46.mcrop.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

public class SharedPreferenceManager {
    private static final String TAG = "SharedPreferenceManager";

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(Configs.PREFS_NAME, Context.MODE_MULTI_PROCESS);
    }


    public static Boolean isSignedIn(Context context) {
        return getSharedPreference(context).getBoolean(Configs.PREFS_USER_LOGGED_IN, false);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void reset(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.clear();
        editor.commit();
    }

    public static void saveTemporaryImagePath(Context context, String imagePath) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_TEMP_IMAGE_PATH, imagePath);
        editor.apply();
    }

    public static String getTemporaryImagePath(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_TEMP_IMAGE_PATH, "");
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(
                        context.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }




   /* public static void setPrivileges(Context context, String s) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_PRIVILEGES, s);
        editor.apply();
    }

    public static String getPrivileges(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_PRIVILEGES, "");
    }*/

}
