package com.edplan.framework.math;

public class Matrix2
{
	public static final int WIDTH=2;
	
	public float data[][];
	
	public Matrix2(){
		data=new float[2][2];
	}
	
	public Matrix2(float v11,float v12,float v21,float v22){
		data=new float[][]{{v11,v12},{v21,v22}};
	}
	
	public Matrix2(float[][] d){
		data=new float[2][2];
		set(d);
	}
	
	public Matrix2 set(float[][] d){
		data[0][0]=d[0][0];
		data[0][1]=d[0][1];
		data[1][0]=d[1][0];
		data[1][1]=d[1][1];
		return this;
	}
	
	public Matrix2 set(float v11,float v12,float v21,float v22){
		data[0][0]=v11;
		data[0][1]=v12;
		data[1][0]=v21;
		data[1][1]=v22;
		return this;
	}
	
	public float detValue(){
		return data[0][0]*data[1][1]-data[0][1]*data[1][0];
	}
	
	public Matrix2 post(Matrix2 m){
		float[][] newData=new float[2][2];
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<WIDTH;j++){
				newData[i][j]=data[i][0]*m.data[0][j]+data[i][1]*m.data[1][j];
			}
		}
		set(newData);
		return this;
	}
	
	public Matrix2 prePost(Matrix2 m){
		float[][] newData=new float[2][2];
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<WIDTH;j++){
				newData[i][j]=m.data[i][0]*data[0][j]+m.data[i][1]*data[1][j];
			}
		}
		set(newData);
		return this;
	}
	
	public Matrix2 copy(){ return new Matrix2(data); }
	
	public static Matrix2 rotation(float ang){
		float sin=FMath.sin(ang);
		float cos=FMath.cos(ang);
		return
			new Matrix2(
				  cos,sin,
				 -sin,cos
			);
	}
	
	public static Matrix2 zoom(float zx,float zy){
		return
			new Matrix2(
				zx,0,
				0,zy
			);
	}
}
