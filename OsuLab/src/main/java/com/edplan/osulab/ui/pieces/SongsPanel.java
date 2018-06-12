package com.edplan.osulab.ui.pieces;
import com.edplan.framework.ui.additions.popupview.PopupView;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.widget.TextureView;
import com.edplan.framework.ui.widget.RelativeLayout;
import com.edplan.framework.ui.layout.Param;

public class SongsPanel extends PopupView
{
	private TextureView background;
	
	public SongsPanel(MContext c){
		super(c);
		setBackground(Color4.Black);
		setRounded(ViewConfiguration.dp(5))
		 .setShadow(
			ViewConfiguration.dp(5),
			Color4.rgba(1,1,1,0.7f),
			Color4.rgba(0,0,0,0));
		{
			background=new TextureView(c);
			RelativeLayout.RelativeParam p=new RelativeLayout.RelativeParam();
			p.width=Param.MODE_MATCH_PARENT;
			p.height=Param.MODE_MATCH_PARENT;
			
		}
	}
}
