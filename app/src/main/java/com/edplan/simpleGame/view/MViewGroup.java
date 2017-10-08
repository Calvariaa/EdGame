package com.edplan.simpleGame.view;

public abstract class MViewGroup extends BaseWidget
{
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
