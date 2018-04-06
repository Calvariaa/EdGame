package com.edplan.nso.storyboard.elements;

public class StoryboardSample implements IStoryboardElements
{
	private String path;
	
	private double time;
	private float volume;
	
	public StoryboardSample(String path,double time,float volume){
		this.path=path;
		this.time=time;
		this.volume=volume;
	}

	public void setTime(double time) {
		this.time=time;
	}

	public double getTime() {
		return time;
	}

	public void setVolume(float volume) {
		this.volume=volume;
	}

	public float getVolume() {
		return volume;
	}

	@Override
	public boolean isDrawable() {
		// TODO: Implement this method
		return false;
	}
	
	public void setPath(String path){
		this.path=path;
	}

	@Override
	public String getPath() {
		// TODO: Implement this method
		return path;
	}

}