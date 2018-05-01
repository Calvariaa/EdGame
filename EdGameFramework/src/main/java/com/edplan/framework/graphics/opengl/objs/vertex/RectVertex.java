package com.edplan.framework.graphics.opengl.objs.vertex;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.IQuad;
import com.edplan.framework.graphics.opengl.objs.Vertex3D;
import com.edplan.framework.ui.Anchor;

public class RectVertex extends TextureVertex3D
{
	public Vec2 rectPosition=new Vec2();

	public RectVertex(){
		
	}
	
	public RectVertex(RectVertex r){
		set(r);
	}
	
	public RectVertex setRectPosition(Vec2 rectPosition) {
		this.rectPosition.set(rectPosition);
		return this;
	}

	public Vec2 getRectPosition() {
		return rectPosition;
	}

	@Override
	public void set(Vertex3D v) {
		// TODO: Implement this method
		super.set(v);
		rectPosition.set(((RectVertex)v).rectPosition);
	}
	
	public void setRect(IQuad rect,float sx,float sy){
		setRectPosition(rect.getPoint(sx,sy));
	}
	
	public RectVertex setRect(IQuad rect,Anchor anchor){
		return this;
	}

	@Override
	public RectVertex copy() {
		// TODO: Implement this method
		return new RectVertex(this);
	}
	
	public static RectVertex atRect(IQuad rect,float sx,float sy){
		return (new RectVertex()).setRectPosition(rect.getPoint(sx,sy));
	}
	
	public static RectVertex atRect(IQuad rect,Anchor a){
		return (new RectVertex()).setRect(rect,a);
	}
	
	public static RectVertex[] listQuad(IQuad q){
		//  3          2
		//   ┌────┐
		//   └────┘
		//  0          1
		/*
		return new RectVertex[]{
			new RectVertex().
		};
		*/
		return null;
	}
}
