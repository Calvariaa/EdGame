package com.edplan.osulab;

import android.app.*;
import android.os.*;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.MContext;
import com.edplan.nso.OsuFilePart;

public class LabActivity extends Activity 
{
	private OsuLabApplication app;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		app=new OsuLabApplication();
		app.setUpActivity(this);
    }
	
	public class OsuLabApplication extends MainApplication
	{
		@Override
		public MainRenderer createRenderer(MContext context){
			// TODO: Implement this method
			return new OsuLabMainRenderer(context,this);
		}
	}
}
