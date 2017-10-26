package com.edplan.nso.parser.partParsers;
import com.edplan.nso.parser.LinesParser;
import com.edplan.nso.OsuFilePart;

public interface PartParser extends LinesParser
{
	public void applyDefault();
	
	public OsuFilePart getPart();
}
