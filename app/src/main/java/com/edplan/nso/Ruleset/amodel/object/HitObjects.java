package com.edplan.nso.Ruleset.amodel.object;

import java.util.List;

public abstract class HitObjects<T extends HitObject>{
	
	public abstract List<T> getHitObjectList();
	
	public abstract void addHitObject(HitObject t);
	
}
