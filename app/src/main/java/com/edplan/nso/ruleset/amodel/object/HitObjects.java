package com.edplan.nso.ruleset.amodel.object;

import java.util.List;

public abstract class HitObjects<T extends HitObject>
{
	public abstract List<T> getHitObjectList();
	
	public abstract void addHitObject(HitObject t);
}
