package com.edplan.osulab.ui;
import java.util.ArrayList;
import com.edplan.framework.ui.widget.component.Hideable;
import com.edplan.osulab.LabGame;

public class BackQuery
{
	private static BackQuery instance=new BackQuery();
	
	private ArrayList<Hideable> query=new ArrayList<Hideable>();
	
	public static BackQuery get(){
		return instance;
	}
	
	public void onBecomeEmpty(){
		LabGame.get().getBackButton().hide();
	}
	
	public void regist(Hideable obj){
		if(remind()==0){
			LabGame.get().getBackButton().show();
		}
		if(!query.contains(obj)){
			query.add(obj);
		}else{
			unregist(obj);
			query.add(obj);
		}
	}
	
	public void unregist(Hideable h){
		final int pre=remind();
		query.remove(h);
		if(pre!=0){
			if(remind()==0){
				onBecomeEmpty();
			}
		}
	}
	
	public boolean back(){
		for(int i=query.size()-1;i>=0;i--){
			final Hideable h=query.get(i);
			unregist(h);
			if(h!=null&&!h.isHidden()){
				h.hide();
				return true;
			}
		}
		return false;
	}
	
	public int remind(){
		return query.size();
	}
}
