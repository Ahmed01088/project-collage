package com.example.projectcollage.firebase;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManger {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public PreferencesManger(Context context){
       preferences=context.getSharedPreferences(Constants.KEY_PREFERENCES_NAME,Context.MODE_PRIVATE);
        editor=preferences.edit();
    }
   public void putBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }
   public Boolean getBoolean(String key){
        return preferences.getBoolean(key,false);
    }
    public void putString(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }
    public String getString(String key){
        return preferences.getString(key,"");
    }
    public void clearPreferences(){
        editor.clear();
        editor.apply();
    }
}
