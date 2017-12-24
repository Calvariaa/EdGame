package com.edplan.opengl.es20;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.edplan.opengl.GlVec2;
import com.edplan.opengl.utils.MathUtil;
import java.nio.IntBuffer;
import com.edplan.framework.graphics.opengl.objs.GLTexture;

public class Es20Texture
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
		GLES20.GL_TEXTURE8
	};
	
	public int width,height;
	
	public float glWidth,glHeight;
	
	public int textureId;
	
	public Es20Texture(){}
	
	public Es20Texture(GLTexture t){
		this.width=t.getWidth();
		this.height=t.getHeight();
		this.glWidth=t.getRealWidth();
		this.glHeight=t.getRealHeight();
		this.textureId=t.getTextureId();
	}

	public void setWidth(int width) {
		this.width=width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height=height;
	}

	public int getHeight() {
		return height;
	}
	
	public void bind(int loc){
		bindGl(glTexIndex[loc]);
	}
	
	public void delete(){
		GLES20.glDeleteTextures(1,new int[]{textureId},0);
	}
	
	private void bindGl(int i){
		GLES20.glActiveTexture(i);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
	}
	
	public GlVec2 toTexture(int x,int y){
		return new GlVec2(glWidth*x/width,glHeight*y/height);
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
	
	
	public static Es20Texture create(Bitmap bmp){
		Es20Texture tex=new Es20Texture();
		int w=MathUtil.power(2,MathUtil.log2(bmp.getWidth()));
		int h=MathUtil.power(2,MathUtil.log2(bmp.getHeight()));
		if(w<bmp.getWidth())w*=2;
		if(h<bmp.getHeight())h*=2;
		Bitmap nb=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
		Canvas c=new Canvas(nb);
		Paint clp=new Paint();
		clp.setAntiAlias(false);
		clp.setStyle(Paint.Style.FILL);
		clp.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		c.drawRect(0,0,c.getWidth(),c.getHeight(),clp);
		//c.drawColor(0x00ffffff);
		Paint p=new Paint();
		p.setAntiAlias(false);
		c.drawBitmap(bmp,0,0,p);
		
		tex.textureId=createTexture();
		tex.width=bmp.getWidth();
		tex.height=bmp.getHeight();
		
		tex.glHeight=tex.height/(float)h;
		tex.glWidth=tex.width/(float)w;
		
		Log.v("gltest",nb.getWidth()+"|"+nb.getHeight());
		
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,nb,0);
		
		nb.recycle();
		//Log.v("fbo","gen tx: "+tex.textureId);
		return tex;
	}
	
	public static Es20Texture createGPUTexture(int w,int h){
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
		Es20Texture tex=new Es20Texture();
		tex.width=w;
		tex.height=h;
		tex.textureId=t[0];
		tex.glHeight=1;
		tex.glWidth=1;
		//Log.v("fbo","gen tx: "+tex.textureId);
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
