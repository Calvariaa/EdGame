package com.edplan.nso.parser.partParsers;
import com.edplan.nso.parser.LinesParser;
import com.edplan.nso.filepart.PartGeneral;
import com.edplan.superutils.U;
import com.edplan.nso.beatmapComponent.SampleSet;
import com.edplan.nso.OsuFilePart;

public class GeneralParser implements PartParser<PartGeneral>
{
	public PartGeneral part;
	
	public GeneralParser(){
		part=new PartGeneral();
	}

	@Override
	public PartGeneral getPart(){
		// TODO: Implement this method
		return part;
	}

	@Override
	public boolean parse(String l){
		// TODO: Implement this method
		if(l==null||l.trim().length()==0){
			return true;
		}else{
			String[] entry=U.divide(l,l.indexOf(":"));
			if(entry.length<2){
				return false;
			}else{
				switch(entry[0]){
					case PartGeneral.AudioFilename:
						part.setAudioFilename(entry[1]);
						return true;
					case PartGeneral.AudioLeadIn:
						part.setAudioLeadIn(U.toInt(entry[1]));
						return true;
					case PartGeneral.Countdown:
						part.setCountdown(U.toBool(entry[1]));
						return true;
					case PartGeneral.LetterboxInBreaks:
						part.setLetterboxInBreaks(U.toBool(entry[1]));
						return true;
					case PartGeneral.Mode:
						part.setMode(U.toInt(entry[1]));
						return true;
					case PartGeneral.PreviewTime:
						part.setPreviewTime(U.toInt(entry[1]));
						return true;
					case PartGeneral.SampleSet:
						part.setSampleSet(SampleSet.parse(entry[1]));
						return true;
					case PartGeneral.SampleVolume:
						part.setSampleVolume(U.toInt(entry[1]));
						return true;
					case PartGeneral.StackLeniency:
						part.setStackLeniency(U.toFloat(entry[1]));
						return true;
					case PartGeneral.WidescreenStoryboard:
						part.setWidescreenStoryboard(U.toBool(entry[1]));
						return true;
					case PartGeneral.SpecialStyle:
						part.setSpecialStyle(U.toBool(entry[1]));
						return true;
					case PartGeneral.EpilepsyWarning:
						part.setEpilepsyWarning(U.toBool(entry[1]));
						return true;
					default:
						//handler err post
						return false;
				}
			}
		}
	}
}
