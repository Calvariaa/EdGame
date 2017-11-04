package com.edplan.superutils.interfaces.wrapped;
import com.edplan.superutils.interfaces.Setter;

public class FloatSetter implements Setter<Float>
{
	private float from;
	
	private float to;
	
	private Setter<Float> value;
	
	FloatSetter(){
		
	}
	
	@Override
	public void set(Float t){
		// TODO: Implement this method
		value.set(from+t*(to-from));
	}
	
	public static Builder build(){
		return new Builder();
	}
	
	public static class Builder{
		private FloatSetter setter;
		
		public Builder(){
			setter=new FloatSetter();
		}
		
		public Builder setProperty(Setter<Float> p){
			setter.value=p;
			return this;
		}
		
		public Builder from(float f){
			setter.from=f;
			return this;
		}
		
		public Builder to(float f){
			setter.to=f;
			return this;
		}
		
		public FloatSetter get(){
			return setter;
		}
	}
}
