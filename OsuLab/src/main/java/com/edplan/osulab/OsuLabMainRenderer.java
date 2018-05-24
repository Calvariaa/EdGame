package com.edplan.osulab;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.drawable.RectDrawable;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.Orientation;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.widget.AbsoluteLayout;
import com.edplan.framework.ui.widget.FrameContainer;
import com.edplan.framework.ui.widget.LinearLayout;
import com.edplan.framework.ui.widget.TestButton;
import com.edplan.framework.ui.widget.TestScroller;
import com.edplan.framework.ui.widget.ViewPage;
import com.edplan.osulab.ui.opening.MainCircleView;

public class OsuLabMainRenderer extends MainRenderer
{
	public OsuLabMainRenderer(MContext c,MainApplication app){
		super(c,app);
	}
	
	@Override
	public EdView createContentView(MContext c){
		// TODO: Implement this method
		RectDrawable draw=new RectDrawable(c);
		draw.setColor(Color4.rgba(1,1,1,0.6f));
		
		AbsoluteLayout mainLayout=new AbsoluteLayout(c);
		
		FrameContainer mainFrameContainer=new FrameContainer(c);
		EdLayoutParam mparam=new EdLayoutParam();
		mparam.width=Param.MODE_MATCH_PARENT;
		mparam.height=Param.MODE_MATCH_PARENT;
		mainLayout.addView(mainFrameContainer,mparam);
		{
			ViewPage page=new ViewPage(c);
			EdLayoutParam pparam=new EdLayoutParam();
			pparam.width=Param.MODE_MATCH_PARENT;
			pparam.height=Param.MODE_MATCH_PARENT;
			page.setLayoutParam(pparam);
			mainFrameContainer.addPage(page);
			mainFrameContainer.swapPage(page);
			{
				LinearLayout llayout=new LinearLayout(c);
				llayout.setChildoffset(50);
				llayout.setGravity(Gravity.Center);
				llayout.setOrientation(Orientation.DIRECTION_T2B);
				EdLayoutParam llparam=new EdLayoutParam();
				llparam.width=Param.MODE_MATCH_PARENT;
				llparam.height=Param.MODE_MATCH_PARENT;
				page.addView(llayout,llparam);
				{
					MainCircleView button=new MainCircleView(c);
					EdLayoutParam lllparam=new EdLayoutParam();
					lllparam.width=Param.makeupScaleOfParentOtherParam(0.6f);
					lllparam.height=Param.makeupScaleOfParentParam(0.6f);
					llayout.addView(button,lllparam);
				}
			}
			{
				TestButton dpTest=new TestButton(c);
				dpTest.setOffsetX(100);
				dpTest.setOffsetY(100);
				EdLayoutParam llparam=new EdLayoutParam();
				llparam.width=Param.makeUpDP(100);
				llparam.height=Param.makeUpDP(100);
				page.addView(dpTest,llparam);
			}
		}
		
		return mainLayout;
	}
}
