package com.edplan.nso.parser.partParsers;
import com.edplan.nso.parser.LinesParser;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.superutils.U;
import com.edplan.nso.OsuFilePart;

public class DifficultyParser implements PartParser
{
	private PartDifficulty part;
	
	public DifficultyParser(){
		part=new PartDifficulty();
	}

	@Override
	public OsuFilePart getPart(){
		// TODO: Implement this method
		return part;
	}

	@Override
	public void applyDefault(){
		// TODO: Implement this method
	}

	@Override
	public boolean parse(String l){
		// TODO: Implement this method
		if(l==null||l.trim().length()==0){
			return true;
		}else{
			String[] entry=U.divide(l,l.indexOf(":"));
			switch(entry[0]){
				case PartDifficulty.ApproachRate:
					part.setApproachRate(U.toFloat(entry[1]));
					return true;
				case PartDifficulty.CircleSize:
					part.setCircleSize(U.toFloat(entry[1]));
					return true;
				case PartDifficulty.HPDrainRate:
					part.setHPDrainRate(U.toFloat(entry[1]));
					return true;
				case PartDifficulty.OverallDifficulty:
					part.setOverallDifficulty(U.toFloat(entry[1]));
					return true;
				case PartDifficulty.SliderMultiplier:
					part.setSliderMultiplier(U.toFloat(entry[1]));
					return true;
				case PartDifficulty.SliderTickRate:
					part.setSliderTickRate(U.toFloat(entry[1]));
					return true;
				default:
					//key not find
					return false;
			}
		}
	}

	
}
