package com.edplan.nso.Ruleset.std.objects;
import com.edplan.nso.Ruleset.amodel.object.HitObject;
import com.edplan.nso.Ruleset.amodel.object.HitObjects;
import java.util.ArrayList;
import java.util.List;

public class StdHitObjects extends HitObjects
{
	private List<HitObject> hitObjects;
	
	public StdHitObjects(){
		hitObjects=new ArrayList<HitObject>();
	}

	@Override
	public void addHitObject(HitObject t){
		// TODO: Implement this method
		if(t instanceof StdHitObject){
			hitObjects.add((StdHitObject)t);
		}
	}

	@Override
	public List<HitObject> getHitObjectList(){
		// TODO: Implement this method
		return hitObjects;
	}
}
