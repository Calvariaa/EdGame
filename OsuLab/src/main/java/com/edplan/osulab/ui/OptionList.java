package com.edplan.osulab.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.RectF;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.ui.animation.ComplexAnimation;
import com.edplan.framework.ui.animation.ComplexAnimationBuilder;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.framework.ui.animation.FloatQueryAnimation;
import com.edplan.framework.ui.animation.callback.OnFinishListener;
import com.edplan.framework.ui.drawable.sprite.ColorRectSprite;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.MarginLayoutParam;
import com.edplan.framework.ui.layout.Orientation;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.widget.ScrollContainer;
import com.edplan.framework.ui.widget.TestButton;
import com.edplan.framework.ui.widget.component.Hideable;

public class OptionList extends ScrollContainer implements Hideable
{
	private ColorRectSprite shadowSprite;
	
	public OptionList(MContext c){
		super(c);
		setChildoffset(ViewConfiguration.dp(1));
		setBackground(Color4.rgba(0,0,0,0.7f));
		setOrientation(Orientation.DIRECTION_T2B);
		setPaddingLeft(ViewConfiguration.dp(2));
		setPaddingRight(ViewConfiguration.dp(2));
		setGravity(Gravity.CenterLeft);
		
		shadowSprite=new ColorRectSprite(c);
		shadowSprite.setColor(Color4.rgba(0,0,0,0.5f),
							  Color4.rgba(0,0,0,0f),
							  Color4.rgba(0,0,0,0.5f),
							  Color4.rgba(0,0,0,0f));
		
		
		{
			TestButton b=new TestButton(c);
			MarginLayoutParam param=new MarginLayoutParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.makeUpDP(30);
			b.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(EdView view){
						// TODO: Implement this method
						hide();
					}
				});
			addView(b,param);
		}
	}
	
	@Override
	public void hide(){
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(new FloatQueryAnimation<OptionList>(this,"alpha")
																	  .transform(getAlpha(),0,Easing.None)
																	  .transform(0,ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.None));
		builder.together(new FloatQueryAnimation<OptionList>(this,"offsetX")
						 .transform(getOffsetX(),0,Easing.None)
						 .transform(-getWidth(),ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.InQuad));
		ComplexAnimation anim=builder.build();
		anim.setOnFinishListener(new OnFinishListener(){
				@Override
				public void onFinish(){
					// TODO: Implement this method
					setVisiblility(VISIBILITY_GONE);
					BackQuery.get().unregist(OptionList.this);
				}
			});
		anim.start();
		setAnimation(anim);
	}

	@Override
	public void show(){
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(new FloatQueryAnimation<OptionList>(this,"alpha")
																	  .transform(getAlpha(),0,Easing.None)
																	  .transform(1,ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.None));
		builder.together(new FloatQueryAnimation<OptionList>(this,"offsetX")
						 .transform(getOffsetX(),0,Easing.None)
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

	@Override
	public void setAlpha(float alpha){
		// TODO: Implement this method
		super.setAlpha(alpha);
		shadowSprite.setAlpha(alpha);
	}

	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		super.onDraw(canvas);
		shadowSprite.setArea(RectF.xywh(canvas.getWidth(),0,ViewConfiguration.dp(5),canvas.getHeight()));
		shadowSprite.draw(canvas);
	}
}
