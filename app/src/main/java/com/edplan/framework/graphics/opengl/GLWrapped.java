package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import android.util.Log;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.utils.AbstractSRable;
import com.edplan.framework.utils.SRable;
import com.edplan.framework.utils.SRable.SROperation;
import com.edplan.framework.utils.advance.BooleanCopyable;
import com.edplan.framework.utils.advance.BooleanSetting;
import com.edplan.framework.interfaces.Setter;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.interfaces.Copyable;
import com.edplan.framework.graphics.opengl.bufferObjects.FBOPool;

public class GLWrapped
{
	public static final
	BooleanSetting depthTest=new BooleanSetting(new Setter<Boolean>(){
			@Override
			public void set(Boolean t) {
				// TODO: Implement this method
				if(t){
					GLES20.glEnable(GLES20.GL_DEPTH_TEST);
				}else{
					GLES20.glDisable(GLES20.GL_DEPTH_TEST);
				}
			}
		},
		false).initial();

	/*
	public static final
	BooleanSetting blend=new BooleanSetting(new Setter<Boolean>(){
			@Override
			public void set(Boolean t) {
				// TODO: Implement this method
				if(t){
					GLES20.glEnable(GLES20.GL_BLEND);
					//GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
					GLES20.glBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE_MINUS_SRC_ALPHA);
					//GLES20.glBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE);
				}else{
					GLES20.glDisable(GLES20.GL_BLEND);
				}
			}
		},
		false).initial();
	*/
	
	public static BlendSetting blend=new BlendSetting().setUp();
	
	public static void initial(){
		//depthTest=false;
		//GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		GLTexture.initial();
		FBOPool.initialGL();
	}
	
	private static int px1,px2,py1,py2;
	public static void setViewport(int x1,int y1,int x2,int y2){
		//if(!(px1==x1&&px2==x2&&py1==y1&&py2==y2)){
			GLES20.glViewport(x1,y1,x2-x1,y2-y1);
			px1=x1;
			px2=x2;
			py1=y1;
			py2=y2;
		//}
	}
	
	public static void setClearColor(float r,float g,float b,float a){
		GLES20.glClearColor(r,g,b,a);
	}
	
	public static void clearColorBuffer(){
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearDepthBuffer(){
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clearDepthAndColorBuffer(){
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
	}
	
	/*
	public static void setDepthTest(boolean f){
		if(f!=depthTest){
			if(f){
				GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			}else{
				GLES20.glDisable(GLES20.GL_DEPTH_TEST);
			}
			depthTest=f;
		}
	}*/
	
	public static void clearColor(Color4 c){
		GLES20.glClearColor(c.r,c.g,c.b,c.a);
	}
		
	public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("ES20_ERROR", op + ": glError " + error);
            throw new GLException(op + ": glError " + error);
        }
	}
	
	public static class BlendSetting extends AbstractSRable<BlendPropert> {
		
		public BlendSetting(){
			
		}

		public BlendSetting setUp(){
			initial();
			getData().applyToGL();
			return this;
		}
		
		public boolean isEnable(){
			return getData().enable;
		}
		
		public BlendType getBlendType(){
			return getData().blendType;
		}
		
		public void set(boolean enable,BlendType blendType){
			BlendPropert prop=new BlendPropert(enable,blendType);
			if(!getData().equals(prop)){
				setCurrentData(prop);
				prop.applyToGL();
			}
		}
		
		public void setEnable(boolean enable){
			set(enable,getBlendType());
		}
		
		public void setBlendType(BlendType type){
			set(isEnable(),type);
		}
		
		@Override
		public void onSave(BlendPropert t) {
			// TODO: Implement this method
		}

		@Override
		public void onRestore(BlendPropert now,BlendPropert pre) {
			// TODO: Implement this method
			if(!now.equals(pre)){
				now.applyToGL();
			}
		}

		@Override
		public GLWrapped.BlendPropert getDefData() {
			// TODO: Implement this method
			return new BlendPropert();
		}
	}
	
	public static class BlendPropert implements Copyable {
		
		public boolean enable=true;
		
		public BlendType blendType=BlendType.Normal;
		
		public BlendPropert(){
			
		}
		
		public BlendPropert(BlendPropert b){
			set(b);
		}
		
		public BlendPropert(boolean e,BlendType t){
			this.enable=e;
			this.blendType=t;
		}
		
		public void set(BlendPropert b){
			this.enable=b.enable;
			this.blendType=b.blendType;
		}
		
		public void applyToGL(){
			if(enable){
				GLES20.glEnable(GLES20.GL_BLEND);
				GLES20.glBlendFunc(blendType.srcType,blendType.dstType);
			}else{
				GLES20.glDisable(GLES20.GL_BLEND);
			}
		}

		@Override
		public boolean equals(Object obj) {
			// TODO: Implement this method
			if(obj instanceof BlendPropert){
				BlendPropert b=(GLWrapped.BlendPropert) obj;
				return (enable==b.enable)&&(blendType==b.blendType);
			}else return false;
		}
		
		@Override
		public Copyable copy() {
			// TODO: Implement this method
			return new BlendPropert(this);
		}
	}
}
