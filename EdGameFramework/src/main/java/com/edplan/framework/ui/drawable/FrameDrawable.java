package com.edplan.framework.ui.drawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.BaseCanvas;

public class FrameDrawable<T extends BaseCanvas> extends EdDrawable<T>
{
	public FrameDrawable(MContext c){
		super(c);
	}

	@Override
	public void draw(T canvas) {
		// TODO: Implement this method
	}
}
