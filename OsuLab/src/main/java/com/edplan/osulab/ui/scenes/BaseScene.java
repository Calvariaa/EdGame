package com.edplan.osulab.ui.scenes;
import com.edplan.framework.ui.widget.component.Hideable;
import com.edplan.framework.ui.widget.ViewPage;
import com.edplan.framework.MContext;
import com.edplan.osulab.LabGame;

public abstract class BaseScene extends ViewPage implements Hideable
{
	public BaseScene(MContext c){
		super(c);
	}
	
	public abstract String getSceneName();

	@Override
	public boolean isHidden(){
		// TODO: Implement this method
		return LabGame.get().getScenes().getCurrentScene()==this;
	}
}
