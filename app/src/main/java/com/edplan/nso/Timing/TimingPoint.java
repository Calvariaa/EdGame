package com.edplan.nso.Timing;
import com.edplan.superutils.interfaces.StringMakeable;
import com.edplan.superutils.U;
import com.edplan.nso.beatmapComponent.SampleSet;

public class TimingPoint implements StringMakeable
{
	private int offset;
	
	private double millionsecondsPerBeat;
	
	private int meter;
	
	private int sampleType;
	
	private SampleSet sampleSet=SampleSet.None;
	
	private int volume=100;
	
	private boolean inherited=true;
	
	private boolean kiaiMode=false;
	
	private boolean omitFirstBarSignature=false;

	public void setOmitFirstBarSignature(boolean omitFirstBarSignature) {
		this.omitFirstBarSignature=omitFirstBarSignature;
	}

	public boolean isOmitFirstBarSignature() {
		return omitFirstBarSignature;
	}

	public void setSampleSet(SampleSet sampleSet){
		this.sampleSet=sampleSet;
	}

	public SampleSet getSampleSet(){
		return sampleSet;
	}

	public void setOffset(int offset){
		this.offset=offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setMillionsecondsPerBeat(double millionsecondsPerBeat){
		this.millionsecondsPerBeat=millionsecondsPerBeat;
	}

	public double getMillionsecondsPerBeat(){
		return millionsecondsPerBeat;
	}

	public void setMeter(int meter){
		this.meter=meter;
	}

	public int getMeter(){
		return meter;
	}

	public void setSampleType(int sampleType){
		this.sampleType=sampleType;
	}

	public int getSampleType(){
		return sampleType;
	}

	public void setVolume(int volume){
		this.volume=volume;
	}

	public int getVolume(){
		return volume;
	}

	public void setInherited(boolean inherited){
		this.inherited=inherited;
	}

	public boolean isInherited(){
		return inherited;
	}

	public void setKiaiMode(boolean kiaiMode){
		this.kiaiMode=kiaiMode;
	}

	public boolean isKiaiMode(){
		return kiaiMode;
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		StringBuilder sb=new StringBuilder();
		sb.append(getOffset()                ).append(",");
		sb.append(getMillionsecondsPerBeat() ).append(",");
		sb.append(getMeter()                 ).append(",");
		sb.append(getSampleType()            ).append(",");
		sb.append(getSampleSet()             ).append(",");
		sb.append(U.toVString(isInherited()) ).append(",");
		sb.append(((isKiaiMode())?1:0)+((isOmitFirstBarSignature())?8:0));
		return sb.toString();
	}
}
