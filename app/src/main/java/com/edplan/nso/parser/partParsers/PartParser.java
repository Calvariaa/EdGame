package com.edplan.nso.parser.partParsers;
import com.edplan.nso.parser.LinesParser;
import com.edplan.nso.OsuFilePart;

public interface PartParser<T extends OsuFilePart> extends LinesParser
{
	public T getPart();
}
