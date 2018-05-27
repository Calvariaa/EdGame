package com.edplan.fontawesome;
import com.edplan.framework.ui.widget.ScrollContainer;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.Orientation;
import com.edplan.framework.ui.text.font.FontAwesome;
import com.edplan.framework.ui.layout.MarginLayoutParam;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.layout.Gravity;

public class AwesomeList extends ScrollContainer
{
	public AwesomeList(MContext c){
		super(c);
		setOrientation(Orientation.DIRECTION_T2B);
		setGravity(Gravity.TopLeft);
		for(int i=0;i<500;i++){
			AwesomeView view=new AwesomeView(c);
			view.setFontAwesome(i,i+1);
			MarginLayoutParam p=new MarginLayoutParam();
			p.width=Param.MODE_MATCH_PARENT;
			p.height=Param.makeUpDP(20);
			addView(view,p);
		}
	}
	
}
