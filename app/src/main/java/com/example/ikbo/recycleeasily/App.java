package com.example.ikbo.recycleeasily;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by Dmitry on 01.12.2016.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
