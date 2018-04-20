package com.edplan.test.osbplayer;

import android.app.Activity;
import android.os.Bundle;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.Toast;
import com.edplan.framework.GameActivity;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;

public class GLActivity extends GameActivity 
{

	BaseGLSurfaceView sv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.mains);
		
	}

	@Override
	public BaseGLSurfaceView createGameSurface() {
		// TODO: Implement this method
		try {
			Bundle data=this.getIntent().getExtras();
			return new OsbGLSurfaceView(
				this,
				new OsbRenderer(this,new JSONObject(data.getString(StaticData.InitialJSON))));
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
			return null;
		}
	}
}
