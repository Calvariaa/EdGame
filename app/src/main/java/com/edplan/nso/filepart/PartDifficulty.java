package com.edplan.nso.filepart;
import com.edplan.nso.OsuFilePart;
import com.edplan.superutils.U;

public class PartDifficulty implements OsuFilePart
{
	public static final String HPDrainRate="HPDrainRate";
	public static final String CircleSize="CircleSize";
	public static final String OverallDifficulty="OverallDifficulty";
	public static final String ApproachRate="ApproachRate";
	public static final String SliderMultiplier="SliderMultiplier";
	public static final String SliderTickRate="SliderTickRate";
	
	public static final String TAG="Difficulty";
	
	private float hPDrainRate;
	private float circleSize;
	private float overallDifficulty;
	private float approachRate;
	private float sliderMultiplier;
	private float sliderTickRate;

	public void setHPDrainRate(float hPDrainRate){
		this.hPDrainRate=hPDrainRate;
	}

	public float getHPDrainRate(){
		return hPDrainRate;
	}

	public void setCircleSize(float circleSize){
		this.circleSize=circleSize;
	}

	public float getCircleSize(){
		return circleSize;
	}

	public void setOverallDifficulty(float overallDifficulty){
		this.overallDifficulty=overallDifficulty;
	}

	public float getOverallDifficulty(){
		return overallDifficulty;
	}

	public void setApproachRate(float approachRate){
		this.approachRate=approachRate;
	}

	public float getApproachRate(){
		return approachRate;
	}

	public void setSliderMultiplier(float sliderMultiplier){
		this.sliderMultiplier=sliderMultiplier;
	}

	public float getSliderMultiplier(){
		return sliderMultiplier;
	}

	public void setSliderTickRate(float sliderTickRate){
		this.sliderTickRate=sliderTickRate;
	}

	public float getSliderTickRate(){
		return sliderTickRate;
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
		U.appendProperty(sb,PartDifficulty.HPDrainRate       ,getHPDrainRate()       ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartDifficulty.CircleSize        ,getCircleSize()        ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartDifficulty.OverallDifficulty ,getOverallDifficulty() ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartDifficulty.ApproachRate      ,getApproachRate()      ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartDifficulty.SliderMultiplier  ,getSliderMultiplier()  ).append(U.NEXT_LINE);
		U.appendProperty(sb,PartDifficulty.SliderTickRate    ,getSliderTickRate()    ).append(U.NEXT_LINE);
		return sb.toString();
	}
}
