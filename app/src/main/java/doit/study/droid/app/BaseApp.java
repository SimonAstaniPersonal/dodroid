package doit.study.droid.app;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import doit.study.droid.R;
import doit.study.droid.data.QuizDBHelper;

/**
 * Use application singleton
*/
public abstract class BaseApp extends Application {
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        createDB();
    }

    protected void createDB(){
        new Thread(){
            @Override
            public void run() {
                QuizDBHelper helper = new QuizDBHelper(BaseApp.this);
                helper.getReadableDatabase();
                helper.close();
            }
        }.start();
    }


    // Get the tracker associated with this app
    public void startTracking() {

        // Initialize an Analytics tracker using a Google Analytics property ID.

        // Does the Tracker already exist?
        // If not, create it
        if (mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);

            // Get the config data for the tracker
            mTracker = ga.newTracker(R.xml.track_app);

            // Enable tracking of activities
            ga.enableAutoActivityReports(this);
        }
    }

    public Tracker getTracker() {
        // Make sure the tracker exists
        startTracking();

        // Then return the tracker
        return mTracker;
    }
}
