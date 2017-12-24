package com.edplan.opengl.es20;

import android.opengl.GLES20;
import android.util.Log;
import com.edplan.opengl.GlBuffer;
import com.edplan.opengl.es20.MatrixState;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TextureTriangle 
{	

	Es20Texture texture;
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //�������������������id  
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��

	FloatBuffer   mVertexBuffer;//���������ݻ���
	FloatBuffer   mTexCoorBuffer;//�������������ݻ���
    int vCount=0;   
    float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
	
	

    public TextureTriangle(Es20Texture t,MyTDView mv)
    {    	
		texture=t;
    	//��ʼ�������������ɫ���
    	initVertexData();
    	//��ʼ����ɫ��        
    	initShader(mv);
    }

    //��ʼ�������������ɫ��ݵķ���
    public void initVertexData()
    {
		/*
    	//���������ݵĳ�ʼ��================begin============================
        vCount=3;
        final float UNIT_SIZE=0.05f;
        float vertices[]=new float[]
        {
        	0*UNIT_SIZE,11*UNIT_SIZE,0,
        	-11*UNIT_SIZE,-11*UNIT_SIZE,0,
        	11*UNIT_SIZE,-11*UNIT_SIZE,0,
        };

        //�������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥��������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ��ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���������ݵĳ�ʼ��================end============================

        //�������������ݵĳ�ʼ��================begin============================
        float texCoor[]=new float[]//������ɫֵ���飬ÿ���4��ɫ��ֵRGBA
        {
			0.5f,0, 
			0,1, 
			1,1        		
        };        
        //�����������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ���
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ��ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�������������ݵĳ�ʼ��================end============================
*/
		
		final float UNIT_SIZE=0.5f;
		GlBuffer vbf=new GlBuffer();
		GlBuffer cbf=new GlBuffer();

		vbf.add3(UNIT_SIZE,-UNIT_SIZE,0)
			.add3(UNIT_SIZE,UNIT_SIZE,0)
			.add3(-UNIT_SIZE,-UNIT_SIZE,0)
			.add3(-UNIT_SIZE,-UNIT_SIZE,0)
			.add3(UNIT_SIZE,UNIT_SIZE,0)
			.add3(-UNIT_SIZE,UNIT_SIZE,0);
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
		mTexCoorBuffer=cbb.asFloatBuffer();
		mTexCoorBuffer.put(cs);
		mTexCoorBuffer.position(0);
		
    }

    //��ʼ����ɫ��
    public void initShader(MyTDView mv)
    {
		/*
    	//���ض�����ɫ��Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("textured.vs", mv.getResources());
        //����ƬԪ��ɫ��Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("textured.fs", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ�������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoord");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
		*/
		
		//加载顶点着色器的脚本内容
        mVertexShader=ShaderUtil.loadFromAssetsFile("textured.vs", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader=ShaderUtil.loadFromAssetsFile("textured.fs", mv.getResources());  
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoord");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
		

		int texLoc=GLES20.glGetUniformLocation(mProgram,"uTexture");
		GLES20.glUniform1i(texLoc,1);
		Log.v("gl_test","utexh: "+texLoc);
		texLoc=GLES20.glGetUniformLocation(mProgram,"hTexture");
		GLES20.glUniform1i(texLoc,1);
		Log.v("gl_test","htexh: "+texLoc);
		//texLoc=GLES20.glGetUniformLocation(mProgram,"kTexture");
		//GLES20.glUniform1i(texLoc,1);
		//Log.v("gl_test","ktexh: "+texLoc);
    }

    public void drawSelf()
    {        
	/*
		//�ƶ�ʹ��ĳ��shader����
		GLES20.glUseProgram(mProgram);        

		MatrixState.setInitStack();

		//������Z������λ��1
		MatrixState.transtate(0, 0, 1);

		//������y����ת
		MatrixState.rotate(yAngle, 0, 1, 0);
		//������z����ת
		MatrixState.rotate(zAngle, 0, 0, 1);  
		//������x����ת
		MatrixState.rotate(xAngle, 1, 0, 0);
		//�����ձ任������shader����
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
		*/
		
		GLES20.glUseProgram(mProgram);        

		MatrixState.setInitStack();

		MatrixState.transtate(0, 0, 1);

		//������y����ת
		//MatrixState.rotate(yAngle, 0, 1, 0);
		//������z����ת
		//MatrixState.rotate(zAngle, 0, 0, 1);  
		//������x����ת
		//MatrixState.rotate(xAngle, 1, 0, 0);

		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
		
		GLES20.glVertexAttribPointer(maPositionHandle,3,GLES20.GL_FLOAT,false,3*4,mVertexBuffer);
		//GLES20.glVertexAttribPointer(maPositionHandle,3,GLES20.GL_FLOAT,false,3*4,mVertexBuffer);
		
		
		GLES20.glVertexAttribPointer(maTexCoorHandle,2,GLES20.GL_FLOAT,false,2*4,mTexCoorBuffer);
		//GLES20.glVertexAttribPointer(maTexCoorHandle,2,GLES20.GL_FLOAT,false,2*4,mTexCoorBuffer);
		
		
		
		//Ϊ����ָ������λ�����
		       
		//Ϊ����ָ����������������
		   
		//������λ���������
		GLES20.glEnableVertexAttribArray(maPositionHandle);  
		GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
		texture.bind(0);
		
         //������
       //  GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
       //  GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         
		//�����������
		//GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		
		
		/*
		
			//为画笔指定顶点位置数据
		
		//允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);  
		GLES20.glEnableVertexAttribArray(maTexCoorHandle); 

		texture.bind(0);
		//绘制三角形
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		*/
		
		

		
		
    }
}
