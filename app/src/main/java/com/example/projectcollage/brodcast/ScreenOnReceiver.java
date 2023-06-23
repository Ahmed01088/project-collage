package com.example.projectcollage.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.projectcollage.utiltis.Constants;

public class ScreenOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Store the value in SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.IS_LIVE, true);
        editor.apply();
    }

}