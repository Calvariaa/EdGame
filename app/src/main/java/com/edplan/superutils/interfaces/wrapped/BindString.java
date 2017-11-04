package com.edplan.superutils.interfaces.wrapped;
import com.edplan.superutils.interfaces.BindProperty;

public class BindString implements BindProperty<String>
{
	private String v;
	
	public BindString(String s){
		v=s;
	}

	@Override
	public void set(String t){
		// TODO: Implement this method
		v=t;
	}

	@Override
	public String get(){
		// TODO: Implement this method
		return v;
	}

	
}
