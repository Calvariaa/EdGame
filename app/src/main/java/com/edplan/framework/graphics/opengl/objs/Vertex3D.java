package com.edplan.framework.graphics.opengl.objs;
import com.edplan.framework.math.Vec3;

public class Vertex3D
{
	public static final Color4 DEF_COLOR=new Color4(1.0f,1.0f,1.0f,1.0f);
	
	public static final Vec3 DEF_POSITION=new Vec3(0,0,0);
	
	public Vec3 position;
	
	public Color4 color;
	
	public Vertex3D(){
		position=DEF_POSITION;
		color=DEF_COLOR;
	}
	
	public Vertex3D(Vec3 p){
		this.position=p;
		color=DEF_COLOR;
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

	@Override
	public String toString() {
		// TODO: Implement this method
		return (new StringBuilder())
			 .append("pos: ").append(position.toString()).append("\n")
			 .append("color: ").append(color.toString()).append("\n").toString();
	}
}
