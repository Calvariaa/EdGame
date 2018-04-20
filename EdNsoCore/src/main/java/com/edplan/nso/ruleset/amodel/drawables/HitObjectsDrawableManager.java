package com.edplan.nso.ruleset.amodel.drawables;

import com.edplan.nso.ruleset.amodel.object.HitObject;
import com.edplan.framework.view.MDrawable;
import com.edplan.superutils.interfaces.TimeBasedObject;

public abstract class HitObjectsDrawableManager<T extends HitObject> implements MDrawable,TimeBasedObject
{
	public abstract void addHitObject(T t);
	
	
}
