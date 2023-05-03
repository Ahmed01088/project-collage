package com.example.projectcollage.firebase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectcollage.utiltis.Constants;

public class PreferencesManger {
   public static final String KEY_ACCESS_TOKEN="token";
   private Context context;
   private static PreferencesManger instance;

    private PreferencesManger(Context context) {
        this.context = context;
    }
    public static synchronized PreferencesManger getInstance(Context context){
        if (instance==null){
            instance=new PreferencesManger(context);
        }
        return instance;
    }
    public boolean storeToken(String token){
        SharedPreferences preferences=context.getSharedPreferences(Constants.DATA,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(KEY_ACCESS_TOKEN,token);
        editor.apply();
        return true;
    }
    public String getToken(){
        SharedPreferences preferences=context.getSharedPreferences(Constants.DATA,Context.MODE_PRIVATE);
        return preferences.getString(KEY_ACCESS_TOKEN,null);
    }

}
