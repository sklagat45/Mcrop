package com.sklagat46.mcrop.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 1/9/16.
 */
public class SharedPreferenceManager {
    private static final String TAG = "SharedPreferenceManager";

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(Configs.PREFS_NAME, Context.MODE_MULTI_PROCESS);
    }

    public static void saveLanguageSpinnerItem(Context context, int item) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(Configs.PREFS_SPINNERITEM, item);
        editor.apply();
    }

    public static int getLanguageSpinnerItem(Context context) {
        return getSharedPreference(context).getInt(Configs.PREFS_SPINNERITEM, 0);
    }

    public static void saveAPIToken(Context context, String APIToken) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_API_TOKEN, APIToken);
        editor.apply();
    }

    public static void saveVnumber(Context context, String Vnumber) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_V_NUMBER, Vnumber);
        editor.apply();
    }

    public static String getVnumber(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_V_NUMBER, "");
    }

    public static void savePhoneNumber(Context context, String phone_number) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_PHONE_NUMBER, phone_number);
        editor.apply();
    }

    public static String getPhoneNumber(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_PHONE_NUMBER, "");
    }

    public static void saveSurname(Context context, String surname) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_SURNAME, surname);
        editor.apply();
    }

    public static String getSurname(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_SURNAME, "");
    }

    public static void saveOthername(Context context, String surname) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_OTHER_NAME, surname);
        editor.apply();
    }

    public static String getOthername(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_OTHER_NAME, "");
    }

    public static void saveIsVerified(Context context, Boolean Vnumber) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Configs.PREFS_IS_VERIFIED, Vnumber);
        editor.apply();
    }

    public static Boolean getIsVerified(Context context) {
        return getSharedPreference(context).getBoolean(Configs.PREFS_IS_VERIFIED, false);
    }

    public static String getAPIToken(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_API_TOKEN, "");
    }

    public static void saveDeviceID(Context context, String device_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_DEVICE_ID, device_id);
        editor.apply();
    }

    public static Boolean getChooseMyLocation(Context context) {
        return getSharedPreference(context).getBoolean(Configs.PREFS_MY_LOCATION, false);
    }

    public static void saveChooseMyLocation(Context context, Boolean myChooselocation) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Configs.PREFS_MY_LOCATION, myChooselocation);
        editor.apply();
    }

    public static String getDeviceID(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_DEVICE_ID, "");
    }

    public static void savepre_lang(Context context, String pr_lang) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PRE_LANG, pr_lang);
        editor.apply();
    }

    public static String getpre_lang(Context context) {
        return getSharedPreference(context).getString(Configs.PRE_LANG, "");
    }

    public static void saveLocationID(Context context, String location_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_LOCATION_ID, location_id);
        editor.apply();
    }

    public static String getLocationID(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_LOCATION_ID, "");
    }

    public static void savePrefferedLocationID(Context context, String location_id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_PREFERED_LOCATION_ID, location_id);
        editor.apply();
    }

    public static String getPrefferedLocationID(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_PREFERED_LOCATION_ID, "");
    }


    public static Location getLocation(Context context) {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(getSharedPreference(context).getFloat(Configs.PREFS_LATITUDE, 0));
        location.setLongitude(getSharedPreference(context).getFloat(Configs.PREFS_LONGITUDE, 0));
        location.setTime(getSharedPreference(context).getLong(Configs.PREFS_LOCATION_TIME, 0));
        Log.e(TAG, location.toString());
        return location;
    }

    public static void saveLocation(Context context, Location location) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Configs.PREFS_LATITUDE, (float) location.getLatitude());
        editor.putFloat(Configs.PREFS_LONGITUDE, (float) location.getLongitude());
        editor.putLong(Configs.PREFS_LOCATION_TIME, location.getTime());
        editor.apply();
    }

    public static LatLng getClickedLocation(Context context) {

        LatLng latLng = new LatLng(getSharedPreference(context).getFloat(Configs.PREFS_LATITUDE_CLICKED, 0),
                getSharedPreference(context).getFloat(Configs.PREFS_LONGITUDE_CLICKED, 0));
        Log.d("Saved clicked prefs:", "" + latLng);
        return latLng;
    }

    public static void saveClickedLocation(Context context, LatLng latLng) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Configs.PREFS_LATITUDE_CLICKED, (float) latLng.latitude);
        editor.putFloat(Configs.PREFS_LONGITUDE_CLICKED, (float) latLng.longitude);
        editor.apply();
    }

    public static void saveCurrentsender(Context context, String sender) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_SENDER, sender);
        editor.apply();
    }

    public static String getCurrentsender(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_SENDER, "");
    }

    public static void saveClickedLat(Context context, Float lat) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Configs.PREFS_LATITUDE_CLICKED, lat);
        editor.apply();
    }

    public static void saveClickedLng(Context context, Float lng) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Configs.PREFS_LONGITUDE_CLICKED, lng);
        editor.apply();
    }

    public static Boolean isRegistered(Context context) {
        return getSharedPreference(context).getBoolean(Configs.PREFS_DEVICE_REGISTERED, false);
    }

    public static void setChangeCount(Context context, int count) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(Configs.PREFS_CHANGE_COUNT, count);
        editor.commit();
    }

    public static int getChangeCount(Context context) {
        return getSharedPreference(context).getInt(Configs.PREFS_CHANGE_COUNT, -1);
    }

    public static void setCountryCode(Context context, String code) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_COUNTRY_CODE, code);
        editor.commit();
    }

    public static String getCountryCode(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_COUNTRY_CODE, "");
    }

    public static void setMaxChangeCount(Context context, int count) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(Configs.PREFS_MAX_CHANGE_COUNT, count);
        editor.commit();
    }

    public static int getMaxChangeCount(Context context) {
        return getSharedPreference(context).getInt(Configs.PREFS_MAX_CHANGE_COUNT, 3);
    }

    public static void setDeviceRegisteredStatus(Context context, Boolean status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Configs.PREFS_DEVICE_REGISTERED, status);
        editor.commit();
    }

    public static Boolean isSignedIn(Context context) {
        return getSharedPreference(context).getBoolean(Configs.PREFS_USER_LOGGED_IN, false);
    }

    public static void setSignInStatus(Context context, Boolean status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Configs.PREFS_USER_LOGGED_IN, status);
        editor.commit();
    }


    public static String getCurrentTime() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }

    public static Long getCurrentTimeStamp() {

        long tsLong = System.currentTimeMillis() / 1000L;

        return tsLong;
    }


    public static String formatTimestampToMonth(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String date = DateFormat.format("MM", cal).toString();
        return date;
    }

    public static String formatTimestampToMonthDay(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd", cal).toString();
        return date;
    }

    public static String changeDateFormat(String dateformatString) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM/dd h:mm a");
        Date date = null;
        String formattedDate = null;
        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public static String changeDateFormatToTime(String dateformatString) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        SimpleDateFormat targetFormat = new SimpleDateFormat("h:mm a");
        Date date = null;
        String formattedDate = null;
        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public static String GetDateMonth(String dateformatString) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM");
        Date date = null;
        String formattedDate = null;
        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public static String GetDateDayTime(String dateformatString) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd, yy h:mm a");
        Date date = null;
        String formattedDate = null;
        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public static String SubscriptionExpiryDate(String dateformatString) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        //SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd, yyyy");
        Date date = null;
        String formattedDate = null;
        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        formattedDate = targetFormat.format(date);
        return formattedDate;
    }


    public static Long formatDateToTimeStamp(String dateformatString) {
        Long timestamp;
        Date date = null;

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //timestamp = String.valueOf(date.getTime()/1000);
        timestamp = date.getTime();
        //timestamp = (date.getTime()/1000);
        return timestamp;
    }

    public static Long DateToTimeStamp(String dateformatString) {
        Long timestamp;
        Date date = null;

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        timestamp = date.getTime();
        return timestamp;
    }

    public static Long alertExpiryDate(String dateformatString) {
        Long days;
        Date date = null;

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(date);
        /*thatDay.set(Calendar.DAY_OF_MONTH,25);
        thatDay.set(Calendar.MONTH,7); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 1985);*/

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();

        days = diff / (24 * 60 * 60 * 1000);
        return days;
    }

    public static Long Datediff(String dateformatString) {
        Long days;
        Date date = null;

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = originalFormat.parse(dateformatString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(date);
        /*thatDay.set(Calendar.DAY_OF_MONTH,25);
        thatDay.set(Calendar.MONTH,7); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 1985);*/

        Calendar today = Calendar.getInstance();

        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();

        days = diff / (24 * 60 * 60 * 1000);
        //timestamp = String.valueOf(date.getTime()/1000);
        return days;
    }


    //DAY diff
    public static void saveDayDiff(Context context, String daydiff) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_DAYDIFF, daydiff);
        editor.apply();
    }

    public static String getDaydiff(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_DAYDIFF, "");
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getRssiValue(Context context) {
        String rssi = "";
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            for (final CellInfo info : tm.getAllCellInfo()) {
                if (info instanceof CellInfoGsm) {
                    final CellSignalStrengthGsm gsm = ((CellInfoGsm) info)
                            .getCellSignalStrength();
                    rssi = "" + gsm.getDbm();
                } else if (info instanceof CellInfoCdma) {
                    final CellSignalStrengthCdma cdma = ((CellInfoCdma) info)
                            .getCellSignalStrength();
                    rssi = "" + cdma.getDbm();
                } else if (info instanceof CellInfoWcdma) {
                    final CellSignalStrengthWcdma wcdma = ((CellInfoWcdma) info)
                            .getCellSignalStrength();
                    rssi = "" + wcdma.getDbm();
                } else if (info instanceof CellInfoLte) {
                    final CellSignalStrengthLte lte = ((CellInfoLte) info)
                            .getCellSignalStrength();
                    rssi = "" + lte.getDbm();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rssi;
    }


    public static void setPrivileges(Context context, String s) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Configs.PREFS_PRIVILEGES, s);
        editor.apply();
    }

    public static String getPrivileges(Context context) {
        return getSharedPreference(context).getString(Configs.PREFS_PRIVILEGES, "");
    }

}
