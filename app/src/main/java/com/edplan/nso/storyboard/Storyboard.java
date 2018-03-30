package com.edplan.nso.storyboard;
import com.edplan.framework.math.Vec2;
import java.util.HashMap;

public class Storyboard
{
	
	private HashMap<String,StoryboardLayer> layers=new HashMap<String,StoryboardLayer>();
	
	public Storyboard(){
		layers.put(Layer.Background.name(),Layer.Background.createLayer());
		layers.put(Layer.Fail.name(),Layer.Fail.createLayer());
		layers.put(Layer.Pass.name(),Layer.Pass.createLayer());
		layers.put(Layer.Foreground.name(),Layer.Foreground.createLayer());
	}
	
	public StoryboardLayer getLayer(String name){
		return layers.get(name);
	}
	
	public enum EventObjType{
		Background(0),
		Video(1),
		Break(2),
		Colour(3),
		Sprite(4),
		Sample(5),
		Animation(6);
		private final int value;
		public EventObjType(int v){
			value=v;
		}

		public static EventObjType parse(String s){
			if(s.length()==0)return null;
			char f=s.charAt(0);
			if(f>='0'&&f<='6'){
				return EventObjType.$VALUES[f-'0'];
			}else{
				return EventObjType.valueOf(s);
			}
		}
	}
	
	public enum Layer{
		Background(3,true,true),
		Fail(2,false,true),
		Pass(1,true,false),
		Foreground(0,true,true);
		private final int depth;
		private final boolean enableWhenPassing;
		private final boolean enableWhenFailing;
		public Layer(int depth,boolean ewp,boolean ewf){
			this.depth=depth;
			this.enableWhenPassing=ewp;
			this.enableWhenFailing=ewf;
		}
		
		public StoryboardLayer createLayer(){
			return new StoryboardLayer(name(),depth,enableWhenPassing,enableWhenFailing);
		}
		
		public static Layer parse(String s){
			if(s.length()==0)return null;
			char f=s.charAt(0);
			if(f>='0'&&f<='3'){
				return Layer.$VALUES[f-'0'];
			}else{
				return Layer.valueOf(s);
			}
		}
	}
	
	public enum Origin{
		TopLeft(0,0),
		TopCentre(0.5f,0),
		TopRight(1,0),
		CentreLeft(0,0.5f),
		Centre(0.5f,0.5f),
		CentreRight(1,0.5f),
		BottomLeft(0,1),
		BottomCentre(0.5f,1),
		BottomRight(1,1);
		private final float originX,originY;
		public Origin(float x,float y){
			originX=x;
			originY=y;
		}
	}
}
