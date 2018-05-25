package com.edplan.osulab;

import android.app.*;
import android.os.*;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.MContext;
import com.edplan.nso.OsuFilePart;
import com.edplan.framework.main.MainActivity;
import com.edplan.osulab.ui.BackQuery;

public class LabActivity extends MainActivity 
{
	private LabApplication app;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		app=new LabApplication();
		app.setUpActivity(this);
    }
	
	
	
	public class LabApplication extends MainApplication
	{
		@Override
		public MainRenderer createRenderer(MContext context){
			// TODO: Implement this method
			return new LabMainRenderer(context,this);
		}

		@Override
		public boolean onBackPressNotHandled(){
			// TODO: Implement this method
			return BackQuery.get().back();
		}
	}
}
