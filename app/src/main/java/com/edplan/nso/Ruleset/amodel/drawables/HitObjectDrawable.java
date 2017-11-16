package com.edplan.nso.Ruleset.amodel.drawables;
import com.edplan.framework.view.MDrawable;
import com.edplan.nso.Ruleset.amodel.object.HitObject;
import com.edplan.superutils.interfaces.TimeBasedObject;

public abstract class HitObjectDrawable<T extends HitObject> implements MDrawable,TimeBasedObject
{
	public abstract HitObjectDrawable setHitObject(T t);
	
	public abstract int getShowTime();
	
	public abstract boolean isVisible();
	
	public abstract boolean ifEnd();
}
