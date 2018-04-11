package com.edplan.nso.storyboard;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.nso.storyboard.StoryboardLayer;
import java.util.HashMap;
import java.util.Map;

public class PlayingStoryboard extends EdDrawable
{
	private HashMap<String,PlayingStoryboardLayer> layers=new HashMap<String,PlayingStoryboardLayer>();
	
	private PreciseTimeline timeline;
	
	public PlayingStoryboard(MContext context,PreciseTimeline timeline,Storyboard storyboard){
		super(context);
		this.timeline=timeline;
		for(Map.Entry<String,StoryboardLayer> l:storyboard.getLayers().entrySet()){
			layers.put(l.getKey(),new PlayingStoryboardLayer(l.getValue(),this));
		}
	}

	public void setTimeline(PreciseTimeline timeline) {
		this.timeline=timeline;
	}

	public PreciseTimeline getTimeline() {
		return timeline;
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		for(Map.Entry<String,PlayingStoryboardLayer> e:layers.entrySet()){
			e.getValue().draw(canvas);
		}
	}
}
