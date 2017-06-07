package com.bayue.live.deqingpu.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/6/5.
 */

public class Preferences {

    public  static final String TOKEN="token";

    public  static void saveString (Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences("",context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String getString ( Context context,String key){
        SharedPreferences sp=context.getSharedPreferences("",context.MODE_PRIVATE);
        return sp.getString(key,"-1");
    }

}
