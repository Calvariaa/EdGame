package com.edplan.nso.filepart;
import com.edplan.nso.OsuFilePart;

public class PartEvents implements OsuFilePart
{
	public static final String TAG="Events";
	
	@Override
	public String getTag(){
		// TODO: Implement this method
		return TAG;
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		return "{@Events}";
	}
}
