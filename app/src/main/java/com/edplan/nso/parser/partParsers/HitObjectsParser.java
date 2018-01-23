package com.edplan.nso.parser.partParsers;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.ruleset.std.parser.StdHitObjectParser;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.NsoException;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.ruleset.ModeManager;

public class HitObjectsParser implements PartParser<PartHitObjects>
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
	public PartHitObjects getPart(){
		// TODO: Implement this method
		return part;
	}
	
	@Override
	public boolean parse(String l) throws NsoException{
		// TODO: Implement this method
		part.addHitObject(parser.parse(l));
		return true;
	}

	
}
