package com.edplan.nso.ruleset.std;
import com.edplan.nso.ruleset.amodel.Mode;
import com.edplan.nso.OsuBeatmap;
import java.io.InputStream;
import com.edplan.nso.OsuFilePart;

public class StdMode implements Mode
{
	public static final int MODE_ID=0;
	
	@Override
	public int getModeId(){
		// TODO: Implement this method
		return MODE_ID;
	}
	
	@Override
	public OsuFilePart parseFilePart(){
		// TODO: Implement this method
		return null;
	}
}
