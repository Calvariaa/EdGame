package com.edplan.nso.beatmapComponent;
import com.edplan.superutils.interfaces.StringMakeable;

public class SampleSet implements StringMakeable
{
	private Type type;
	
	public SampleSet(){
		
	}
	
	public SampleSet(Type t){
		setType(t);
	}

	public void setType(Type type){
		this.type=type;
	}

	public Type getType(){
		return type;
	}
	
	@Override
	public String makeString(){
		// TODO: Implement this method
		return (type!=null)?type.value():"{@SampleSet}"; 
	}
	
	public static SampleSet parse(String line){
		return new SampleSet(Type.fromName(line));
	}
	
	
	public enum Type{
		Soft("Soft"),Normal("Normal"),Drum("Drum")
		;
		private final String value;

		public Type(String v){
			value=v;
		}

		public String value(){
			return value;
		}

		public static Type fromName(String s){
			switch(s){
				case "Soft":
					return Soft;
				case "Normal":
					return Normal;
				case "Drum":
					return Drum;
				default:
					return null;
			}
		}
	}
}
