package com.edplan.opengl.es20;

import android.opengl.GLES20;
import android.opengl.Matrix;
import java.nio.FloatBuffer;
import com.edplan.opengl.GlBuffer;
import com.edplan.opengl.GlVec2;
import com.edplan.opengl.GlVec4;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import android.util.Log;
import java.util.Arrays;

public class Es20Slider 
{
	public static float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    public static float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵
    public static float[] mMVPMatrix;//最后起作用的总变换矩阵

	int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用id
    int maPositionHandle; //顶点位置属性引用id  
    int maColorHandle; //顶点颜色属性引用id  
    String mVertexShader;//顶点着色器    	 
    String mFragmentShader;//片元着色器
    static float[] mMMatrix = new float[16];//具体物体的移动旋转矩阵，旋转、平移

	FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	FloatBuffer   mColorBuffer;//顶点着色数据缓冲
    int vCount=0;	
    float xAngle=0;//绕x轴旋转的角度
	
	private float dx=0;
	
	public Es20Slider(MyTDView mv,float dx,float z){
		this.dx=dx;
		this.z=z;
		initVertexData();
		initShader(mv);
		
	}

	public void setZ(float z) {
		this.z=z;
	}

	public float getZ() {
		return z;
	}
	
	public void initVertexData(){
		final float UNIT_SIZE=0.1f;
		final int pc=60;
		final float r=UNIT_SIZE*4;
		final float w=UNIT_SIZE;
		GlBuffer vbf=new GlBuffer();
		GlBuffer cbf=new GlBuffer();
		addArc(vbf,cbf,pc,(float)Math.PI*1.3f,r,w,0,0);
		addArc(vbf,cbf,pc,(float)Math.PI*1.3f,r,w,UNIT_SIZE*3,0);
		/*
		float ang=0;
		float detAng=(float)(Math.PI*1.3/pc);
		GlVec4 cc=new GlVec4(0.8f,0.8f,0.8f,1.0f),bc=new GlVec4(0,0,0,1.0f);
		GlVec2 tmp=new GlVec2((float)Math.cos(ang),(float)Math.sin(ang));
		GlVec2 pp1,pp2,pp3;
		GlVec2 p1=tmp.copyNew().multiple(r-w),p2=tmp.copyNew().multiple(r),p3=tmp.copyNew().multiple(r+w);
		//vbf.add3(0,0,-UNIT_SIZE).add3(UNIT_SIZE*3,0,-UNIT_SIZE).add3(0,UNIT_SIZE*3,-UNIT_SIZE);
		//cbf.add4(1,1,1,1).add4(1,1,1,1).add4(0,0,0,1);
		for(int i=0;i<pc;i++){
			//if(true)continue;
			pp1=p1;
			pp2=p2;
			pp3=p3;
			tmp.rotate(detAng);
			p1=tmp.copyNew().multiple(r-w);
			p2=tmp.copyNew().multiple(r);
			p3=tmp.copyNew().multiple(r+w);
			
			vbf.add3(p1).add3(pp1).add3(pp2);
			cbf.add4(bc).add4(bc).add4(cc);
			vbf.add3(p1).add3(pp2).add3(p2);
			cbf.add4(bc).add4(cc).add4(cc);
			vbf.add3(p3).add3(pp3).add3(pp2);
			cbf.add4(bc).add4(bc).add4(cc);
			vbf.add3(p3).add3(pp2).add3(p2);
			cbf.add4(bc).add4(cc).add4(cc);
		}*/
		
		float[] vs=vbf.toArray();
		vCount=vs.length/3;
		float[] cs=cbf.toArray();
		Log.v("test","vs : "+Arrays.toString(vs));
		Log.v("test","cs : "+Arrays.toString(cs));
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(vs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vs);
		mVertexBuffer.position(0);
		
		ByteBuffer cbb=ByteBuffer.allocateDirect(cs.length*4);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer=cbb.asFloatBuffer();
		mColorBuffer.put(cs);
		mColorBuffer.position(0);
		
		/*
		mVertexBuffer=asBuffer(vs);
		mColorBuffer=asBuffer(cs);*/
	}
	
	public FloatBuffer asBuffer(float[] fs){
		ByteBuffer bb=ByteBuffer.allocateDirect(fs.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb=bb.asFloatBuffer();
		fb.put(fs);
		fb.position(0);
		return fb;
	}
	
	
	float z=-0f;
	public void addArc(GlBuffer vbf,GlBuffer cbf,int pc,float deg,float r,float w,float cx,float cy){
		float ang=0;
		float detAng=deg/pc;
		GlVec4 cc=new GlVec4(0.8f,0.8f,0.8f,1.0f),bc=new GlVec4(0,0,0,1.0f);
		GlVec2 tmp=new GlVec2((float)Math.cos(ang),(float)Math.sin(ang));
		GlVec2 pp1,pp2,pp3;
		GlVec2 p1=tmp.copyNew().multiple(r-w).add(dx,0),p2=tmp.copyNew().multiple(r).add(dx,0),p3=tmp.copyNew().multiple(r+w).add(dx,0);
		//vbf.add3(0,0,-UNIT_SIZE).add3(UNIT_SIZE*3,0,-UNIT_SIZE).add3(0,UNIT_SIZE*3,-UNIT_SIZE);
		//cbf.add4(1,1,1,1).add4(1,1,1,1).add4(0,0,0,1);
		for(int i=0;i<pc;i++){
			//if(true)continue;
			pp1=p1;
			pp2=p2;
			pp3=p3;
			tmp.rotate(detAng);
			p1=tmp.copyNew().multiple(r-w).add(cx+dx,cy);
			p2=tmp.copyNew().multiple(r).add(cx+dx,cy);
			p3=tmp.copyNew().multiple(r+w).add(cx+dx,cy);
			
			final float ups=0.01f;
			vbf.add3(p1,z).add3(pp1,z).add3(pp2,ups+z);
			cbf.add4(bc).add4(bc).add4(cc);
			vbf.add3(p1,z).add3(pp2,ups+z).add3(p2,ups+z);
			cbf.add4(bc).add4(cc).add4(cc);
			vbf.add3(p3,z).add3(pp3,z).add3(pp2,ups+z);
			cbf.add4(bc).add4(bc).add4(cc);
			vbf.add3(p3,z).add3(pp2,ups+z).add3(p2,ups+z);
			cbf.add4(bc).add4(cc).add4(cc);
		}
	}
	
	
	public void initShader(MyTDView mv)
    {
    	//加载顶点着色器的脚本内容
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id  
        maColorHandle= GLES20.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
	
	public void drawSelf(){
		
		//MatrixState.pushMatrix();//���Ʊ���
		//MatrixState.setProjectOrtho(-1, 1, -1, 1, 1, 10);
		//MatrixState.setCamera(0, 0, 0, 0, 0, -1, 0, 1, 0);
		
		//制定使用某套shader程序
		GLES20.glUseProgram(mProgram);        
		//初始化变换矩阵
		//Matrix.setRotateM(mMMatrix,0,0,0,1,0);
		//设置沿Z轴正向位移1
		//Matrix.translateM(mMMatrix,0,0,0,1);
		//设置绕x轴旋转
		//Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);
		//
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix()/*.getFianlMatrix(mMMatrix)*/, 0); 
		//为画笔指定顶点位置数据
		GLES20.glVertexAttribPointer(
			maPositionHandle,   
			3, 
			GLES20.GL_FLOAT, 
			false,
			3*4,   
			mVertexBuffer
		);
		GLES20.glVertexAttribPointer  
		(
			maColorHandle,
			4,
			GLES20.GL_FLOAT,
			false,
			4*4,
			mColorBuffer
		);
		//允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);  
		GLES20.glEnableVertexAttribArray(maColorHandle);  
		//绘制三角形
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		
	}
	
	public static float[] getFianlMatrix(float[] spec)
    {
    	mMVPMatrix=new float[16];
    	Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);        
        return mMVPMatrix;
    }
	
}
