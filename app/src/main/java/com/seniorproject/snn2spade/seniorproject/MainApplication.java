package com.seniorproject.snn2spade.seniorproject;

import android.app.Application;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;

/**
 * Created by snn2spade on 3/15/2017 AD.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
