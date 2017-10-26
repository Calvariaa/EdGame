package com.edplan.nso.filepart;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.beatmapComponent.SampleSet;
import java.util.Map;
import com.edplan.superutils.U;

public class PartGeneral implements OsuFilePart
{
	public static final String AudioFilename="AudioFilename";
	public static final String AudioLeadIn="AudioLeadIn";
	public static final String PreviewTime="PreviewTime";
	public static final String Countdown="Countdown";
	public static final String SampleSet="SampleSet";
	public static final String StackLeniency="StackLeniency";
	public static final String Mode="Mode";
	public static final String LetterboxInBreaks="LetterboxInBreaks";
	public static final String WidescreenStoryboard="WidescreenStoryboard";
	
	
	
	public static final String TAG="General";
	
	private String audioFilename="";
	private int audioLeadIn=0;
	private int previewTime=0;
	private boolean countdown=false;
	private SampleSet sampleSet=null;
	private float stackLeniency=0;
	private int mode=-1;
	private boolean letterboxInBreaks=false;
	private boolean widescreenStoryboard=false;

	public PartGeneral(){
		//Map<String,Object> map=U.makeMap(String.class,Object.class,);
	}
	
	
	
	public void setWidescreenStoryboard(boolean widescreenStoryboard){
		this.widescreenStoryboard=widescreenStoryboard;
	}

	public boolean isWidescreenStoryboard(){
		return widescreenStoryboard;
	}

	public void setAudioFilename(String audioFilename){
		this.audioFilename=audioFilename;
	}

	public String getAudioFilename(){
		return audioFilename;
	}

	public void setAudioLeadIn(int audioLeadIn){
		this.audioLeadIn=audioLeadIn;
	}

	public int getAudioLeadIn(){
		return audioLeadIn;
	}

	public void setPreviewTime(int previewTime){
		this.previewTime=previewTime;
	}

	public int getPreviewTime(){
		return previewTime;
	}

	public void setCountdown(boolean countdown){
		this.countdown=countdown;
	}

	public boolean ifCountdown(){
		return countdown;
	}

	public void setSampleSet(SampleSet sampleSet){
		this.sampleSet=sampleSet;
	}

	public SampleSet getSampleSet(){
		return sampleSet;
	}

	public void setStackLeniency(float stackLeniency){
		this.stackLeniency=stackLeniency;
	}

	public float getStackLeniency(){
		return stackLeniency;
	}

	public void setMode(int mode){
		this.mode=mode;
	}

	public int getMode(){
		return mode;
	}

	public void setLetterboxInBreaks(boolean letterboxInBreaks){
		this.letterboxInBreaks=letterboxInBreaks;
	}

	public boolean isLetterboxInBreaks(){
		return letterboxInBreaks;
	}
	
	@Override
	public String getTag(){
		// TODO: Implement this method
		return TAG;
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		StringBuilder sb=new StringBuilder();
		U.appendProperty(sb,PartGeneral.AudioFilename        ,getAudioFilename()                    ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.AudioLeadIn          ,getAudioLeadIn()                      ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.PreviewTime          ,getPreviewTime()                      ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.Countdown            ,U.toVString(ifCountdown())            ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.SampleSet            ,getSampleSet().makeString()           ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.StackLeniency        ,getStackLeniency()                    ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.Mode                 ,getMode()                             ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.LetterboxInBreaks    ,U.toVString(isLetterboxInBreaks())    ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartGeneral.WidescreenStoryboard ,U.toVString(isWidescreenStoryboard()) ).append(U.NEXT_LINE);
		
		return sb.toString();
	}
}
