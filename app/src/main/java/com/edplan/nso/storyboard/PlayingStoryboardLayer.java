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

public class PlayingStoryboardLayer extends EdDrawable
{
	private List<ElementNode> sprites=new ArrayList<ElementNode>();
	
	private int index=0;
	
	private List<ADrawableStoryboardElement> spriteInField=new ArrayList<ADrawableStoryboardElement>();
	
	private PlayingStoryboard storyboard;
	
	public PlayingStoryboardLayer(StoryboardLayer layer,PlayingStoryboard storyboard){
		super(storyboard.getContext());
		this.storyboard=storyboard;
		int depth=-1;
		for(IStoryboardElements ele:layer.getElements()){
			if(ele.isDrawable()){
				depth++;
				/*
				if(depth>=31593||depth<31592)continue;
				*/
				
				
				ADrawableStoryboardElement drawable=ele.createDrawable(storyboard);
				ElementNode node=new ElementNode();
				node.startTime=drawable.getStartTime();
				node.endTime=drawable.getEndTime();
				node.element=drawable;
				node.rawElement=ele;
				node.depth=sprites.size();
				//System.out.println("to "+node.depth);
				//if(node.depth%2000==0||node.depth>76500)System.out.println(node.depth);
				sprites.add(node);
				
				/*
				ele.onApply(drawable,storyboard);
				
				System.out.println(((BaseDrawableSprite)drawable).getAnimations());
				System.out.println(((StoryboardSprite)ele).rawData);
				*/
				
			}else{
				ele.onApply(null,storyboard);
			}
		}
		System.out.println(sprites.size());
		/*
		if(false){
			return;
		}
		System.out.println(sprites.get(0).getStartTime());
		*/
		/*
		*/
	}

	/*
	public List<ADrawableStoryboardElement> getSprites() {
		return sprites;
	}
	*/
	
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
		
		/*
		while(index<sprites.size()&&sprites.get(index).getStartTime()<=time){
			ADrawableStoryboardElement ele=sprites.get(index);
			spriteInField.add(ele);
			ele.onAdd();
			index++;
		}
		Iterator<ADrawableStoryboardElement> iter=spriteInField.iterator();
		while(iter.hasNext()){
			ADrawableStoryboardElement ele=iter.next();
			if(ele.getEndTime()<time){
				iter.remove();
				ele.onRemove();
			}
		}*/
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		refreshObjects();
		int c=GLWrapped.blend.save();
		for(ADrawableStoryboardElement ele:spriteInField){
			ele.draw(canvas);
		}
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
			added=true;
			rawElement.onApply(element,storyboard);
			element.onAdd();
		}
		
	}
}
