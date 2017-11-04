package com.edplan.mygame;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.edplan.mygame.acts.MainGamePage;
import com.edplan.simpleGame.view.BaseDatas;

public class GameMainActivity extends Activity
{
	MainGamePage mainPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		BaseDatas.initial(this);

		GameSurfaceView gsv=new GameSurfaceView(this);
		gsv.setClearColor(0xFF333333);
        setContentView(gsv);
		mainPage=new MainGamePage(gsv.getGameContext(),gsv);
		gsv.setContent(mainPage);
		
		
		//Log.v("base_data",BaseDatas.dpToPixel(1)+"");
	}
}
