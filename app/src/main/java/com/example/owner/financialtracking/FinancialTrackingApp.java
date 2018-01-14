package com.example.owner.financialtracking;

import android.app.Application;
import android.content.Context;

/**
 * Created by Owner on 14/01/2018.
 */

public class FinancialTrackingApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FinancialTrackingApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return FinancialTrackingApp.context;
    }
}
