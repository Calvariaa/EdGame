package com.edplan.framework.graphics.opengl.objs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.edplan.framework.math.Vec2;
import java.nio.IntBuffer;
import java.io.InputStream;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.edplan.framework.resource.IResource;
import java.io.IOException;

public class GLTexture
{
	public static final int[] glTexIndex=new int[]{
		GLES20.GL_TEXTURE0,
		GLES20.GL_TEXTURE1,
		GLES20.GL_TEXTURE2,
		GLES20.GL_TEXTURE3,
		GLES20.GL_TEXTURE4,
		GLES20.GL_TEXTURE5,
		GLES20.GL_TEXTURE6,
		GLES20.GL_TEXTURE7,
		GLES20.GL_TEXTURE8,
		GLES20.GL_TEXTURE9,
		GLES20.GL_TEXTURE10,
		GLES20.GL_TEXTURE11,
		GLES20.GL_TEXTURE12,
		GLES20.GL_TEXTURE13,
		GLES20.GL_TEXTURE14,
		GLES20.GL_TEXTURE15,
		GLES20.GL_TEXTURE16,
		GLES20.GL_TEXTURE17,
		GLES20.GL_TEXTURE18
	};

	private int width,height;

	private int realWidth,realHeight;
	
	private float glWidth,glHeight;

	private int textureId;
	
	public GLTexture(){
		
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public int getRealWidth() {
		return realWidth;
	}

	public int getRealHeight() {
		return realHeight;
	}

	public float getGlWidth() {
		return glWidth;
	}

	public float getGlHeight() {
		return glHeight;
	}

	public int getTextureId() {
		return textureId;
	}

	public void bind(int loc){
		bindGl(glTexIndex[loc]);
	}

	private void bindGl(int i){
		GLES20.glActiveTexture(i);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
	}

	public Vec2 toTexturePosition(float x,float y){
		return new Vec2(glWidth*x/width,glHeight*y/height);
	}
	
	public void delete(){
		GLES20.glDeleteTextures(1,new int[]{textureId},0);
	}
	
	public Bitmap toBitmap(){
		int[] b=new int[getWidth()*getHeight()];
		IntBuffer buffer=IntBuffer.wrap(b);
		buffer.position(0);
		GLES20.glReadPixels(0,0,getWidth(),getHeight(),GLES20.GL_RGBA,GLES20.GL_UNSIGNED_BYTE,buffer);
		Bitmap bmp=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
		bmp.copyPixelsFromBuffer(buffer);
		buffer.clear();
		return bmp;
	}
	
	public static GLTexture createGPUTexture(int w,int h){
		int[] t=new int[1];
		GLES20.glGenTextures(1,t,0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,t[0]);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D,0,GLES20.GL_RGBA,w,h,0,GLES20.GL_RGBA,GLES20.GL_UNSIGNED_SHORT_4_4_4_4,null);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
		GLTexture tex=new GLTexture();
		tex.width=w;
		tex.height=h;
		tex.textureId=t[0];
		tex.glHeight=1;
		tex.glWidth=1;
		//Log.v("fbo","gen tx: "+tex.textureId);
		return tex;
	}
	
	public static GLTexture createNotChecked(Bitmap bmp,int w,int h){
		GLTexture tex=new GLTexture();
		tex.textureId=createTexture();
		tex.width=w;
		tex.height=h;

		tex.glHeight=tex.height/(float)bmp.getWidth();
		tex.glWidth=tex.width/(float)bmp.getHeight();
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bmp,0);
		return tex;
	}
	
	public static GLTexture decodeStream(InputStream in){
		return create(BitmapFactory.decodeStream(in),true);
	}
	
	public static GLTexture decodeStream(InputStream in,boolean ifClose) throws IOException{
		GLTexture t=decodeStream(in);
		if(ifClose)in.close();
		return t;
	}
	
	public static GLTexture decodeFile(File f) throws FileNotFoundException, IOException{
		return decodeStream(new FileInputStream(f),true);
	}
	
	public static GLTexture decodeResource(IResource res,String name) throws IOException{
		return decodeStream(res.openInput(name),true);
	}
	
	public static GLTexture create(Bitmap bmp,boolean ifDispos){
		GLTexture t=create(bmp);
		if(ifDispos)bmp.recycle();
		return t;
	}
	
	public static GLTexture create(Bitmap bmp){
		GLTexture tex;
		int w=1;
		int h=1;
		while(w<bmp.getWidth())w*=2;
		while(h<bmp.getHeight())h*=2;
		if(w!=bmp.getWidth()||h!=bmp.getHeight()){
			Bitmap nb=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
			Canvas c=new Canvas(nb);
			c.drawColor(0x00000000);
			Paint p=new Paint();
			p.setAntiAlias(false);
			c.drawBitmap(bmp,0,0,p);
			tex=createNotChecked(nb,bmp.getWidth(),bmp.getHeight());
			nb.recycle();
		}else{
			tex=createNotChecked(bmp,bmp.getWidth(),bmp.getHeight());
		}
		return tex;
	}

	private static int createTexture(){
		int[] t=new int[1];
		GLES20.glGenTextures(1,t,0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,t[0]);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
							   GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
		return t[0];
	}
}
