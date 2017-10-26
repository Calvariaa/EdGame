package com.edplan.nso.filepart;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.Timing.TimingPoints;
import com.edplan.nso.Timing.TimingPoint;

public class PartTimingPoints implements OsuFilePart
{
	public static final String TAG="TimingPoints";
	
	public TimingPoints timingPoints=null;
	
	public PartTimingPoints(){
		timingPoints=new TimingPoints();
	}

	public void setTimingPoints(TimingPoints timingPoints){
		this.timingPoints=timingPoints;
	}

	public TimingPoints getTimingPoints(){
		return timingPoints;
	}
	
	public void addTimingPoint(TimingPoint t){
		getTimingPoints().addTimingPoint(t);
	}
	
	@Override
	public String getTag(){
		// TODO: Implement this method
		return TAG;
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		return (timingPoints!=null)?timingPoints.makeString():"{@TimingPoints}";
	}

}
