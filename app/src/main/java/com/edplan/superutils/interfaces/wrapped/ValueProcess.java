package com.edplan.superutils.interfaces.wrapped;
import com.edplan.superutils.interfaces.Function;

public abstract class ValueProcess<T> implements Function<Float>
{
	
	
	@Override
	public void invoke(Float t){
		// TODO: Implement this method
	}
	
	public abstract T getInterpolator(float f,T t1,T t2);
	
	
	
	private class ControlPoint{
		T value;
		float weight;
	}
}
