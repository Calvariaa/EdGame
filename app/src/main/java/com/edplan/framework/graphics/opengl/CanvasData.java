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

	private Mat4 finalMatrix;

	private float pixelDensity=1;
	
	private Texture3DShader texture3DShader;

	public CanvasData(CanvasData c){
		this.currentMaskMatrix=c.getCurrentMaskMatrix().copy();
		this.texture3DShader=c.texture3DShader;
		this.currentProjMatrix=c.currentProjMatrix.copy();
		this.finalMatrix=(c.finalMatrix!=null)?c.finalMatrix.copy():null;
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

	/**
	 *定义了canvas上每单位有多少像素
	 */
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
		freshMatrix();
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

	/**
	 *每次直接操作之后要freshMatrix，否则效果不会显示
	 */
	public Mat4 getCurrentMaskMatrix() {
		return currentMaskMatrix;
	}
	
	public CanvasData translate(float tx,float ty){
		getCurrentMaskMatrix().translate(tx,ty,0);
		freshMatrix();
		return this;
	}
	
	/**
	 *对轴进行缩放，而不是对物件缩放，所以处理Matrix时用倒数
	 */
	public CanvasData scaleContent(float s){
		if(s==0)throw new IllegalArgumentException("you can't scale content using a scale rate ==0");
		float rs=1/s;
		getCurrentMaskMatrix().scale(rs,rs,1);
		freshMatrix();
		this.pixelDensity*=s;
		return this;
	}
	
	public void freshMatrix(){
		finalMatrix=null;
	}
	
	public Mat4 getFinalMatrix(){
		return (finalMatrix==null)?(finalMatrix=currentMaskMatrix.copy().post(currentProjMatrix)):finalMatrix;
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
