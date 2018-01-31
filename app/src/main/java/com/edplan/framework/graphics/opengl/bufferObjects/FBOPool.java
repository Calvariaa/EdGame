package com.edplan.framework.graphics.opengl.bufferObjects;
import java.util.ArrayList;
import java.util.Iterator;
import android.util.Log;

public class FBOPool
{
	private int maxMemory=1080*1920*20*0;
	
	private int currentMemory=0;
	
	private int minWidth=120;
	
	private int minHeight=120;
	
	private ArrayList<WrappedEntry> list=new ArrayList<WrappedEntry>();
	
	
	/**
	 *想内存池申请一个width,height的FBO，返回的FBO长宽不小于申请的，当没申请到的时候返回null
	 */
	public FrameBufferObject requireFBO(int width,int height){
		Iterator<WrappedEntry> iter=list.iterator();
		WrappedEntry e;
		while(iter.hasNext()){
			e=iter.next();
			if(e.width>=width&&e.height>=height){
				FrameBufferObject fbo=e.fbo;
				fbo.setWidth(width);
				fbo.setHeight(height);
				//if(fbo.isBind())fbo.unBind();
				iter.remove();
				currentMemory-=e.width*e.height;
				//Log.v("fbo-pool","pop a "+e.width+"x"+e.height+" fbo. Current memory:"+currentMemory+" cur size: "+list.size());
				return fbo;
			}
		}
		return null;
	}
	
	
	/**
	 *返回fbo是否被保存
	 */
	public boolean saveFBO(FrameBufferObject fbo){
		//Log.v("fbo-pool","onSaveFbo "+fbo.getFBOId());
		if(fbo.isBind())throw new RuntimeException("you can't save a binded fbo into pool");
		if(fbo.getCreatedHeight()<minHeight||fbo.getCreatedWidth()<minWidth){
			//不保存过小的fbo
			return false;
		}else{
			if(currentMemory>maxMemory){
				return false;
			}else{
				list.add(new WrappedEntry(fbo));
				currentMemory+=fbo.getCreatedHeight()*fbo.getCreatedWidth();
				return true;
			}
		}
	}
	
	public static class WrappedEntry{
		
		public int width;
		public int height;
		public FrameBufferObject fbo;
		
		public WrappedEntry(FrameBufferObject fbo){
			this.fbo=fbo;
			this.width=fbo.getCreatedWidth();
			this.height=fbo.getCreatedHeight();
		}
	}
	
	public static class HeightList extends ArrayList<WrappedEntry>{
		
	}
}
