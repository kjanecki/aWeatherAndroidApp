package com.marcinkaminski.aWeather.sync;

import android.app.IntentService;
import android.content.Intent;
// Class for Services that handle asynchronous requests (expressed as Intents) on demand.
// Clients send requests through startService(Intent) calls;
// the service is started as needed, handles each Intent in turn using a worker
// thread, and stops itself when it runs out of work.
public class AppSyncIntentService extends IntentService {
    public AppSyncIntentService() {
        super("AppSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppSyncTask.syncWeather(this);
    }
}