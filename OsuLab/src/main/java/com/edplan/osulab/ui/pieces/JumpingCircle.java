package com.edplan.osulab.ui.pieces;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectF;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.animation.ComplexAnimation;
import com.edplan.framework.ui.animation.ComplexAnimationBuilder;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.framework.ui.animation.FloatQueryAnimation;
import com.edplan.framework.ui.drawable.sprite.CircleSprite;
import com.edplan.framework.ui.drawable.sprite.TextureCircleSprite;
import com.edplan.osulab.ui.opening.MainCircleView;
import java.io.IOException;
import com.edplan.framework.ui.animation.callback.OnFinishListener;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.osulab.ui.UiConfig;
import com.edplan.framework.ui.drawable.sprite.ShadowCircleSprite;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.graphics.opengl.BlendType;
import com.edplan.framework.math.Vec2;

public class JumpingCircle extends EdView
{
	private float maxShadowWidth=9;
	
	private float shadowWidth;
	
	private CircleSprite ring;
	private CircleSprite pinkCover;
	private ShadowCircleSprite shadow,shadowInner;
	private TextureCircleSprite centerRing;
	
	private GLTexture logo;
	
	public JumpingCircle(MContext c){
		super(c);
		ring=new CircleSprite(c);
		centerRing=new TextureCircleSprite(c);
		
		shadow=new ShadowCircleSprite(c);
		float gr=0.8f;
		shadow.setStartColor(Color4.rgba(gr,gr,gr,0.7f));
		shadow.setEndColor(Color4.rgba(gr,gr,gr,0));
		shadow.setBlendType(BlendType.Additive);
		
		shadowInner=new ShadowCircleSprite(c);
		shadowInner.setEndColor(Color4.rgba(1,1,1,0.3f));
		shadowInner.setStartColor(Color4.rgba(1,1,1,0));
		shadowInner.setInner();
		shadowInner.setBlendType(BlendType.Additive);
		
		pinkCover=new CircleSprite(c);
		pinkCover.setColor(Color4.rgba(1,1,1,1f),
						   Color4.rgba(1,1,1,1f),
						   Color4.gray(0.9f),
						   Color4.gray(0.9f));
		try{
			logo=getContext().getAssetResource().loadTexture("osu/ui/logo.png");
			centerRing.setTexture(logo,null);
		}catch(IOException e){
			e.printStackTrace();
			getContext().toast("err");
		}
	}
	
	public void setInner(float w){
		centerRing.setRadius(w);
		pinkCover.setRadius(w);
		ring.setInnerRadius(w);
		shadowInner.setRadius(w);
		shadowInner.setInnerRadius(w-ViewConfiguration.dp(5));
	}
	
	public void setRadius(float r){
		ring.setRadius(r);
		shadow.setInnerRadius(r-2);
	}
	
	public void startOpeningAnimation(OnFinishListener l){
		ring.resetRadius();
		pinkCover.resetRadius();
		centerRing.resetRadius();
		shadow.resetRadius();
		shadowInner.resetRadius();
		
		final float radius=getWidth()/2;
		
		ring.setPosition(getWidth()/2,getHeight()/2);
		ring.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		pinkCover.setPosition(getWidth()/2,getHeight()/2);
		pinkCover.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		centerRing.setPosition(getWidth()/2,getHeight()/2);
		centerRing.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		
		float shadowWidthPx=ViewConfiguration.dp(maxShadowWidth);
		shadow.setPosition(getWidth()/2,getHeight()/2);
		shadow.setArea(RectF.ltrb(-getWidth()/2-shadowWidthPx,-getHeight()/2-shadowWidthPx,getWidth()/2+shadowWidthPx,getHeight()/2+shadowWidthPx));
		
		shadowInner.setPosition(getWidth()/2,getHeight()/2);
		shadowInner.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		
		pinkCover.setAccentColor(UiConfig.Color.PINK);
		pinkCover.setAlpha(1);
		
		FloatQueryAnimation anim=new FloatQueryAnimation<JumpingCircle>(this,"radius");
		anim.transform(0,0,Easing.None);
		anim.transform(radius,500,Easing.OutQuad);
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(anim);
		{
			FloatQueryAnimation anim2=new FloatQueryAnimation<JumpingCircle>(JumpingCircle.this,"inner");
			anim2.transform(0,200,Easing.None);
			anim2.transform(radius*0.9f,300,Easing.OutQuad);
			builder.together(anim2,0);
		}

		ComplexAnimation camin=builder.build();
		camin.setOnFinishListener(l);
		camin.start();
		setAnimation(camin);
	}

	double glowDuration=800;
	double shadowClock;
	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		super.onDraw(canvas);
		shadowClock+=getContext().getFrameDeltaTime();
		shadowClock%=glowDuration*2;
		float p=(float)Math.sqrt(Math.abs(shadowClock/glowDuration-1));
		shadowWidth=p*maxShadowWidth;
		shadow.setRadius(shadow.getInnerRadius()+ViewConfiguration.dp(shadowWidth));
		shadow.draw(canvas);
		pinkCover.draw(canvas);
		shadowInner.draw(canvas);
		ring.draw(canvas);
	}

	@Override
	public boolean inViewBound(float x,float y){
		// TODO: Implement this method
		return Vec2.length(x-(getLeft()+getRight())/2,y-(getTop()+getBottom())/2)<Math.min(getWidth(),getHeight())/2;
	}
}
