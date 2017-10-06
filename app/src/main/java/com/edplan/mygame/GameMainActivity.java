package com.edplan.mygame;
import android.app.Activity;
import android.os.Bundle;
import com.edplan.simpleGame.view.BaseDatas;

public class GameMainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		BaseDatas.initial(this);

		GameSurfaceView gsv=new GameSurfaceView(this);
        setContentView(gsv);
	}
}
