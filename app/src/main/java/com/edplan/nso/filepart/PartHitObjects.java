package com.edplan.nso.filepart;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.Ruleset.amodel.parser.HitObjectParser;
import com.edplan.nso.Ruleset.amodel.object.HitObjects;
import com.edplan.nso.Ruleset.ModeManager;
import com.edplan.nso.NsoException;
import com.edplan.nso.Ruleset.std.objects.StdHitObjects;
import com.edplan.nso.Ruleset.amodel.object.HitObject;
import com.edplan.nso.Ruleset.amodel.parser.HitObjectReparser;
import com.edplan.nso.Ruleset.std.parser.StdHitObjectReparser;
import android.util.Log;
import com.edplan.superutils.U;
import com.edplan.nso.Ruleset.mania.objects.ManiaHitObjects;
import java.util.List;

public class PartHitObjects implements OsuFilePart
{
	public static final String TAG="HitObjects";
	
	public int mode;
	
	public HitObjects hitObjects;
	
	public void initial(int mode) throws NsoException{
		this.mode=mode;
		switch(mode){
			case ModeManager.MODE_STD:
				hitObjects=new StdHitObjects();
				break;
			case ModeManager.MODE_MANIA:
				hitObjects=new ManiaHitObjects();
				break;
			default:
				throw new NsoException("invalid mode : "+mode);
		}
	}
	
	public void addHitObject(HitObject obj){
		hitObjects.addHitObject(obj);
	}
	
	public List<HitObject> getHitObjectList(){
		return hitObjects.getHitObjectList();
	}
	
	
	@Override
	public String getTag(){
		// TODO: Implement this method
		return TAG;
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		HitObjectReparser rp;
		switch(mode){
			case ModeManager.MODE_STD:
				rp=new StdHitObjectReparser();
				break;
			default:
				rp=new StdHitObjectReparser();
				break;
		}
		StringBuilder sb=new StringBuilder();
		try{
			for(HitObject t:hitObjects.getHitObjectList()){
				sb.append(rp.reparse(t)).append(U.NEXT_LINE);
			}
		}catch(NsoException e){
			Log.e("reparse","err build hitObject",e);
		}
		return sb.toString();
	}

}
