package com.edplan.nso.ruleset.mania.objects;
import com.edplan.nso.ruleset.amodel.object.HitObject;
import com.edplan.nso.ruleset.mania.objects.ManiaHolder;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.std.objects.StdHitObjects;

public class ManiaHitObjects extends StdHitObjects
{
	@Override
	public void addHitObject(HitObject t){
		// TODO: Implement this method
		if(t instanceof StdHitObject){
			switch(((StdHitObject)t).getResType()){
				case Circle:
					super.addHitObject(ManiaNote.toManiaNote((StdHitObject)t));
					return;
				case ManiaHolder:
					super.addHitObject(t);
					return;
				default:
					return;
			}
		}
	}
}
