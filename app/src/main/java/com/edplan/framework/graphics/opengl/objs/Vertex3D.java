package com.edplan.framework.graphics.opengl.objs;
import com.edplan.framework.math.Vec3;

public class Vertex3D
{
	public Vec3 position;
	
	public Color4 color;
	
	public Vertex3D(){
		
	}
	
	public Vertex3D(Vec3 p){
		this.position=p;
	}
	
	public Vertex3D(float x,float y,float z){
		this(new Vec3(x,y,z));
	}

	public Vertex3D setColor(Color4 color) {
		this.color=color;
		return this;
	}

	public Color4 getColor() {
		return color;
	}

	public Vertex3D setPosition(Vec3 position) {
		this.position=position;
		return this;
	}

	public Vec3 getPosition() {
		return position;
	}
}
