package com.edplan.framework.graphics.opengl.objs.vertex;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.IQuad;

public class RectVertex extends TextureVertex3D
{
	public Vec2 rectPosition;

	public RectVertex setRectPosition(Vec2 rectPosition) {
		this.rectPosition=rectPosition;
		return this;
	}

	public Vec2 getRectPosition() {
		return rectPosition;
	}
	
	public static RectVertex atRect(IQuad rect,float sx,float sy){
		return (new RectVertex()).setRectPosition(rect.getPoint(sx,sy));
	}
}
