package com.edplan.framework;
import com.edplan.superutils.classes.MLooperThread;
import com.edplan.superutils.classes.MLooper;
import com.edplan.superutils.MTimer;
import android.content.Context;
import com.edplan.framework.resource.AResource;
import com.edplan.framework.resource.AssetResource;
import com.edplan.framework.resource.advance.ApplicationAssetResource;
import com.edplan.framework.graphics.opengl.ShaderManager;
import com.edplan.superutils.classes.advance.RunnableHandler;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.looper.UILooper;
import com.edplan.superutils.classes.advance.IRunnableHandler;
import com.edplan.framework.ui.looper.UIStep;
import com.edplan.framework.ui.ViewRoot;

public class MContext
{
	private Thread mainThread;
	
	private MLooperThread loopThread;
	
	private MLooper looper;
	
	private RunnableHandler runnableHandler;
	
	private MTimer looperTimer;
	
	private ShaderManager shaderManager;
	
	private Context androidContext;
	
	private ApplicationAssetResource assetResource;
	
	private int step;
	
	private UILooper uiLooper;
	
	private int displayWidth,displayHeight;
	
	private ViewRoot viewRoot;
	
	public MContext(Context androidContext){
		this.androidContext=androidContext;
		//initial();
	}

	public void setViewRoot(ViewRoot viewRoot){
		this.viewRoot=viewRoot;
	}

	public ViewRoot getViewRoot(){
		return viewRoot;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public Thread getMainThread() {
		return mainThread;
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
	
	/**
	 *在主线程上运行，当当前就是在HANDLE_RUNNABLES时立马执行
	 */
	public void runOnUIThread(Runnable r){
		if(currentUIStep()==UIStep.HANDLE_RUNNABLES){
			r.run();
		}else{
			getUiLooper().post(r);
		}
	}
	
	public void runOnUIThread(Runnable r,int delayMs){
		getUiLooper().post(r,delayMs);
	}

	/*
	public void setContent(EdView content) {
		this.content=content;
	}

	public EdView getContent() {
		return content;
	}
	*/

	/*
	public void setStep(int step) {
		this.step=step;
	}

	public int getStep() {
		return step;
	}*/
	
	private IRunnableHandler getRunnableHandler() {
		return uiLooper;
	}
	
	public void setDisplaySize(int w,int h){
		this.displayWidth=w;
		this.displayHeight=h;
	}
	
	public void initial(){
		mainThread=Thread.currentThread();
		assetResource=new ApplicationAssetResource(getNativeContext().getAssets());
		ShaderManager.initStatic(this);
	}
	
	public Context getNativeContext(){
		return androidContext;
	}
	
	public ApplicationAssetResource getAssetResource(){
		return assetResource;
	}
	
	public double getFrameDeltaTime(){
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
