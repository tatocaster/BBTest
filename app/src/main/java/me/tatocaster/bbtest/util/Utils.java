package me.tatocaster.bbtest.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tatocaster on 8/30/17.
 */

public class Utils {
    public static String readJSONFromAsset(Context context, String filename) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public static InputStream readFileFromAssets(Context context, String filename) {
        AssetManager assManager = context.getAssets();
        InputStream is = null;
        try {
            is = assManager.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
