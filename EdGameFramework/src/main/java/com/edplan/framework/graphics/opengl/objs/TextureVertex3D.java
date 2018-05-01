package com.edplan.framework.graphics.opengl.objs;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.Vec3;
import com.edplan.framework.interfaces.Setable;

public class TextureVertex3D extends Vertex3D
{
	public static final Vec2 DEF_TEXTURE_POINT=new Vec2(0,0);	
	public Vec2 texturePoint=DEF_TEXTURE_POINT.copy();
	
	public TextureVertex3D(){
		super();
	}
	
	public TextureVertex3D(float tx,float ty,float x,float y,float z,Color4 color){
		super(x,y,z,color);
		this.texturePoint.set(tx,ty);
	}
	
	public TextureVertex3D(Vec3 p){
		super(p);
	}
	
	public TextureVertex3D(Vec3 p,Vec2 tp){
		super(p);
		this.texturePoint=tp;
	}
	
	public TextureVertex3D(TextureVertex3D t){
		set(t);
	}
	
	@Override
	public void set(Vertex3D v){
		super.set(v);
		texturePoint.set(((TextureVertex3D)v).texturePoint);
	}

	@Override
	public TextureVertex3D setColor(Color4 color) {
		this.color.set(color);
		return this;
	}

	public Color4 getColor() {
		return color;
	}

	public TextureVertex3D setTexturePoint(Vec2 texturePoint) {
		this.texturePoint.set(texturePoint);
		return this;
	}

	public Vec2 getTexturePoint() {
		return texturePoint;
	}

	@Override
	public TextureVertex3D copy() {
		// TODO: Implement this method
		return new TextureVertex3D(this);
	}
	
	public static TextureVertex3D atPosition(Vec3 pos){
		return new TextureVertex3D(pos);
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return super.toString();
	}
}
