package com.example.logo.util;

import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Bitmap {


    public static android.graphics.Bitmap getBitmapFromString(String str) {
        byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
        return getBitmapFromByteArray(decodedString);
    }

    public static String getStringFromBitmap(android.graphics.Bitmap img){
        return Base64.encodeToString(getBytesFromBitmap(img), Base64.DEFAULT);
    }

    public static android.graphics.Bitmap getBitmapFromByteArray(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
    }

    public static byte[] getBytesFromBitmap(android.graphics.Bitmap bitmap){
        final int COMPRESSION_QUALITY = 100;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY, byteArrayBitmapStream);
        return byteArrayBitmapStream.toByteArray();
    }
}
