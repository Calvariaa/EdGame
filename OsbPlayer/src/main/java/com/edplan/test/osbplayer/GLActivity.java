package com.edplan.test.osbplayer;

import android.app.Activity;
import android.os.Bundle;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.Toast;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;
import com.squareup.leakcanary.LeakCanary;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.MContext;
import com.edplan.framework.main.MainActivity;

public class GLActivity extends MainActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		OsbApplication app=new OsbApplication();
		app.setUpActivity(this);
	}
	
	public class OsbApplication extends MainApplication
	{
		@Override
		public MainRenderer createRenderer(MContext c){
			// TODO: Implement this method
			Bundle data=getIntent().getExtras();
			try
			{
				return new OsbRenderer(c, this, new JSONObject(data.getString(StaticData.InitialJSON)));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
				Toast.makeText(GLActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
				return null;
			}
		}
	}
}
