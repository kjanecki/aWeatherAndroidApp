package com.marcinkaminski.aWeather.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

//Class that handles asynchronous requests that were previously scheduled
public class AppJobService extends JobService {
    private AsyncTask<Void, Void, Void> mFetchWeatherTask;

//    Any such logic needs to be performed on a separate thread, as this function is executed on your application's main thread.
//    Returns true if your service needs to process the work (on a separate thread). False if there's no more work to be done for this job.
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mFetchWeatherTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                AppSyncTask.syncWeather(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchWeatherTask.execute();
        return true;
    }

//  True to indicate to the JobManager whether you'd like to reschedule this job based on
//  the retry criteria provided at job creation-time. False to drop the job. Regardless of the
//  value returned, your job must stop executing.
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchWeatherTask != null) {
            mFetchWeatherTask.cancel(true);
        }
        return true;
    }
}