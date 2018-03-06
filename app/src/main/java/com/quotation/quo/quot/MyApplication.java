package com.quotation.quo.quot;

import android.app.Application;

import java.util.Locale;

/**
 * Created by Rezeq on 3/6/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class MyApplication extends Application {

    public static String language;

    @Override
    public void onCreate() {
        super.onCreate();
        language = Locale.getDefault().getLanguage();
    }
}
