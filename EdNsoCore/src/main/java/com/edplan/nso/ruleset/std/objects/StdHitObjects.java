package com.edplan.nso.ruleset.std.objects;
import com.edplan.nso.ruleset.amodel.object.HitObject;
import com.edplan.nso.ruleset.amodel.object.HitObjects;
import java.util.ArrayList;
import java.util.List;

public class StdHitObjects<T extends StdHitObject> extends HitObjects<T>
{
	private List<T> hitObjects;
	
	public StdHitObjects(){
		hitObjects=new ArrayList<T>();
	}
	
	public int size(){
		return hitObjects.size();
	}
	
	public StdHitObject get(int i){
		return hitObjects.get(i);
	}

	@Override
	public void addHitObject(HitObject t){
		// TODO: Implement this method
		if(t instanceof StdHitObject){
			hitObjects.add((T)t);
		}
	}

	@Override
	public List<T> getHitObjectList(){
		// TODO: Implement this method
		return hitObjects;
	}
}
