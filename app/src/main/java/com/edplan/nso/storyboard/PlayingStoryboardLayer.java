package com.edplan.nso.storyboard;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.timing.PreciseTimeline;
import java.util.List;
import com.edplan.nso.storyboard.elements.drawable.BaseDrawableSprite;
import java.util.ArrayList;
import com.edplan.nso.storyboard.elements.IStoryboardElements;
import com.edplan.nso.storyboard.elements.drawable.ADrawableStoryboardElement;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.nso.storyboard.elements.StoryboardSprite;
import com.edplan.framework.fallback.GLES10Drawable;
import com.edplan.framework.graphics.opengl.GL10Canvas2D;

public class PlayingStoryboardLayer extends EdDrawable implements GLES10Drawable
{
	private List<ElementNode> sprites=new ArrayList<ElementNode>();
	
	private List<ElementNode> spritesNotAdded=new ArrayList<ElementNode>();
	
	private int index=0;
	
	private List<ADrawableStoryboardElement> spriteInField=new ArrayList<ADrawableStoryboardElement>();
	
	private PlayingStoryboard storyboard;
	
	private boolean preApplyMode=false;
	
	private int newApply=0;
	
	public PlayingStoryboardLayer(StoryboardLayer layer,PlayingStoryboard storyboard){
		super(storyboard.getContext());
		this.storyboard=storyboard;
		int depth=-1;
		//int maxDepth=30000;
		for(IStoryboardElements ele:layer.getElements()){
			if(ele.isDrawable()){
				depth++;
				ADrawableStoryboardElement drawable=ele.createDrawable(storyboard);
				ElementNode node=new ElementNode();
				node.startTime=drawable.getStartTime();
				node.endTime=drawable.getEndTime();
				node.element=drawable;
				node.rawElement=ele;
				node.depth=sprites.size();
				sprites.add(node);
				
				
				if(preApplyMode)ele.onApply(drawable,storyboard);
				/*
				System.out.println(((BaseDrawableSprite)drawable).getAnimations());
				System.out.println(((StoryboardSprite)ele).rawData);
				*/
				
				
			}else{
				ele.onApply(null,storyboard);
			}
		}
		spritesNotAdded.addAll(sprites);
		System.out.println(sprites.size());
	}

	public int getNewApply() {
		return newApply;
	}
	
	public int objectInField(){
		return spriteInField.size();
	}

	protected void refreshObjects(){
		double time=storyboard.getTimeline().frameTime(); 
		
		spriteInField.clear();
		for(ElementNode ele:sprites){
			if(ele.startTime<=time&&ele.endTime>=time){
				spriteInField.add(ele.element);
				if(!ele.hasAdded()){
					ele.apply();
				}
			}
		}
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		newApply=0;
		refreshObjects();
		int c=GLWrapped.blend.save();
		canvas.setEnablePost(true);
		for(ADrawableStoryboardElement ele:spriteInField){
			ele.draw(canvas);
		}
		canvas.postDraw();
		canvas.setEnablePost(false);
		GLWrapped.blend.restoreToCount(c);
	}

	@Override
	public void drawGL10(GL10Canvas2D canvas) {
		// TODO: Implement this method
		newApply=0;
		refreshObjects();
		int c=GLWrapped.blend.save();
		//canvas.setEnablePost(true);
		for(ADrawableStoryboardElement ele:spriteInField){
			ele.drawGL10(canvas);
		}
		canvas.postDraw();
		//canvas.setEnablePost(false);
		GLWrapped.blend.restoreToCount(c);
	}
	
	public class ElementNode{
		public int depth;
		public double startTime;
		public double endTime;
		public ADrawableStoryboardElement element;
		public IStoryboardElements rawElement;
		
		boolean added=false;
	
		public boolean hasAdded(){
			return added;
		}
		
		public void apply(){
			newApply++;
			added=true;
			if(!preApplyMode)rawElement.onApply(element,storyboard);
			element.onAdd();
		}
		
	}
	
	
	public class ApplyThread extends Thread{
		
		
	}
	
}
