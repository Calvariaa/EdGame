package com.edplan.framework.ui.animation;
import com.edplan.superutils.classes.SafeList;
import com.edplan.superutils.interfaces.Loopable;
import java.util.Iterator;
import com.edplan.framework.utils.MLog;

public class AnimationHandler extends Loopable
{
	private SafeList<AbstractAnimation> animations=new SafeList<AbstractAnimation>();

	public void addAnimation(AbstractAnimation anim){
		animations.add(anim);
	}
	
	@Override
	public void onLoop(int deltaTime) {
		// TODO: Implement this method
		animations.startIterate();
		Iterator<AbstractAnimation> iter=animations.iterator();
		AbstractAnimation anim;
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
	private boolean postProgress(AbstractAnimation anim,int postTime){
		int p;
		switch(anim.getLoopType()){
			case None:
				p=anim.getProgressTime()+postTime;
				if(p<anim.getDuration()){
					anim.setProgressTime(p);
				}else{
					anim.setProgressTime(anim.getDuration());
					return true;
				}
				break;
			case Loop:
				p=anim.getProgressTime()+postTime;
				if(p<anim.getDuration()){
					anim.setProgressTime(p);
				}else{
					anim.setProgressTime(p%anim.getDuration());
					anim.addLoopCount();
				}
				break;
			case LoopAndReverse:
				//没有进行特别的参数检查，请确保各参数在范围中
				if(anim.getLoopCount()%2==0){
					p=anim.getProgressTime()+postTime;
					if(p<anim.getDuration()){
						anim.setProgressTime(p);
					}else{
						anim.setProgressTime(anim.getDuration()-p%anim.getDuration());
						anim.addLoopCount();
					}
				}else{
					p=anim.getProgressTime()-postTime;
					if(p>0){
						anim.setProgressTime(p);
					}else{
						anim.setProgressTime(-p);
						anim.addLoopCount();
					}
				}
				break;
			case Endless:
				anim.setProgressTime(anim.getProgressTime()+postTime);
				break;
			default:
				MLog.test.vOnce("err-anim-postProgress","?--?","谁让你走到这里的？？( ✘_✘ )↯");
		}
		return false;
	}
	
	private void finishAnimation(AbstractAnimation anim){
		anim.onFinish();
	}
	
	private void skipAnimation(AbstractAnimation anim){
		anim.setProgressTime(anim.getDuration());
	}
	
	private void stopAnimation(AbstractAnimation anim){
		
	}
	
	private void onRemoveAnimation(AbstractAnimation anim){
		anim.onEnd();
	}
}
