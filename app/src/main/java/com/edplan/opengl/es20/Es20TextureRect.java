package com.edplan.opengl.es20;

import android.opengl.GLES20;
import android.util.Log;
import com.edplan.opengl.GlBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import android.opengl.Matrix;
import com.edplan.framework.utils.MLog;

public class Es20TextureRect 
{
	
	FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	FloatBuffer mTexCoordBuffer;//顶点着色数据缓冲
    int vCount=0;	
	
	public Es20Texture texture;

	private String mVertexShader;

	private String mFragmentShader;

	private int mProgram;

	private int maPositionHandle;

	private int maTexCoordHandle;

	private int muMVPMatrixHandle;
	
	public float rotateY=0;
	
	public Es20TextureRect(Es20Texture t,MyTDView v){
		texture=t;
		initVertex();
		initShader(v);
	}
	
	float z=0.00f;
	public void initVertex(){
		final float UNIT_SIZE=500.5f;
		GlBuffer vbf=new GlBuffer();
		GlBuffer cbf=new GlBuffer();
		
		vbf.add3(UNIT_SIZE,-UNIT_SIZE,z)
			 .add3(UNIT_SIZE,UNIT_SIZE,z)
			 .add3(-UNIT_SIZE,-UNIT_SIZE,z)
			 .add3(-UNIT_SIZE,-UNIT_SIZE,z)
			 .add3(UNIT_SIZE,UNIT_SIZE,z)
			 .add3(-UNIT_SIZE,UNIT_SIZE,z);
		cbf.add2(1,1);//texture.toTexture(texture.width,texture.height));
		cbf.add2(1,0);//(texture.toTexture(texture.width,0));
		cbf.add2(0,1);//(texture.toTexture(0,texture.height));
		cbf.add2(0,1);//(texture.toTexture(0,texture.height));
		cbf.add2(1,0);//(texture.toTexture(texture.width,0));
		cbf.add2(0,0);//(texture.toTexture(0,0));
		
		float[] vs=vbf.toArray();
		vCount=vs.length/3;
		float[] cs=cbf.toArray();
		//Log.v("test","vs : "+Arrays.toString(vs));
		//Log.v("test","cs : "+Arrays.toString(cs));

		ByteBuffer vbb=ByteBuffer.allocateDirect(vs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vs);
		mVertexBuffer.position(0);

		ByteBuffer cbb=ByteBuffer.allocateDirect(cs.length*4);
		cbb.order(ByteOrder.nativeOrder());
		mTexCoordBuffer=cbb.asFloatBuffer();
		mTexCoordBuffer.put(cs);
		mTexCoordBuffer.position(0);
	}
	
	public void initShader(MyTDView mv)
    {
    	//加载顶点着色器的脚本内容
        mVertexShader=ShaderUtil.loadFromAssetsFile("textured.vs", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader=ShaderUtil.loadFromAssetsFile("textured.fs", mv.getResources());  
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		Log.v("gl_test","pos: "+maPositionHandle);
        //获取程序中顶点颜色属性引用id  
        maTexCoordHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoord");
		Log.v("gl_test","tex: "+maTexCoordHandle);
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
		Log.v("gl_test","mvp: "+muMVPMatrixHandle);
    }
	boolean hasTest=false;
	public void drawSelf(Es20Texture tex){
		GLES20.glUseProgram(mProgram);  
		
		if(!hasTest){
			hasTest=true;
			long t=System.currentTimeMillis();
			int times=15000;
			for(int i=0;i<times;i++){
				GLES20.glUseProgram(mProgram);
			}
			Log.v("gltest","useProgram "+times+"times cost "+(System.currentTimeMillis()-t)+"ms");
			
		}
		

		MatrixState.setInitStack();

		//������Z������λ��1
		MatrixState.transtate(0, 0, 1);



		//������y����ת
		//MatrixState.rotate(yAngle, 0, 1, 0);
		//������z����ת
		MatrixState.rotate(rotateY, 0, 1, 1);  
		//������x����ת
		//MatrixState.rotate(xAngle, 1, 0, 0);

		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix()/*.getFianlMatrix(mMMatrix)*/, 0); 
		MLog.test.vOnce("mvp gl","gl_test","mvp ok: "+Arrays.toString(MatrixState.getFinalMatrix()));
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
			maTexCoordHandle,
			2,
			GLES20.GL_FLOAT,
			false,
			2*4,
			mTexCoordBuffer
		);
		//允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);  
		GLES20.glEnableVertexAttribArray(maTexCoordHandle); 

		tex.bind(0);
		//绘制三角形
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		
	}
	
	public void drawSelf(){
		drawSelf(texture);
	}
}
