package com.edplan.framework.ui.drawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;

/**
 *默认绘制在整个canvas上，
 *默认不进行layout
 */
public abstract class EdDrawable<T extends BaseCanvas>
{
//	private Mat4 translationMatrix=Mat4.createIdentity();

	private MContext context;

	public EdDrawable(MContext context){
		this.context=context;
	}

	public MContext getContext() {
		return context;
	}

	public abstract void draw(T canvas);
}
