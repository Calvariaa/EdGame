package com.edplan.framework.ui.animation;

import com.edplan.framework.interfaces.FloatInvokeSetter;
import com.edplan.framework.interfaces.InvokeSetter;
import com.edplan.framework.ui.animation.interpolate.RawFloatInterpolator;
import com.edplan.framework.ui.animation.interpolate.ValueInterpolator;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import java.util.ArrayList;
import java.util.List;

public class FloatQueryAnimation<T> extends BasePreciseAnimation
{
	private List<AnimNode> nodes=new ArrayList<AnimNode>();

	private RawFloatInterpolator interpolator;

	private FloatInvokeSetter<T> setter;

	private boolean alwaysInitial=true;

	private AnimNode currentNode;

	private double initialOffset;

	private T target;

	public FloatQueryAnimation(T target,double initialOffset,RawFloatInterpolator interpolator,FloatInvokeSetter<T> setter,boolean alwaysInitial){
		this.target=target;
		this.interpolator=interpolator;
		this.setter=setter;
		this.alwaysInitial=alwaysInitial;
		this.initialOffset=initialOffset;
		setStartTime(initialOffset);
	}

	public void transform(float value,double duration,Easing easing){
		if(hasNode()){
			AnimNode pre=getEndNode();
			AnimNode next=new AnimNode(value,pre.endTime,duration,easing);
			pre.next=next;
			next.pre=pre;
			nodes.add(next);
		}else{
			nodes.add(new AnimNode(value,initialOffset,duration,easing));
		}
	}

	public void transform(float value,double startTime,double duration,Easing easing){
		if(hasNode()){
			AnimNode pre=getEndNode();
			if(startTime<pre.endTime){
				double offset=pre.endTime-startTime;
				startTime+=offset;
				duration-=offset;
			}
			AnimNode next=new AnimNode(value,startTime,duration,easing);
			pre.next=next;
			next.pre=pre;
			nodes.add(next);
		}else{
			nodes.add(new AnimNode(value,initialOffset,0,Easing.None));
			transform(value,startTime,duration,easing);
		}
	}

	public void skip(double time){
		transform(getEndNode().value,time,Easing.None);
	}

	public double currentTime(){
		return initialOffset+getProgressTime();
	}

	@Override
	protected void postTime(double deltaTime,double progressTime) {
		// TODO: Implement this method
		if(hasNode()){
			if(currentNode==null){
				currentNode=nodes.get(0);
			}
			while(currentNode.next!=null&&currentNode.next.startTime<currentTime()){
				currentNode=currentNode.next;
			}
			currentNode.apply(currentTime());
		}
	}

	@Override
	protected void seekToTime(double progressTime) {
		// TODO: Implement this method
		/*
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
		 currentNode.apply(currentTime());
		 }
		 }
		 }*/
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
		return (nodes.size()>0)?(getEndNode().endTime-getStartTimeAtTimeline()):0;
	}

	public double getEndNodeTime(){
		return (hasNode())?getEndNode().endTime:0;
	}

	public AnimNode getEndNode(){
		return nodes.size()>0?nodes.get(nodes.size()-1):null;
	}

	@Override
	public void dispos() {
		// TODO: Implement this method
		super.dispos();
		for(AnimNode n:nodes)n.dispos();
		nodes.clear();
	}

	public class AnimNode{
		//这些时间均为ProgressTime
		float value;
		Easing easing;
		double endTime;
		double duration;
		double startTime;

		public AnimNode next;
		public AnimNode pre;

		public AnimNode(float value,double startTime,double duration,Easing easing){
			this.value=value;
			this.duration=duration;
			this.startTime=startTime;
			this.endTime=startTime+duration;
			this.easing=easing;
		}

		public double getEndTime(){
			return endTime;
		}

		public float interplate(double time){
			if(pre==null){
				return value;
			}else{
				return interpolator.applyInterplate(pre.value,value,time,easing);
			}
		}

		public void apply(double progressTime){
			double p=Math.min(1,Math.max(0,(duration==0)?1:((progressTime-startTime)/duration)));
			setter.invoke(target,interplate(p));
		}

		public void dispos(){
			pre=null;
			next=null;
		}
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		StringBuilder sb=new StringBuilder();
		for(AnimNode n:nodes){
			sb.append("->(v:")
				.append(n.value)
				.append(",s:")
				.append(n.startTime)
				.append(",d:")
				.append(n.duration)
				.append(",e:")
				.append(n.endTime)
				.append(")\n");
		}
		return sb.toString();
	}

}