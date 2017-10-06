package com.edplan.simpleGame.view.Game;
import com.edplan.simpleGame.view.Stage;
import android.graphics.Canvas;

public class Background
{
	//装载背景的stage
	public Stage stage;
	
	public Background(Stage _stage){
		setStage(_stage);
	}
	
	public void setStage(Stage _stage){
		this.stage=_stage;
	}
	
	public void drawBackground(Canvas c){
		
	}
	
	public void setAlpha(int a){
		
	}
}
