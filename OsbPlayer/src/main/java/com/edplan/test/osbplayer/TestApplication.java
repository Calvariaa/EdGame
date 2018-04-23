package com.edplan.test.osbplayer;
import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

public class TestApplication extends Application
{

	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		LeakCanary.install(this);
	}
	
}
