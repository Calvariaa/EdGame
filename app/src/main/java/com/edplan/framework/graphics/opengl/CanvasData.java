package com.edplan.framework.graphics.opengl;

import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.interfaces.Copyable;
import com.edplan.framework.interfaces.Recycleable;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.math.Vec2;

public class CanvasData implements Recycleable,Copyable {

	private float width;

	private float height;
	
	private Mat4 currentMaskMatrix;

	private Mat4 currentProjMatrix;

	private float pixelDensity;
	
	private Texture3DShader texture3DShader;

	public CanvasData(CanvasData c){
		this.currentMaskMatrix=c.getCurrentMaskMatrix().copy();
		this.texture3DShader=c.texture3DShader;
		this.currentProjMatrix=c.currentProjMatrix.copy();
		this.width=c.width;
		this.height=c.height;
		this.pixelDensity=c.pixelDensity;
	}

	public CanvasData(){
		currentProjMatrix=new Mat4();
		currentMaskMatrix=new Mat4();
	}

	public void setPixelDensity(float pixelDensity) {
		this.pixelDensity=pixelDensity;
	}

	public float getPixelDensity() {
		return pixelDensity;
	}

	public void setWidth(float width) {
		this.width=width;
	}

	public float getWidth() {
		return width;
	}

	public void setHeight(float height) {
		this.height=height;
	}

	public float getHeight() {
		return height;
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
	
	public CanvasData translate(float tx,float ty){
		getCurrentMaskMatrix().translate(tx,ty,0);
		return this;
	}
	
	public CanvasData scale(float s){
		getCurrentMaskMatrix().scale(s,s,1);
		this.pixelDensity*=s;
		return this;
	}
	
	public CanvasData clip(Vec2 wh){
		setWidth(wh.x);
		setHeight(wh.y);
		return this;
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
