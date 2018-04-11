package com.edplan.framework.ui.animation;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.ui.animation.interpolate.IInterpolator;
import com.edplan.framework.ui.animation.interpolate.ValueInterpolator;
import com.edplan.framework.interfaces.Setter;
import com.edplan.framework.interfaces.InvokeSetter;

public class QueryAnimation<T,V> extends BasePreciseAnimation
{
	private List<AnimNode> nodes=new ArrayList<AnimNode>();
	
	private ValueInterpolator<V> interpolator;
	
	private InvokeSetter<T,V> setter;
	
	private boolean alwaysInitial=true;
	
	private AnimNode currentNode;
	
	private T target;
	
	public QueryAnimation(T target,ValueInterpolator<V> interpolator,InvokeSetter<T,V> setter,boolean alwaysInitial){
		this.target=target;
		this.interpolator=interpolator;
		this.setter=setter;
		this.alwaysInitial=alwaysInitial;
	}
	
	public void transform(V value,double duration,Easing easing){
		if(hasNode()){
			AnimNode pre=getEndNode();
			AnimNode next=new AnimNode(value,pre.endTime,duration,easing);
			pre.next=next;
			nodes.add(next);
		}else{
			nodes.add(new AnimNode(value,0,duration,easing));
		}
	}
	
	@Override
	protected void postTime(double deltaTime,double progressTime) {
		// TODO: Implement this method
		if(hasNode()&&progressTime<getDuration()){
			if(currentNode==null){
				seekToTime(progressTime);
			}else{
				while(currentNode.endTime<progressTime){
					if(currentNode.next==null){
						break;
					}else{
						currentNode=currentNode.next;
					}
				}
				currentNode.apply(progressTime);
			}
		}
	}

	@Override
	protected void seekToTime(double progressTime) {
		// TODO: Implement this method
		if(hasNode()){
			int idx=search(progressTime);
			if(idx==-1){
				if(alwaysInitial)setter.invoke(target,nodes.get(0).value);
				return;
			}else{
				if(idx==nodes.size()){
					setter.invoke(target,getEndNode().value);
				}else{
					currentNode=nodes.get(idx);
					currentNode.apply(progressTime);
				}
			}
		}
	}
	
	public boolean hasNode(){
		return nodes.size()!=0;
	}
	
	// obj(i).start<= time <obj(i).end=obj(i+1).start
	private int search(double time){
		if(time<0){
			return -1;
		}else if(time>getDuration()){
			return nodes.size();
		}else{
			int i=0;
			while(time<nodes.get(i).startTime){
				i++;
				if(i==nodes.size()){
					return i;
				}
			}
			while(nodes.get(i).endTime<=time){
				i++;
				if(i==nodes.size()){
					return i;
				}
			}
			return i;
		}
	}

	@Override
	public double getDuration() {
		// TODO: Implement this method
		return (nodes.size()>0)?(getEndNode().endTime):0;
	}
	
	private AnimNode getEndNode(){
		return nodes.size()>0?nodes.get(nodes.size()-1):null;
	}
	
	public class AnimNode{
		//这些时间均为ProgressTime
		V value;
		Easing easing;
		double endTime;
		double duration;
		double startTime;
		
		public AnimNode next;
		
		public AnimNode(V value,double startTime,double duration,Easing easing){
			this.value=value;
			this.duration=duration;
			this.startTime=startTime;
			this.endTime=startTime+duration;
			this.easing=easing;
		}
		
		public V interplate(double time){
			if(next==null){
				return value;
			}else{
				return interpolator.applyInterplate(value,next.value,time,easing);
			}
		}
		
		public void apply(double progressTime){
			double p=(currentNode.duration==0)?0:((progressTime-currentNode.startTime)/currentNode.duration);
			setter.invoke(target,currentNode.interplate(p));
		}
	}
}
