package com.edplan.fontawesome;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.text.font.bmfont.BMFont;

public class AwesomeRenderer extends MainRenderer
{
	public AwesomeRenderer(MContext context,MainApplication app){
		super(context,app);
	}

	@Override
	public EdView createContentView(MContext c){
		// TODO: Implement this method
		BMFont conb=BMFont.getFont(BMFont.Noto_Sans_CJK_JP_Medium);
		conb.addFont(c.getAssetResource().subResource("font"),"FontAwesome.fnt");
		return new AwesomeList(c);
	}

}
