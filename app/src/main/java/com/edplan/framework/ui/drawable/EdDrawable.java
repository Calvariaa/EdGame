package com.edplan.framework.ui.drawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.Mat4;

/**
 *默认绘制在整个canvas上，
 *大小调整什么的由参数确定后，传到draw的应该为已经变换过了的canvas
 */
public abstract class EdDrawable
{
	private Mat4 translationMatrix=Mat4.createIdentity();

	public void setTranslationMatrix(Mat4 translationMatrix) {
		this.translationMatrix.set(translationMatrix);
	}

	public Mat4 getTranslationMatrix() {
		return translationMatrix;
	}
	
	public abstract void draw(GLCanvas2D canvas);
}
