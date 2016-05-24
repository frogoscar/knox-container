package com.samsung.knox.samples.containerlwc.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.samsung.knox.samples.containerlwc.utils.SACodeUtils;


public class SAApplication extends Application {

	public static final String TAG = SAApplication.class.getSimpleName();
	public static Context mAppContext;

	public static final boolean IS_DEBUG = true;
	public static final boolean IS_INFO = true;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAppContext = getApplicationContext();
		// populating the error codes
		SACodeUtils.populateCodes();
		
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, 
                                          Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

			@Override
			public void onActivityDestroyed(Activity arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityPaused(Activity arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityResumed(Activity arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityStarted(Activity arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityStopped(Activity arg0) {
				// TODO Auto-generated method stub
				
			}


        });
		
	}

	public static Context getAppContext() {
		return mAppContext;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
