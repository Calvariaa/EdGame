package com.edplan.nso.ruleset.std.playing.judgment;
import com.edplan.nso.ruleset.amodel.playing.Judgment;

public class StdJudgment extends Judgment
{
	public Level type;
	
	
	
	public enum Level{
		None,Miss,S100,S300
	}
}
