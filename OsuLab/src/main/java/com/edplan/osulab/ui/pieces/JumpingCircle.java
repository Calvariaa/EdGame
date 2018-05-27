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
import com.edplan.osulab.LabGame;

public class JumpingCircle extends EdView
{
	private float maxShadowWidth=12;
	
	private float shadowWidth=maxShadowWidth;
	
	private CircleSprite ring;
	private CircleSprite pinkCover;
	private ShadowCircleSprite shadow,shadowInner;
	private TextureCircleSprite centerRing;
	
	private GLTexture logo;
	
	private BoundOverlay boundOverlay;
	private BaseBoundOverlay initialBound;
	
	private boolean performingOpeningAnim=true;
	
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

	@Override
	public void onInitialLayouted(){
		// TODO: Implement this method
		super.onInitialLayouted();
		BaseBoundOverlay b=new BaseBoundOverlay();
		initialBound=b;
		b.setLeft(getLeft());
		b.setTop(getTop());
		b.setRight(getRight());
		b.setBottom(getBottom());
		boundOverlay=b;
	}
	
	private void performBoundOverlayChangeAnim(float pl,float pt,float pr,float pb,final BoundOverlay next){
		BaseBoundOverlay tmp=new BaseBoundOverlay();
		tmp.setLeft(pl);
		tmp.setTop(pt);
		tmp.setRight(pr);
		tmp.setBottom(pb);
		boundOverlay=tmp;
		
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(new FloatQueryAnimation<BoundOverlay>(tmp,"left")
															 .transform(pl,0,Easing.None)
															 .transform(next.getLeft(),ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.OutQuad));
		builder.together(new FloatQueryAnimation<BoundOverlay>(tmp,"top")
						 .transform(pt,0,Easing.None)
						 .transform(next.getTop(),ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.OutQuad));
		builder.together(new FloatQueryAnimation<BoundOverlay>(tmp,"right")
						 .transform(pr,0,Easing.None)
						 .transform(next.getRight(),ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.OutQuad));													 
		builder.together(new FloatQueryAnimation<BoundOverlay>(tmp,"bottom")
						 .transform(pb,0,Easing.None)
						 .transform(next.getBottom(),ViewConfiguration.DEFAULT_TRANSITION_TIME,Easing.OutQuad));
		ComplexAnimation anim=builder.build();
		anim.start();
		anim.setOnFinishListener(new OnFinishListener(){
				@Override
				public void onFinish(){
					// TODO: Implement this method
					boundOverlay=next;
				}
			});
		setAnimation(anim);
	}

	public void setBoundOverlay(BoundOverlay boundOverlay){
		if(boundOverlay==null)boundOverlay=initialBound;
		performBoundOverlayChangeAnim(getLeft(),getTop(),getRight(),getBottom(),boundOverlay);
	}

	public BoundOverlay getBoundOverlay(){
		return boundOverlay;
	}

	@Override
	public float getLeft(){
		// TODO: Implement this method
		if(boundOverlay!=null){
			return boundOverlay.getLeft()+getOffsetX();
		}
		return super.getLeft();
	}

	@Override
	public float getTop(){
		// TODO: Implement this method
		if(boundOverlay!=null){
			return boundOverlay.getTop()+getOffsetY();
		}
		return super.getTop();
	}

	@Override
	public float getRight(){
		// TODO: Implement this method
		if(boundOverlay!=null){
			return boundOverlay.getRight()+getOffsetX();
		}
		return super.getRight();
	}

	@Override
	public float getBottom(){
		// TODO: Implement this method
		if(boundOverlay!=null){
			return boundOverlay.getBottom()+getOffsetY();
		}
		return super.getBottom();
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
	
	public void startOpeningAnimation(final OnFinishListener l){
		setClickable(false);
		performingOpeningAnim=true;
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
		camin.setOnFinishListener(new OnFinishListener(){
				@Override
				public void onFinish(){
					// TODO: Implement this method
					performingOpeningAnim=false;
					setClickable(true);
					if(l!=null)l.onFinish();
				}
			});
		camin.start();
		setAnimation(camin);
	}

	@Override
	public void onClickEvent(){
		// TODO: Implement this method
		super.onClickEvent();
		
		if(LabGame.get().getSceneSelectButtonBar().isHidden()){
			LabGame.get().getSceneSelectButtonBar().show();
		}else{
			LabGame.get().getSceneSelectButtonBar().update();
		}
		
	}

	double glowDuration=800;
	public static double shadowClock;
	public static float glowProgress;
	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		super.onDraw(canvas);
		
		if(!performingOpeningAnim){
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
			final float w=radius*0.9f;
			centerRing.setRadius(w);
			pinkCover.setRadius(w);
			ring.setInnerRadius(w);
			shadowInner.setRadius(w);
			shadowInner.setInnerRadius(w-ViewConfiguration.dp(5));
			ring.setRadius(radius);
			shadow.setInnerRadius(radius-2);
		}
		
		
		shadowClock+=getContext().getFrameDeltaTime();
		shadowClock%=glowDuration*2;
		glowProgress=(float)Math.sqrt(Math.abs(shadowClock/glowDuration-1));
		float p=glowProgress;
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
