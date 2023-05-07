package com.example.projectcollage.utiltis;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtils {
    public static String fromMillisToTimeString(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return format.format(millis);
    }
}