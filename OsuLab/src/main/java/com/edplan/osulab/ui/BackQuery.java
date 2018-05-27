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
	
	public void onChange(){
		if(remind()==0){
			LabGame.get().getBackButton().hide();
		}else{
			if(LabGame.get().getBackButton().isHidden()){
				LabGame.get().getBackButton().show();
			}
		}
	}
	
	public void regist(Hideable obj){
		if(!query.contains(obj)){
			query.add(obj);
		}else{
			unregist(obj);
			query.add(obj);
		}
		onChange();
	}
	
	public void unregist(Hideable h){
		final int pre=remind();
		query.remove(h);
		if(pre!=remind()){
			onChange();
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
