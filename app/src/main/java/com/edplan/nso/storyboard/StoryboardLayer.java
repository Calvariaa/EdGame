package com.edplan.nso.storyboard;
import com.edplan.nso.storyboard.elements.IStoryboardElements;
import java.util.ArrayList;
import java.util.List;

public class StoryboardLayer
{
	private String name;
	private int depth;
	private boolean enableWhenPassing=true;
	private boolean enableWhenFailing=true;
	
	private List<IStoryboardElements> elements=new ArrayList<IStoryboardElements>();
	
	public StoryboardLayer(String name,int depth,boolean enableWhenPassing,boolean enableWhenFailing){
		this.name=name;
		this.depth=depth;
		this.enableWhenPassing=enableWhenPassing;
		this.enableWhenFailing=enableWhenFailing;
	}
	
	public void add(IStoryboardElements e){
		elements.add(e);
	}
	
}
