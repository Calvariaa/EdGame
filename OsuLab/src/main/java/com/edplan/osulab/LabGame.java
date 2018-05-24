package com.edplan.osulab;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.drawable.RectDrawable;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.widget.AbsoluteLayout;
import com.edplan.framework.ui.widget.FrameContainer;
import com.edplan.framework.ui.widget.RelativeLayout;
import com.edplan.framework.ui.widget.TestButton;
import com.edplan.framework.ui.widget.ViewPage;
import com.edplan.osulab.ui.opening.MainCircleView;
import com.edplan.osulab.ui.toolbar.LabToolbar;

/**
 *全局的管理类
 */
public class LabGame
{
	private static LabGame game;
	
	private LabToolbar toolBar;
	
	public static void createGame(){
		game=new LabGame();
	}
	
	public static LabGame get(){
		return game;
	}
	
	public EdView createContentView(MContext c){
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
			final MainCircleView mainCircleView;
			{
				RelativeLayout llayout=new RelativeLayout(c);
				//llayout.setChildoffset(50);
				llayout.setGravity(Gravity.Center);
				//llayout.setOrientation(Orientation.DIRECTION_T2B);
				EdLayoutParam llparam=new EdLayoutParam();
				llparam.width=Param.MODE_MATCH_PARENT;
				llparam.height=Param.MODE_MATCH_PARENT;
				page.addView(llayout,llparam);
				{
					mainCircleView=new MainCircleView(c);
					RelativeLayout.RelativeParam lllparam=new RelativeLayout.RelativeParam();
					lllparam.width=Param.makeupScaleOfParentOtherParam(0.6f);
					lllparam.height=Param.makeupScaleOfParentParam(0.6f);
					lllparam.gravity=Gravity.Center;
					llayout.addView(mainCircleView,lllparam);
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
				dpTest.setOnClickListener(new EdView.OnClickListener(){
						@Override
						public void onClick(EdView view){
							// TODO: Implement this method
							view.post(new Runnable(){
									@Override
									public void run(){
										// TODO: Implement this method
										mainCircleView.startOpeningAnim();
									}
								},1000);
						}
					});
			}
		}
		return mainLayout;
	}
}
