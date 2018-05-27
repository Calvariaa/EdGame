package com.edplan.fontawesome;

import android.app.*;
import android.os.*;
import com.edplan.framework.main.MainActivity;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.MContext;

public class AwesomeActivity extends MainActivity 
{
	AwesomeApplication app;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app=new AwesomeApplication();
		app.setUpActivity(this);
    }
	
	public class AwesomeApplication extends MainApplication
	{
		@Override
		public MainRenderer createRenderer(MContext c){
			// TODO: Implement this method
			return new AwesomeRenderer(c,this);
		}
	}
}
