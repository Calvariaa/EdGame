package com.edplan.framework.graphics.opengl;

import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.interfaces.Copyable;
import com.edplan.framework.interfaces.Recycleable;
import com.edplan.framework.math.Mat4;

public class CanvasData implements Recycleable,Copyable {

	private Mat4 currentMaskMatrix;

	private Mat4 currentProjMatrix;

	private Texture3DShader texture3DShader;

	public CanvasData(CanvasData c){
		this.currentMaskMatrix=c.getCurrentMaskMatrix().copy();
		this.texture3DShader=c.texture3DShader;
		this.currentProjMatrix=c.currentProjMatrix.copy();
	}

	public CanvasData(){
		currentProjMatrix=new Mat4();
		currentMaskMatrix=new Mat4();
	}

	public void setCurrentProjMatrix(Mat4 projMatrix) {
		this.currentProjMatrix.set(projMatrix);
	}

	public Mat4 getCurrentProjMatrix() {
		return currentProjMatrix;
	}

	public void setTexture3DShader(Texture3DShader texture3DShader) {
		this.texture3DShader=texture3DShader;
	}

	public Texture3DShader getTexture3DShader() {
		return texture3DShader;
	}

	public void setCurrentMaskMatrix(Mat4 matrix) {
		this.currentMaskMatrix.set(matrix);
	}

	public Mat4 getCurrentMaskMatrix() {
		return currentMaskMatrix;
	}

	@Override
	public void recycle() {
		// TODO: Implement this method
		this.currentMaskMatrix.recycle();
		this.currentProjMatrix.recycle();
	}

	@Override
	public Copyable copy() {
		// TODO: Implement this method
		return new CanvasData(this);
	}
}
