package com.sklagat46.mcrop.util;

public class Configs {
    public static final String API_AUTH_KEY = "f7efc3de9d4090974ebd2ea47c5bc68ba6667333";
    public static final String API_AUTH_HEADER = "X-Authorization";
    public static final String SESSION_AUTH_HEADER = "Authorization";
    public static final String SESSION_TOKEN_SCHEMA = "Bearer";

    public static final String PREFS_LOCATION_ID = "LOCATION_ID";

    public static final String PREFS_NAME = "lukafriscoutaAppPrefs";

    public static final String PREFS_USER_LOGGED_IN = "PS_USER_LOGGED_IN";
    public static final String PREFS_DEVICE_REGISTERED = "isREGISTERED";

    public static final String PREFS_LATITUDE_CLICKED = "LATITUDE_C";
    public static final String PREFS_LONGITUDE_CLICKED = "LONGITUDE_C";
    public static final String PREFS_LOCATION_TIME_CLICKED = "LOCATION_TIME_C";

    public static final String PREFS_LATITUDE = "LATITUDE";
    public static final String PREFS_LONGITUDE = "LONGITUDE";
    public static final String PREFS_LOCATION_TIME = "LOCATION_TIME";

    public static final String PREFS_API_TOKEN = "API_TOKEN";
    public static final String PREFS_DEVICE_ID = "DEVICE_ID";
    public static final String PRE_LANG = "PRE_LANG";

    public static final String PREFS_SPINNERITEM = "SPINNERITEM";
    public static final String PREFS_DAYDIFF = "DAYDIFF";
    public static final String PREFS_SENDER = "SENDER";

   /*public static final String PREFS_API_KEY = "API_KEY";
    public static final String PREFS_DEVICE_URI = "DEVICE_URI";*/


    //public static final String PREFS_BASE_URL = "http://afriscoutapi.ihub.co.ke";
    //public static final String PREFS_BASE_URL = "http://afriscoutapistaging.ihub.co.ke";
    public static final String PREFS_BASE_URL = "https://ansel.ihub.co.ke";


    public static final String PREFS_TEMP_IMAGE_PATH = "TEMP_IMAGE_PATH";

    public static final int STATUS_SUCCESS = 1;
    public static final String SUCCESS = "true";
    public static final String FAILURE = "false";

    public static final String IMAGES_DIRECTORY = "/AfriScout/images/";
    public static final String IMAGE_EXTENSION = ".jpg";


    public static final String PREFS_V_NUMBER = "V_NUMBER";
    public static final String PREFS_IS_VERIFIED = "IS_VERIFIED";
    public static final String PREFS_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String PREFS_SURNAME = "SURNAME";
    public static final String PREFS_OTHER_NAME = "OTHER_NAME";

    public static final String PREFS_CAMERA_PHOTO_PATH_JSON = "PS_CAMERA_PHOTO_PATH_JSON";
    public static final double DEFAULT_LATITUDE = -3.018767938449095;
    public static final double DEFAULT_LONGITUDE = 38.24385812053968;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final float DEFAULT_ZOOM = 17;

    public static final long UPDATE_INTERVAL = 10000;
    public static final long FASTEST_UPDATE_INTERVAL =
            UPDATE_INTERVAL / 2;
    public static final String PREFS_CHANGE_COUNT = "change_count";
    public static final String PREFS_MAX_CHANGE_COUNT = "max_change_count";
    public static final String PREFS_COUNTRY_CODE = "country_code";
    public static final String PREFS_PRIVILEGES = "privilege";
    public static final String PREFS_PREFERED_LOCATION_ID = "preferred_location";
    public static String PREFS_MY_LOCATION = "myLocation";

	/*public static String cleanString(String string){
		string = string.replaceAll("\n|\r\n|\\n|\\r\\n", "#");

		string = string.replaceAll("\"", "'");

		return string;
	}*/
}
