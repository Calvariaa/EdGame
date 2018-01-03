package com.edplan.framework.math;
import android.opengl.Matrix;
import com.edplan.framework.interfaces.Recycleable;

public class Mat4 implements Recycleable
{
	public static final int WIDTH=4;
	
	public static final Mat4 IDENTITY_MAT4=(new Mat4()).setIden();
	
	public float[] data;
	
	public Mat4(float[] d){
		this();
		set(d);
	}
	
	public Mat4(){
		data=new float[16];
	}
	
	public Mat4 setIden(){
		return setDiag(1,1,1,1);
	}
	
	public Mat4 set(float... datas){
		if(datas.length!=16){
			throw new IllegalArgumentException("Mat4.set(float...) should put 16 var");
		}else{
			for(int i=0;i<data.length;i++){
				data[i]=datas[i];
			}
		}
		return this;
	}
	
	public Mat4 set(Mat4 mat){
		return set(mat.data);
	}
	
	public Mat4 setDiag(float v11,float v22,float v33,float v44){
		float[] tmp={v11,v22,v33,v44};
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<WIDTH;j++){
				if(i==j){
					set(i,j,tmp[i]);
				}else{
					set(i,j,0);
				}
			}
		}
		return this;
	}
	
	public Mat4 set(int x,int y,float d){
		data[x+WIDTH*y]=d;
		return this;
	}
	
	public float get(int x,int y){
		return data[x+WIDTH*y];
	}
	
	public Mat4 translate(float x,float y,float z){
		Matrix.translateM(data,0,x,y,z);
		return this;
	}
	
	public Mat4 rotate(float angle,float x,float y,float z){
    	Matrix.rotateM(data,0,angle,x,y,z);
		return this;
    }
	
	//默认以(0,0,1)旋转
	public Mat4 rotate2D(float ox,float oy,float angel,boolean isClockwise){
		return this
				 .translate(ox,oy,0)
				 .rotate(angel,0,0,(isClockwise)?1:-1)
				 .translate(-ox,-oy,0);
	}
	
	public Mat4 scale(float sx,float sy,float sz){
		Matrix.scaleM(data,0,sx,sy,sz);
		return this;
	}
	
	public Mat4 setCamera
    (
		float cx,float cy,float cz,   
		float tx,float ty,float tz,   
		float upx,float upy,float upz   		
    )
    {
    	Matrix.setLookAtM
        (
			data, 
			0, 
			cx,cy,cz,
			tx,ty,tz,
			upx,upy,upz
        );
		return this;
	}
	
	public Mat4 setCamera(Vec3 position,Vec3 lookPosition,Vec3 upVec){
		return setCamera(
			position.x     ,position.y     ,position.z,
			lookPosition.x ,lookPosition.y ,lookPosition.z,
			upVec.x        ,upVec.y        ,upVec.z
		);
	}
	
	public Mat4 setOrtho
    (
    	float left,float right,
    	float bottom,float top,
    	float near,float far
    )
    {    	
    	Matrix.orthoM(data, 0, left, right, bottom, top, near, far);
		return this;
    }
	
	public Mat4 setFrustum
    (
    	float left,float right,
    	float bottom,float top,
    	float near,float far
    )
    {
    	Matrix.frustumM(data, 0, left, right, bottom, top, near, far);    	
		return this;
    }
	
	public Mat4 pre(Mat4 mat){
		Matrix.multiplyMM(data,0,data,0,mat.data,0);
		return this;
	}
	
	public Mat4 post(Mat4 mat){
		Matrix.multiplyMM(data,0,mat.data,0,data,0);
		return this;
	}
	
	public Mat4 copy(){
		return new Mat4(this.data);
	}
	
	public static Mat4 rotation(float angel,float dx,float dy,float dz){
		return createIdentity().rotate(angel,dx,dy,dz);
	}
	
	public static Mat4 translation(float tx,float ty,float tz){
		return createIdentity().translate(tx,ty,tx);
	}
	
	public static Mat4 createIdentity(){
		return IDENTITY_MAT4.copy();
	}

	@Override
	public void recycle() {
		// TODO: Implement this method
		this.data=null;
	}
}
