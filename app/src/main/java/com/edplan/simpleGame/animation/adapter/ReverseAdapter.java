package com.edplan.simpleGame.animation.adapter;
import com.edplan.simpleGame.animation.AnimaAdapter;

public class ReverseAdapter implements AnimaAdapter
{
	public AnimaAdapter adapter;
	
	public ReverseAdapter(AnimaAdapter _adapter){
		adapter=_adapter;
	}
	
	public AnimaAdapter getRes(){
		return adapter;
	}
	
	public static AnimaAdapter create(AnimaAdapter a){
		if(a instanceof ReverseAdapter){
			return ((ReverseAdapter)a).getRes();
		}else{
			return new ReverseAdapter(a);
		}
	}

	@Override
	public void setProgress(float p){
		// TODO: Implement this method
		adapter.setProgress(1-p);
	}
}
