package com.edplan.simpleGame.view;
import com.edplan.simpleGame.MContext;

public abstract class MViewGroup extends BaseWidget
{
	public MViewGroup(MContext con){
		super(con);
	}
	
	public MViewGroup add(BaseWidget w){
		w.setParent(this);
		return this;
	}
	
	public MViewGroup add(BaseWidget... ws){
		for(BaseWidget w:ws){
			add(w);
		}
		return this;
	}
	
}
