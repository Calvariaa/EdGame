package com.edplan.framework.main;

import android.app.Activity;

public class MainActivity extends Activity
{
	private MainApplication app;
	
	public void register(MainApplication app){
		this.app=app;
	}

	@Override
	protected void onPause(){
		// TODO: Implement this method
		super.onPause();
		app.onPause();
	}

	@Override
	protected void onResume(){
		// TODO: Implement this method
		super.onResume();
		app.onResume();
	}

	@Override
	public void onLowMemory(){
		// TODO: Implement this method
		super.onLowMemory();
		app.onLowMemory();
	}

	@Override
	public void onBackPressed(){
		// TODO: Implement this method
		app.onBackPressed();
	}
}
