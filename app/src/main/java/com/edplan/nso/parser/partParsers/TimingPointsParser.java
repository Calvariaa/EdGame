package com.edplan.nso.parser.partParsers;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.filepart.PartTimingPoints;
import com.edplan.nso.Timing.TimingPoint;
import com.edplan.superutils.classes.strings.StringSpliter;
import com.edplan.superutils.U;

public class TimingPointsParser implements PartParser
{
	private PartTimingPoints part;

	public TimingPointsParser(){
		part=new PartTimingPoints();
	}
	
	public TimingPoint parseTimingPoint(String l){
		TimingPoint t=new TimingPoint();
		StringSpliter sp=new StringSpliter(l,",");
		t.setOffset(U.toInt(sp.next()));
		t.setMillionsecondsPerBeat(U.toDouble(sp.next()));
		t.setMeter(U.toInt(sp.next()));
		t.setSampleType(U.toInt(sp.next()));
		t.setSampleSet(U.toInt(sp.next()));
		t.setVolume(U.toInt(sp.next()));
		t.setInherited(U.toBool(sp.next()));
		t.setKiaiMode(U.toBool(sp.next()));
		return t;
	}
	
	@Override
	public OsuFilePart getPart(){
		// TODO: Implement this method
		return part;
	}

	@Override
	public boolean parse(String l){
		// TODO: Implement this method
		l=l.trim();
		if(l==null||l.length()==0){
			return true;
		}else{
			part.addTimingPoint(parseTimingPoint(l));
			return true;
		}
	}

	@Override
	public void applyDefault(){
		// TODO: Implement this method
	}
}
