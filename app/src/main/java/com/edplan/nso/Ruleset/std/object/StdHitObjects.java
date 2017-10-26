package com.edplan.nso.Ruleset.std.object;
import com.edplan.nso.Ruleset.amodel.object.HitObjects;
import java.util.List;
import java.util.ArrayList;

public class StdHitObjects extends HitObjects<StdHitObject>
{
	private List<StdHitObject> hitObjects;
	
	public StdHitObjects(){
		hitObjects=new ArrayList<StdHitObject>();
	}

	@Override
	public void addHitObject(StdHitObject t){
		// TODO: Implement this method
		hitObjects.add(t);
	}

	@Override
	public List<StdHitObject> getHitObjectList(){
		// TODO: Implement this method
		return hitObjects;
	}
}
