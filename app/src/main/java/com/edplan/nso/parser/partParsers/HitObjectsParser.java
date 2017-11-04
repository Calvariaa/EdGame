package com.edplan.nso.parser.partParsers;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.Ruleset.std.parser.StdHitObjectParser;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.NsoException;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.Ruleset.ModeManager;

public class HitObjectsParser implements PartParser
{
	private ParsingBeatmap parsingBeatmap;
	private PartHitObjects part;
	private StdHitObjectParser parser;
	
	public HitObjectsParser(ParsingBeatmap pb){
		parsingBeatmap=pb;
	}
	
	public void initial(int mode) throws NsoException{
		part=new PartHitObjects();
		part.initial(mode);
		switch(mode){
			case ModeManager.MODE_STD:
			case ModeManager.MODE_MANIA:
				parser=new StdHitObjectParser(parsingBeatmap);
				break;
			default:
				throw new NsoException("invalid mode : "+mode);
		}
	}

	@Override
	public OsuFilePart getPart(){
		// TODO: Implement this method
		return part;
	}
	
	@Override
	public boolean parse(String l) throws NsoException{
		// TODO: Implement this method
		part.addHitObject(parser.parse(l));
		return true;
	}

	@Override
	public void applyDefault(){
		// TODO: Implement this method
	}

	
}
