package com.edplan.osulab.ui.scenes.songs;
import com.edplan.osulab.ui.scenes.BaseScene;
import com.edplan.framework.MContext;
import com.edplan.osulab.ScenesName;

public class SongsScene extends BaseScene
{
	public SongsScene(MContext c){
		super(c);
	}
	
	public static String getSceneNameStatic(){
		return ScenesName.SongSelect;
	}
	
	public static boolean isSingleInstanceStatic(){
		return true;
	}
	
	@Override
	public void hide(){
		// TODO: Implement this method
	}

	@Override
	public void show(){
		// TODO: Implement this method
	}

	@Override
	public String getSceneName(){
		// TODO: Implement this method
		return ScenesName.SongSelect;
	}
	
}
