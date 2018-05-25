package com.edplan.osulab.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.ui.animation.ComplexAnimation;
import com.edplan.framework.ui.animation.ComplexAnimationBuilder;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.framework.ui.animation.FloatQueryAnimation;
import com.edplan.framework.ui.animation.callback.OnFinishListener;
import com.edplan.framework.ui.drawable.ColorDrawable;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.osulab.ui.toolbar.LabToolbar;
import com.edplan.framework.ui.widget.TestButton;
import com.edplan.framework.ui.widget.RelativeLayout;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.widget.component.Hideable;

/**
 *
 */
public class SceneOverlay extends RelativeContainer implements Hideable
{
	public SceneOverlay(MContext c){
		super(c);
		ColorDrawable cd=new ColorDrawable(c);
		cd.setColor(Color4.rgba(0,0,0,0.8f),
					Color4.rgba(0,0,0,0.8f),
					Color4.rgba(0,0,0,0.5f),
					Color4.rgba(0,0,0,0.5f));
		setBackground(cd);
		
		{
			TestButton button=new TestButton(c);
			button.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(EdView view){
						// TODO: Implement this method
						if(getVisiblility()!=VISIBILITY_GONE){
							hide();
						}else{
							show();
						}
					}
				});
			RelativeLayout.RelativeParam p=new RelativeLayout.RelativeParam();
			p.width=Param.MODE_MATCH_PARENT;
			p.height=Param.makeUpDP(30);
			p.gravity=Gravity.TopCenter;
			addView(button,p);
		}
	}
	
	@Override
	public void hide(){
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(new FloatQueryAnimation<SceneOverlay>(this,"alpha")
																	  .transform(getAlpha(),0,Easing.None)
																	  .transform(0,ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.None));
		builder.together(new FloatQueryAnimation<SceneOverlay>(this,"offsetY")
						 .transform(getOffsetY(),0,Easing.None)
						 .transform(getHeight(),ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.InQuad));
		ComplexAnimation anim=builder.build();
		anim.setOnFinishListener(new OnFinishListener(){
				@Override
				public void onFinish(){
					// TODO: Implement this method
					setVisiblility(VISIBILITY_GONE);
					BackQuery.get().unregist(SceneOverlay.this);
				}
			});
		anim.start();
		setAnimation(anim);
	}

	@Override
	public void show(){
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(new FloatQueryAnimation<SceneOverlay>(this,"alpha")
																	  .transform(getAlpha(),0,Easing.None)
																	  .transform(1,ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.None));
		builder.together(new FloatQueryAnimation<SceneOverlay>(this,"offsetY")
						 .transform(getOffsetY(),0,Easing.None)
						 .transform(0,ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.OutQuad));
		ComplexAnimation anim=builder.build();
		anim.start();
		setAnimation(anim);
		setVisiblility(VISIBILITY_SHOW);
		BackQuery.get().regist(this);
	}
	
	@Override
	public boolean isHidden(){
		// TODO: Implement this method
		return getVisiblility()==VISIBILITY_GONE;
	}
}
