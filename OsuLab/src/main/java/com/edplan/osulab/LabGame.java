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
import com.edplan.osulab.ui.pieces.JumpingCircle;
import com.edplan.osulab.ui.MainBackground;
import com.edplan.osulab.ui.pieces.BackButton;
import com.edplan.osulab.ui.MessageList;

/**
 *全局的管理类
 */
public class LabGame
{
	private static LabGame game;
	
	private LabToolbar toolBar;

	private OptionList optionList;
	
	private MessageList messageList;

	private SceneOverlay sceneOverlay;
	
	private JumpingCircle jumpingCircle;
	
	private MainBackground mainBackground;
	
	private FrameContainer mainFrameContainer;
	
	private BackButton backButton;

	public void setMessageList(MessageList messageList){
		this.messageList=messageList;
	}

	public MessageList getMessageList(){
		return messageList;
	}

	public void setBackButton(BackButton backButton){
		this.backButton=backButton;
	}

	public BackButton getBackButton(){
		return backButton;
	}

	public void setMainBackground(MainBackground mainBackground){
		this.mainBackground=mainBackground;
	}

	public MainBackground getMainBackground(){
		return mainBackground;
	}

	public void setJumpingCircle(JumpingCircle jumpingCircle){
		this.jumpingCircle=jumpingCircle;
	}

	public JumpingCircle getJumpingCircle(){
		return jumpingCircle;
	}

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
		jumpingCircle=new JumpingCircle(c);
		mainBackground=new MainBackground(c);
		mainFrameContainer=new FrameContainer(c);
		backButton=new BackButton(c);
		{
			RelativeLayout.RelativeParam mparam=new RelativeLayout.RelativeParam();
			mparam.width=Param.MODE_MATCH_PARENT;
			mparam.height=Param.MODE_MATCH_PARENT;
			mainLayout.addView(mainBackground,mparam);
		}
		{
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
					llayout.setGravity(Gravity.Center);
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
					{
						RelativeLayout.RelativeParam lllparam=new RelativeLayout.RelativeParam();
						lllparam.width=Param.makeupScaleOfParentOtherParam(0.6f);
						lllparam.height=Param.makeupScaleOfParentParam(0.6f);
						lllparam.gravity=Gravity.Center;
						llayout.addView(jumpingCircle,lllparam);
					}
				}
			}
		}
		
		{
			toolBar=new LabToolbar(c);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.makeUpDP(UiConfig.TOOLBAR_HEIGHT_DP);
			param.gravity=Gravity.TopCenter;
			mainLayout.addView(toolBar,param);
		}
		
		{
			optionList=new OptionList(c);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.makeUpDP(350);
			param.height=Param.MODE_MATCH_PARENT;
			param.marginTop=ViewConfiguration.dp(40);
			mainLayout.addView(optionList,param);
		}
		
		{
			messageList=new MessageList(c);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.makeUpDP(350);
			param.height=Param.MODE_MATCH_PARENT;
			param.marginTop=ViewConfiguration.dp(40);
			param.gravity=Gravity.BottomRight;
			mainLayout.addView(messageList,param);
		}
		
		{
			sceneOverlay=new SceneOverlay(c);
			RelativeLayout.RelativeParam param=new RelativeLayout.RelativeParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.MODE_MATCH_PARENT;
			param.marginTop=ViewConfiguration.dp(UiConfig.TOOLBAR_HEIGHT_DP);
			param.gravity=Gravity.BottomCenter;
			mainLayout.addView(sceneOverlay,param);
		}
		{
			RelativeLayout.RelativeParam mparam=new RelativeLayout.RelativeParam();
			mparam.width=Param.makeUpDP(50);
			mparam.height=Param.makeUpDP(50);
			mparam.gravity=Gravity.BottomLeft;
			mainLayout.addView(backButton,mparam);
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
