package com.edplan.framework.view;
import com.edplan.framework.MContext;

public abstract class MViewGroup extends BaseView
{
	public MViewGroup(MContext con){
		super(con);
	}
	
	public MViewGroup add(BaseView w){
		w.setParent(this);
		return this;
	}
	
	public MViewGroup add(BaseView... ws){
		for(BaseView w:ws){
			add(w);
		}
		return this;
	}
	
}
