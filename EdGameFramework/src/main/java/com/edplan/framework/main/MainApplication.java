package com.edplan.framework.main;
import android.app.Activity;
import android.content.Context;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;
import com.edplan.framework.graphics.opengl.MainRenderer;

public abstract class MainApplication
{
	protected Context context;
	
	public abstract MainRenderer createRenderer();
	
	public Context getNativeContext(){
		return context;
	}
	
	public BaseGLSurfaceView createGLView(){
		return new BaseGLSurfaceView(getNativeContext(),createRenderer());
	}
	
	public void setUpActivity(Activity act){
		this.context=act;
		act.setContentView(createGLView());
		onApplicationCreate();
	}
	
	/**
	 *应用被初始化时被调用，GL环境可能尚未搭建
	 */
	public void onApplicationCreate(){
		
	}
	
	/**
	 *GL环境创建好了之后被调用
	 */
	public void onGLCreate(){
		
	}
}
