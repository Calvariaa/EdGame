package com.edplan.superutils.interfaces.wrapped;
import com.edplan.superutils.interfaces.BindProperty;

public class BindFloat implements BindProperty<Float>
{
	private float v;
	
	public BindFloat(float f){
		v=f;
	}
	
	@Override
	public void set(Float t){
		// TODO: Implement this method
		v=t;
	}

	@Override
	public Float get(){
		// TODO: Implement this method
		return v;
	}
}
