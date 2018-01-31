package com.edplan.framework.timing;
import com.edplan.framework.ui.animation.precise.AbstractPreciseAnimation;
import com.edplan.framework.utils.MLog;
import com.edplan.superutils.classes.SafeList;
import com.edplan.superutils.interfaces.Loopable;
import java.util.Iterator;

public class PreciseTimeline extends Loopable
{
	private int latestFrameTime=0;
	
	private long latestFrameRealTime;
	
	private int preFrameDeltaTime;
	
	private long savedRealTime;
	
	private boolean pausing=false;
	
	private boolean askPause=false;
	
	private boolean hasRestart=true;
	
	private boolean askRestart=false;
	
	private SafeList<AbstractPreciseAnimation> animations=new SafeList<AbstractPreciseAnimation>();

	public void addAnimation(AbstractPreciseAnimation a){
		animations.add(a);
	}
	
	@Override
	public void onLoop(int deltaTime) {
		// TODO: Implement this method
		if(pausing){
			
		}else{
			if(askPause){
				//接受暂停请求时应该将当前帧处理完
				handleFrame(deltaTime);
				savedRealTime=System.currentTimeMillis();
				pausing=true;
				hasRestart=false;
				onPause();
			}else if(hasRestart){
				handleFrame(deltaTime);
			}else{
				savedRealTime=-1;
				hasRestart=true;
				onRestart();
				handleFrame(deltaTime);
			}
		}
	}
	
	protected void handleFrame(int deltaTime){
		this.preFrameDeltaTime=deltaTime;
		latestFrameTime+=deltaTime;
		latestFrameRealTime=getRealTime();
		handlerAnimation(deltaTime);
	}
	
	protected void handlerAnimation(int deltaTime){
		animations.startIterate();
		Iterator<AbstractPreciseAnimation> iter=animations.iterator();
		AbstractPreciseAnimation anim;
		while(iter.hasNext()){
			anim=iter.next();
			switch(anim.getState()){
				case Waiting:
					break;
				case Skip:
					skipAnimation(anim);
					onRemoveAnimation(anim);
					iter.remove();
					break;
				case Stop:
					stopAnimation(anim);
					onRemoveAnimation(anim);
					iter.remove();
					break;
				case Running:
					if(postProgress(anim,deltaTime)){
						finishAnimation(anim);
						onRemoveAnimation(anim);
						iter.remove();
					}
					break;
				default:
					MLog.test.vOnce("switch-err","???","什么鬼啊");
			}
		}
		animations.endIterate();
	}
	
	/**
	 *返回值代表此动画是否Finish
	 */
	private boolean postProgress(AbstractPreciseAnimation anim,int postTime){
		int p;
		p=frameTime()-anim.getStartTimeAtTimeline();
		if(p>=0)if(p<anim.getDuration()){
			if(!anim.hasStart())anim.onStart();
			anim.setProgressTime(p);
			anim.onProgress(p);
		}else{
			p=anim.getDuration();
			if(!anim.hasStart())anim.onStart();
			anim.setProgressTime(p);
			anim.onProgress(p);
			return true;
		}
		return false;
	}

	private void finishAnimation(AbstractPreciseAnimation anim){
		anim.onFinish();
	}

	private void skipAnimation(AbstractPreciseAnimation anim){
		anim.setProgressTime(anim.getDuration());
	}

	private void stopAnimation(AbstractPreciseAnimation anim){

	}

	private void onRemoveAnimation(AbstractPreciseAnimation anim){
		anim.onEnd();
	}
	
	
	/**
	 *暂停Timeline，不会立即暂停，会等到接受下一桢时才处理
	 */
	public void pause(){
		askPause=true;
		askRestart=false;
	}
	
	/**
	 *暂停后的重启，同样不会立即执行
	 */
	public void restart(){
		pausing=false;
	}
	
	/**
	 *触发暂停时的回调
	 */
	protected void onPause(){
		
	}

	/**
	 *被暂停又被恢复后，第一次进入新一帧的时候被调用
	 */
	protected void onRestart(){
		
	}

	public void setLatestFrameRealTime(long latestFrameRealTime) {
		this.latestFrameRealTime=latestFrameRealTime;
	}

	public long getLatestFrameRealTime() {
		return latestFrameRealTime;
	}
	
	/**
	 *通过savedRealTime解决暂停问题
	 */
	public long getRealTime(){
		return (savedRealTime!=-1)?savedRealTime:System.currentTimeMillis();
	}

	/**
	 *当前准确时间（时间轴上的）
	 */
	public int currentTime(){
		return latestFrameTime+(int)((getRealTime()-getLatestFrameRealTime()));
	}
	
	/**
	 *当前帧刷新时的时间
	 */
	public int frameTime(){
		return latestFrameTime;
	}

	/**
	 *上一帧到这一帧的时间差
	 */
	public int deltaTime(){
		return preFrameDeltaTime;
	}
}
