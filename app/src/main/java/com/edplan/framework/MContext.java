package com.edplan.framework;
import com.edplan.superutils.classes.MLooperThread;
import com.edplan.superutils.classes.MLooper;
import com.edplan.superutils.MTimer;
import android.content.Context;
import com.edplan.framework.resource.IResource;
import com.edplan.framework.resource.AssetResource;
import com.edplan.framework.resource.advance.ApplicationAssetResource;
import com.edplan.framework.graphics.opengl.ShaderManager;
import com.edplan.superutils.classes.advance.RunnableHandler;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.looper.UILooper;
import com.edplan.superutils.classes.advance.IRunnableHandler;

public class MContext
{
	private MLooperThread loopThread;
	
	private MLooper looper;
	
	private RunnableHandler runnableHandler;
	
	private MTimer looperTimer;
	
	private ShaderManager shaderManager;
	
	private Context androidContext;
	
	private ApplicationAssetResource assetResource;
	
	private int step;
	
	private EdView content;
	
	private UILooper uiLooper;
	
	public MContext(Context androidContext){
		this.androidContext=androidContext;
		//initial();
	}

	public void setUiLooper(UILooper uiLooper) {
		this.uiLooper=uiLooper;
	}

	public UILooper getUiLooper() {
		return uiLooper;
	}
	
	public int currentUIStep(){
		return getUiLooper().getStep();
	}
	
	public void runOnUIThread(Runnable r){
		getUiLooper().post(r);
	}
	
	public void runOnUIThread(Runnable r,int delayMs){
		getUiLooper().post(r,delayMs);
	}

	public void setContent(EdView content) {
		this.content=content;
	}

	public EdView getContent() {
		return content;
	}

	public void setStep(int step) {
		this.step=step;
	}

	public int getStep() {
		return step;
	}
	
	public IRunnableHandler getRunnableHandler() {
		return uiLooper;
	}
	
	public void initial(){
		assetResource=new ApplicationAssetResource(getNativeContext().getAssets());
		shaderManager=new ShaderManager(getAssetResource().getShaderResource());
	}
	
	public ShaderManager getShaderManager() {
		return shaderManager;
	}
	
	public Context getNativeContext(){
		return androidContext;
	}
	
	public ApplicationAssetResource getAssetResource(){
		return assetResource;
	}
	
	public int getFrameDeltaTime(){
		return uiLooper.getTimer().getDeltaTime();
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
