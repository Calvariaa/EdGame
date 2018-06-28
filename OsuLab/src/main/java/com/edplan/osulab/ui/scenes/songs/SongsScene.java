package com.edplan.osulab.ui.scenes.songs;
import com.edplan.framework.MContext;
import com.edplan.framework.math.RectF;
import com.edplan.framework.ui.Anchor;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.widget.RelativeLayout;
import com.edplan.framework.ui.widget.component.Hideable;
import com.edplan.osulab.LabGame;
import com.edplan.osulab.ScenesName;
import com.edplan.osulab.ui.scenes.BaseScene;
import java.util.ArrayList;
import java.util.List;

public class SongsScene extends BaseScene
{
	private BottomBar bottomBar;
	
	private SongsListView songsListView;
	
	private List<Hideable> hideableContent=new ArrayList<Hideable>();
	
	public SongsScene(MContext c){
		super(c);
		{
			songsListView=new SongsListView(c);
			RelativeLayout.RelativeParam p=new RelativeLayout.RelativeParam();
			p.width=Param.makeUpDP(SongsListView.WIDTH_DP);
			p.height=Param.MODE_MATCH_PARENT;
			p.gravity=Gravity.TopRight;
			addView(songsListView,p);
			hideableContent.add(songsListView);
		}
		{
			bottomBar=new BottomBar(c);
			RelativeLayout.RelativeParam p=new RelativeLayout.RelativeParam();
			p.width=Param.MODE_MATCH_PARENT;
			p.height=Param.makeUpDP(BottomBar.HEIGHT_DP);
			p.gravity=Gravity.BottomCenter;
			addView(bottomBar,p);
			hideableContent.add(bottomBar);
		}
	}

	public void setBottomBar(BottomBar bottomBar){
		this.bottomBar=bottomBar;
	}

	public BottomBar getBottomBar(){
		return bottomBar;
	}
	
	public static String getSceneNameStatic(){
		return ScenesName.SongSelect;
	}
	
	public static boolean isSingleInstanceStatic(){
		return true;
	}

	@Override
	public double getHideDuration(){
		// TODO: Implement this method
		return ViewConfiguration.DEFAULT_TRANSITION_TIME;
	}
	
	@Override
	public void hide(){
		// TODO: Implement this method
		for(Hideable h:hideableContent)h.hide();
	}

	@Override
	public void show(){
		// TODO: Implement this method
		for(Hideable h:hideableContent)h.show();
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
