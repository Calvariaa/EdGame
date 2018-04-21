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
import java.util.LinkedList;
import com.edplan.framework.graphics.opengl.BaseCanvas;

public class PlayingStoryboardLayer extends EdDrawable implements GLES10Drawable
{
	private List<ElementNode> sprites=new ArrayList<ElementNode>();
	
	private List<ElementNode> applyNode=new LinkedList<ElementNode>();
	
	private List<ElementNode> spritesNotAdded=new LinkedList<ElementNode>();
	
	private int index=0;
	
	private List<ElementNode> spriteInField=new ArrayList<ElementNode>();
	
	private PlayingStoryboard storyboard;
	
	private boolean preApplyMode=false;
	
	private int newApply=0;
	
	private ApplyThread applyThread;
	
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
		Collections.sort(sprites, new Comparator<ElementNode>(){
				@Override
				public int compare(PlayingStoryboardLayer.ElementNode p1,PlayingStoryboardLayer.ElementNode p2) {
					// TODO: Implement this method
					return (int)Math.signum(p1.startTime-p2.startTime);
				}
			});
		spritesNotAdded.addAll(sprites);
		applyNode.addAll(sprites);
		sprites.clear();
		
		applyThread=new ApplyThread();
		applyThread.start();
		//System.out.println(sprites.size());
	}

	public int getNewApply() {
		return newApply;
	}
	
	public int objectInField(){
		return spriteInField.size();
	}

	private static Comparator<ElementNode> nodeSorter=new Comparator<ElementNode>(){
		@Override
		public int compare(PlayingStoryboardLayer.ElementNode p1,PlayingStoryboardLayer.ElementNode p2) {
			// TODO: Implement this method
			return p1.depth-p2.depth;
		}
	};
	protected void refreshObjects(){
		double time=storyboard.getTimeline().frameTime(); 
		//applyThread.refreshCurrentTime(time);
		Iterator<ElementNode> iter=spritesNotAdded.iterator();
		ElementNode ele;
		while(iter.hasNext()){
			ele=iter.next();
			if(ele.startTime<=time){
				spriteInField.add(ele);
				if(!ele.hasAdded())ele.apply();
				iter.remove();
			}else{
				break;
			}
		}
		iter=spriteInField.iterator();
		while(iter.hasNext()){
			ele=iter.next();
			if(ele.endTime<time){
				ele.onRemove();
				iter.remove();
			}
		}
		Collections.sort(spriteInField,nodeSorter);
	}
	
	@Override
	public void draw(BaseCanvas canvas) {
		// TODO: Implement this method
		newApply=0;
		refreshObjects();
		int c=canvas.getBlendSetting().save();
		canvas.enablePost();
		for(ElementNode ele:spriteInField){
			ele.element.draw(canvas);
		}
		canvas.disablePost();
		canvas.getBlendSetting().restoreToCount(c);
	}

	@Override
	public void drawGL10(GL10Canvas2D canvas) {
		// TODO: Implement this method
		newApply=0;
		refreshObjects();
		int c=GLWrapped.blend.save();
		//canvas.setEnablePost(true);
		for(ElementNode ele:spriteInField){
			ele.element.drawGL10(canvas);
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
		
		public synchronized void apply(){
			if(added)return;
			newApply++;
			added=true;
			if(!preApplyMode)rawElement.onApply(element,storyboard);
			element.onAdd();
		}
		
		public void onRemove(){
			element.onRemove();
		}
		
	}
	
	
	public class ApplyThread extends Thread{
		
		private double currentTime;
		
		private double preApplyTime=2000;

		@Override
		public void run() {
			// TODO: Implement this method
			super.run();
			Iterator<ElementNode> iter=applyNode.iterator();
			ElementNode node;
			while(iter.hasNext()){
				node=iter.next();
				while(node.startTime>currentTime+preApplyTime){
					try {
						sleep(10);
					} catch (InterruptedException e) {}
				}
				if(!node.hasAdded())node.apply();
				iter.remove();
			}
		}
		
		public void refreshCurrentTime(double time){
			currentTime=time;
		}
		
	}
	
}
