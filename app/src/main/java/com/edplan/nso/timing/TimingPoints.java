package com.edplan.nso.timing;
import com.edplan.superutils.interfaces.StringMakeable;
import java.util.ArrayList;
import com.edplan.superutils.U;

public class TimingPoints implements StringMakeable
{
	private ArrayList<TimingPoint> timings;
	
	public TimingPoints(){
		timings=new ArrayList<TimingPoint>();
	}
	
	public void addTimingPoint(TimingPoint t){
		timings.add(t);
	}
	
	@Override
	public String makeString(){
		// TODO: Implement this method
		StringBuilder sb=new StringBuilder();
		for(TimingPoint t:timings){
			sb.append(t.makeString()).append(U.NEXT_LINE);
		}
		return sb.toString();
	}
}
