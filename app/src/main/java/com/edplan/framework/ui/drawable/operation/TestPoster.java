package com.edplan.framework.ui.drawable.operation;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.RectF;
import com.edplan.framework.graphics.opengl.GLPaint;

public class TestPoster implements ITexturePoster
{
	@Override
	public void drawTexture(GLCanvas2D canvas,GLTexture t) {
		// TODO: Implement this method
		GLPaint paint=new GLPaint();
		canvas.drawTexture(t,new RectF(0,0,t.getWidth(),t.getHeight()),paint);
	}
}
