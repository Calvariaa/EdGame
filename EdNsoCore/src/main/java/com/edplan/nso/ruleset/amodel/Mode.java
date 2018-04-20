package com.edplan.nso.ruleset.amodel;
import com.edplan.nso.OsuBeatmap;
import java.io.InputStream;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.OsuFilePart;

public interface Mode
{
	public int getModeId();
	
	public OsuFilePart parseFilePart();
}
