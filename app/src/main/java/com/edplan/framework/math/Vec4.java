package com.edplan.framework.math;

import com.edplan.framework.graphics.opengl.objs.Color4;

public class Vec4 extends Color4 
{
	public Vec4(float x,float y,float z,float w){
		super(x,y,z,w);
	}
	
	public Vec4(Color4 r){
		super(r);
	}
	
	public Vec4 add(float f){
		add(f,f,f,f);
		return this;
	}

	@Override
	public Vec4 copyNew() {
		// TODO: Implement this method
		return new Vec4(this);
	}
}
