package com.edplan.osulab.ui.scenes.songs;
import com.edplan.framework.MContext;
import com.edplan.framework.math.RectF;
import com.edplan.framework.ui.Anchor;
import com.edplan.osulab.LabGame;
import com.edplan.osulab.ScenesName;
import com.edplan.osulab.ui.scenes.BaseScene;
import com.edplan.framework.ui.ViewConfiguration;

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
		BaseBoundOverlay bound=new BaseBoundOverlay();
		RectF area=RectF.anchorOWH(Anchor.Center,
								   getRight(),
								   getBottom(),
								   ViewConfiguration.dp(200),
								   ViewConfiguration.dp(200));
		bound.setLeft(area.getLeft());
		bound.setTop(area.getTop());
		bound.setRight(area.getRight());
		bound.setBottom(area.getBottom());
		LabGame.get().getJumpingCircle().setBoundOverlay(bound);
	}

	@Override
	public String getSceneName(){
		// TODO: Implement this method
		return ScenesName.SongSelect;
	}
	
}
