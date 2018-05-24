package com.edplan.osulab.ui.toolbar;
import com.edplan.framework.ui.widget.LinearLayout;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.Orientation;

public class LabToolbar extends LinearLayout
{
	public LabToolbar(MContext c){
		super(c);
		setOrientation(Orientation.DIRECTION_L2R);
	}
}
