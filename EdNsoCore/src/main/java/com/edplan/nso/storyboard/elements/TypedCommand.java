package com.edplan.nso.storyboard.elements;
import com.edplan.framework.ui.animation.Easing;

public class TypedCommand<T> implements ICommand
{
	private Easing easing;
	private double startTime;
	private double endTime;
	
	private T startValue;
	private T endValue;

	public TypedCommand(Easing e,double startTime,double endTime,T startValue,T endValue){
		this.easing=e;
		this.startTime=startTime;
		this.endTime=endTime;
		this.startValue=startValue;
		this.endValue=endValue;
	}
	
	public void setStartValue(T startValue) {
		this.startValue=startValue;
	}

	public T getStartValue() {
		return startValue;
	}

	public void setEndValue(T endValue) {
		this.endValue=endValue;
	}

	public T getEndValue() {
		return endValue;
	}
	
	@Override
	public Easing getEasing() {
		// TODO: Implement this method
		return easing;
	}

	@Override
	public void setEasing(Easing easing) {
		// TODO: Implement this method
		this.easing=easing;
	}

	@Override
	public double getStartTime() {
		// TODO: Implement this method
		return startTime;
	}

	@Override
	public void setStartTime(double startTime) {
		// TODO: Implement this method
		this.startTime=startTime;
	}

	@Override
	public double getEndTime() {
		// TODO: Implement this method
		return endTime;
	}

	@Override
	public void setEndTime(double endTime) {
		// TODO: Implement this method
		this.endTime=endTime;
	}

	@Override
	public double getDuration() {
		// TODO: Implement this method
		return endTime-startTime;
	}

	@Override
	public int compareTo(ICommand other) {
		// TODO: Implement this method
		int result=(int)Math.signum(other.getStartTime()-getStartTime());
		if(result!=0){
			return result;
		}else{
			return (int)Math.signum(other.getEndTime()-getEndTime());
		}
	}
}
