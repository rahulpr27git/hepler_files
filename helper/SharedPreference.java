package com.jbmatrix.utils.helper;

/**
 * Created by android1 on 21/5/15.
 */

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreference {

    private SharedPreferences prefs;
    Context mctx;

    public SharedPreference(Context ctx) {
        // TODO Auto-generated constructor stub
        mctx = ctx;
    }

    public void write(String key, String putId, String value) {
        prefs = mctx.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.clear();
        editor.putString(putId, value);
        editor.commit();
    }

    public String read(String key, String putId) {
        prefs = mctx.getSharedPreferences(key, Context.MODE_PRIVATE);
        String id= prefs.getString(putId,"");
        return id;
    }

    public void clear(String key) {
        prefs = mctx.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}