package com.edplan.nso.ruleset;
import com.edplan.nso.ruleset.amodel.playing.PlayField;

public abstract class Ruleset
{
	/**
	 *每个Ruleset独有的id
	 */
	public abstract int getRulesetId();
	
	public abstract PlayField createPlayField();
	
	
	
}
