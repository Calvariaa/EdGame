package com.edplan.superutils.interfaces.wrapped;
import com.edplan.superutils.interfaces.BindProperty;

public class BindInt implements BindProperty<Integer>
{
	private int i;
	
	public BindInt(int i){
		this.i=i;
	}

	@Override
	public void set(Integer t){
		// TODO: Implement this method
		i=t;
	}

	@Override
	public Integer get(){
		// TODO: Implement this method
		return i;
	}
}
