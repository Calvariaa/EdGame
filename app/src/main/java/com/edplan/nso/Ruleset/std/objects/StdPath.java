package com.edplan.nso.Ruleset.std.objects;
import com.edplan.superutils.Math.Vct2;
import java.util.List;
import java.util.ArrayList;

public class StdPath
{
	private Type type;
	private List<Vct2<Integer,Integer>> controlPoints;
	
	public StdPath(){
		controlPoints=new ArrayList<Vct2<Integer,Integer>>();
	}
	
	public void addControlPoint(Vct2<Integer,Integer> p){
		controlPoints.add(p);
	}
	
	public void addControlPoint(int x,int y){
		addControlPoint(new Vct2<Integer,Integer>(x,y));
	}

	public void setType(Type type){
		this.type=type;
	}

	public Type getType(){
		return type;
	}

	public void setControlPoints(List<Vct2<Integer, Integer>> controlPoints){
		this.controlPoints=controlPoints;
	}

	public List<Vct2<Integer, Integer>> getControlPoints(){
		return controlPoints;
	}
	
	public enum Type{
		Linear("L"),Perfect("P"),Bezier("B"),Catmull("C");
		
		public final String tag;
		
		Type(String t){
			tag=t;
		}
		
		public String getTag(){
			return tag;
		}
		
		public static Type forName(String n){
			switch(n){
				case "L":
					return Linear;
				case "P":
					return Perfect;
				case "B":
					return Bezier;
				case "C":
					return Catmull;
				default:return null;
			}
		}
	}
}




