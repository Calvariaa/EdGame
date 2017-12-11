package com.edplan.framework;
import com.edplan.superutils.classes.MLooperThread;
import com.edplan.superutils.classes.MLooper;
import com.edplan.superutils.MTimer;
import android.content.Context;
import com.edplan.framework.resource.IResource;
import com.edplan.framework.resource.AssetResource;
import com.edplan.framework.resource.advance.ApplicationAssetResource;

public class MContext
{
	private MLooperThread loopThread;
	
	private MLooper looper;
	
	private MTimer looperTimer;
	
	
	
	private Context androidContext;
	
	private ApplicationAssetResource assetResource;
	
	public MContext(Context androidContext){
		this.androidContext=androidContext;
		initial();
	}
	
	public void initial(){
		assetResource=new ApplicationAssetResource(getNativeContext().getAssets());
	}
	
	public Context getNativeContext(){
		return androidContext;
	}
	
	public ApplicationAssetResource getAssetResource(){
		return assetResource;
	}
	
	public int getFrameDeltaTime(){
		return getLooper().getTimer().getDeltaTime();
	}
	
	public MLooper getLooper(){
		return looper;
	}
	
	public void setLoopThread(MLooperThread loopThread){
		this.loopThread=loopThread;
		looper=loopThread.getLooper();
		looperTimer=looper.getTimer();
	}

	public MLooperThread getLoopThread(){
		return loopThread;
	}
}
