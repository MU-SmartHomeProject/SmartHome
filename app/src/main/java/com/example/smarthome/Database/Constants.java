package com.example.smarthome.Database;

import android.net.Uri;

public class Constants {


    public static final String AUTHORITY = "com.yoshitha.smarthomemanageradmin.provider";
    public static final String CONTENT_PATH = "devices";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    // Fields for device table
    public static final String DEVICE_TABLE = "devices";

    public static final String USER_NAME        = "username";
    public static final String DEVICE_ID        = "id";
    public static final String DEVICE_NAME      = "name";
    public static final String DEVICE_TYPE      = "type";
    public static final String DEVICE_STATUS    = "status";
    public static final String DEVICE_INTENSITY = "intensity";
    public static final String DEVICE_COLOR     = "color";



}
