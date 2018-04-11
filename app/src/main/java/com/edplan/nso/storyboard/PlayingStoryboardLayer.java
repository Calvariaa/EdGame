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

public class PlayingStoryboardLayer extends EdDrawable
{
	private List<ADrawableStoryboardElement> sprites=new ArrayList<ADrawableStoryboardElement>();
	
	private int index=0;
	
	private List<ADrawableStoryboardElement> spriteInField=new ArrayList<ADrawableStoryboardElement>();
	
	private PlayingStoryboard storyboard;
	
	public PlayingStoryboardLayer(StoryboardLayer layer,PlayingStoryboard storyboard){
		super(storyboard.getContext());
		this.storyboard=storyboard;
		for(IStoryboardElements ele:layer.getElements()){
			if(ele.isDrawable()){
				ADrawableStoryboardElement drawable=ele.createDrawable(storyboard);
				sprites.add(drawable);
				ele.onApply(drawable,storyboard);
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

	public void setSprites(List<ADrawableStoryboardElement> sprites) {
		this.sprites=sprites;
	}

	public List<ADrawableStoryboardElement> getSprites() {
		return sprites;
	}
	
	public int objectInField(){
		return spriteInField.size();
	}

	protected void refreshObjects(){
		double time=storyboard.getTimeline().frameTime(); 
		
		spriteInField.clear();
		for(ADrawableStoryboardElement ele:sprites){
			if(ele.getStartTime()<=time&&ele.getEndTime()>=time){
				spriteInField.add(ele);
				if(!ele.hasAdd())ele.onAdd();
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
}
