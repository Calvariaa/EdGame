package com.edplan.osulab.ui;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.drawable.ColorDrawable;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class MainBackground extends RelativeContainer
{
	public MainBackground(MContext c){
		super(c);
		ColorDrawable cd=new ColorDrawable(c);
		float t=0.1f,b=0.25f;
		cd.setColor(Color4.gray(t),Color4.gray(t),
					Color4.gray(b),Color4.gray(b));
		setBackground(cd);
	}
	
}
