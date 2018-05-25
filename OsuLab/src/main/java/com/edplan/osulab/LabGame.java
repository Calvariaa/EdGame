package com.edplan.osulab;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.ui.animation.callback.OnFinishListener;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.widget.FrameContainer;
import com.edplan.framework.ui.widget.RelativeLayout;
import com.edplan.framework.ui.widget.TestButton;
import com.edplan.framework.ui.widget.ViewPage;
import com.edplan.osulab.ui.OptionList;
import com.edplan.osulab.ui.SceneOverlay;
import com.edplan.osulab.ui.UiConfig;
import com.edplan.osulab.ui.opening.MainCircleView;
import com.edplan.osulab.ui.toolbar.LabToolbar;

/**
 *全局的管理类
 */
public class LabGame
{
	private static LabGame game;
	
	private LabToolbar toolBar;

	private OptionList optionList;

	private SceneOverlay sceneOverlay;

	public void setSceneOverlay(SceneOverlay sceneOverlay){
		this.sceneOverlay=sceneOverlay;
	}

	public SceneOverlay getSceneOverlay(){
		return sceneOverlay;
	}

	public void setToolBar(LabToolbar toolBar){
		this.toolBar=toolBar;
	}

	public LabToolbar getToolBar(){
		return toolBar;
	}

	public void setOptionList(OptionList optionList){
		this.optionList=optionList;
	}

	public OptionList getOptionList(){
		return optionList;
	}
	
	public EdView createContentView(MContext c){
		RelativeLayout mainLayout=new RelativeLayout(c);
		mainLayout.setBackground(Color4.gray(0.2f));
		{
			FrameContainer mainFrameContainer=new FrameContainer(c);
			RelativeLayout.RelativeParam mparam=new RelativeLayout.RelativeParam();
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
					dpTest.setOffsetY(200);
					EdLayoutParam llparam=new EdLayoutParam();
					llparam.width=Param.makeUpDP(100);
					llparam.height=Param.makeUpDP(40);
					page.addView(dpTest,llparam);
					dpTest.setOnClickListener(new EdView.OnClickListener(){
							@Override
							public void onClick(EdView view){
								// TODO: Implement this method
								toolBar.hide();
								view.post(new Runnable(){
										@Override
										public void run(){
											// TODO: Implement this method
											mainCircleView.startOpeningAnim(new OnFinishListener(){
													@Override
													public void onFinish(){
														// TODO: Implement this method
														toolBar.show();
													}
												});
										}
									},2000);
							}
						});
				}
			}
		}
		
		{
			optionList=new OptionList(c);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.makeUpDP(250);
			param.height=Param.MODE_MATCH_PARENT;
			param.marginTop=ViewConfiguration.dp(40);
			mainLayout.addView(optionList,param);
		}
		
		{
			toolBar=new LabToolbar(c);
			//toolBar.setVisiblility(EdView.VISIBILITY_GONE);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.makeUpDP(UiConfig.TOOLBAR_HEIGHT_DP);
			param.gravity=Gravity.TopCenter;
			mainLayout.addView(toolBar,param);
		}
		{
			sceneOverlay=new SceneOverlay(c);
			//toolBar.setVisiblility(EdView.VISIBILITY_GONE);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.MODE_MATCH_PARENT;
			param.marginTop=ViewConfiguration.dp(UiConfig.TOOLBAR_HEIGHT_DP);
			param.gravity=Gravity.BottomCenter;
			mainLayout.addView(sceneOverlay,param);
		}
		return mainLayout;
	}
	
	public static void createGame(){
		game=new LabGame();
	}

	public static LabGame get(){
		return game;
	}
}