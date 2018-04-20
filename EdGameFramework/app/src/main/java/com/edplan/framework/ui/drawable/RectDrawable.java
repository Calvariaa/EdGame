package com.edplan.framework.ui.drawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.BaseCanvas;

public class RectDrawable<T extends BaseCanvas> extends EdDrawable<T>
{
	private GLPaint paint=new GLPaint();
	
	public RectDrawable(MContext c){
		super(c);
	}
	
	public void setColor(Color4 c){
		paint.setMixColor(c);
	}

	@Override
	public void draw(T canvas) {
		// TODO: Implement this method
		
	}
}
