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
	public static class depthTest{
		private static SRable<BooleanCopyable> saves;
		static{
			saves=new SRable<BooleanCopyable>(new SROperation<BooleanCopyable>(){
					@Override
					public void onSave(BooleanCopyable v) {
						// TODO: Implement this method
					}

					@Override
					public void onRestore(BooleanCopyable now,BooleanCopyable pre) {
						// TODO: Implement this method
						set(now.getValue());
					}

					@Override
					public BooleanCopyable getDefData() {
						// TODO: Implement this method
						return new BooleanCopyable(false);
					}
				});
			saves.initial();
		}
		
		public static void set(boolean v){
			if(saves.getData().getValue()!=v){
				if(v){
					GLES20.glEnable(GLES20.GL_DEPTH_TEST);
				}else{
					GLES20.glDisable(GLES20.GL_DEPTH_TEST);
				}
				saves.getData().setValue(v);
			}
		}
		
		public static int save(){
			return saves.save();
		}
		
		public static void restore(){
			saves.restore();
		}
		
		public static void restorToCount(int id){
			saves.restoreToCount(id);
		}
	}*/
	
	
	
	
	//private static boolean depthTest=false;
	
	public static void initial(){
		//depthTest=false;
		//GLES20.glDisable(GLES20.GL_DEPTH_TEST);
	}
	
	public static void enableBlend(){
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
		//GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_DST_ALPHA);
	}
	
	public static void setViewport(int x1,int y1,int x2,int y2){
		GLES20.glViewport(x1,y1,x2,y2);
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
}
