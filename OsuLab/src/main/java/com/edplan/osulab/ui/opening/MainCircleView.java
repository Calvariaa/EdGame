package com.edplan.osulab.ui.opening;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.ui.drawable.sprite.CircleSprite;
import com.edplan.framework.math.RectF;
import com.edplan.framework.ui.animation.FloatQueryAnimation;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.framework.interfaces.FloatInvokeSetter;
import com.edplan.framework.ui.animation.ComplexAnimationBuilder;
import com.edplan.framework.ui.animation.ComplexAnimation;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.FMath;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.drawable.sprite.TextureCircleSprite;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import java.io.IOException;

public class MainCircleView extends EdView
{
	private float PieceSize=0.4f;
	
	private CircleSprite ring;
	private CircleSprite pinkCover;
	private TextureCircleSprite centerRing;
	private CircleSprite p1,p2,p3,p4;
	private GLTexture logo;
	
	public MainCircleView(MContext c){
		super(c);
		ring=new CircleSprite(c);
		centerRing=new TextureCircleSprite(c);
		pinkCover=new CircleSprite(c);
		p1=new CircleSprite(c);
		p2=new CircleSprite(c);
		p3=new CircleSprite(c);
		p4=new CircleSprite(c);
		try{
			logo=getContext().getAssetResource().loadTexture("osu/ui/logo.png");
			centerRing.setTexture(logo,null);
		}catch(IOException e){
			e.printStackTrace();
			getContext().toast("err");
		}
	}
	
	public void setPieceOriginOffset(float length){
		Vec2 pos=new Vec2(getWidth()/2,getHeight()/2);
		Vec2 org=pos.copy();
		pos.move(length,0);
		final float progress=length/(getWidth()/2);
		pos.rotate(org,FMath.Pi*progress);
		p1.setPosition(pos.x,pos.y);
		pos.rotate(org,FMath.PiHalf);
		p2.setPosition(pos.x,pos.y);
		pos.rotate(org,FMath.PiHalf);
		p3.setPosition(pos.x,pos.y);
		pos.rotate(org,FMath.PiHalf);
		p4.setPosition(pos.x,pos.y);
		float alpha=FMath.linearCut(
			progress,
			0,0.95f,1,
			0,1,0);
		p1.setAlpha(alpha);
		p2.setAlpha(alpha);
		p3.setAlpha(alpha);
		p4.setAlpha(alpha);
		pinkCover.setAlpha(Math.min(1,2*progress));
		final float radius=getWidth()/2*PieceSize*FMath.linearCut(
			progress,
			0,0.7f,1,
			0,1,0);
		p1.setRadius(radius);
		p2.setRadius(radius);
		p3.setRadius(radius);
		p4.setRadius(radius);
	}
	
	public void setInner(float w){
		centerRing.setRadius(w);
		pinkCover.setRadius(w);
		ring.setInnerRadius(w);
	}

	public void startOpeningAnim(){
		ring.resetRadius();
		pinkCover.resetRadius();
		centerRing.resetRadius();
		p1.resetRadius();
		p2.resetRadius();
		p3.resetRadius();
		p4.resetRadius();
		
		ring.setPosition(getWidth()/2,getHeight()/2);
		ring.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		pinkCover.setPosition(getWidth()/2,getHeight()/2);
		pinkCover.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		centerRing.setPosition(getWidth()/2,getHeight()/2);
		centerRing.setArea(RectF.ltrb(-getWidth()/2,-getHeight()/2,getWidth()/2,getHeight()/2));
		pinkCover.setAccentColor(Color4.rgb255(253,123,181));
		pinkCover.setAlpha(1);
		float unit=getWidth()/2*PieceSize;
		p1.setArea(RectF.ltrb(-unit,-unit,unit,unit));
		p1.setAccentColor(Color4.rgb255(255,125,183));
		p2.setArea(RectF.ltrb(-unit,-unit,unit,unit));
		p2.setAccentColor(Color4.rgb255(0,192,254));
		p3.setArea(RectF.ltrb(-unit,-unit,unit,unit));
		p3.setAccentColor(Color4.rgb255(252,243,106));
		p4.setArea(RectF.ltrb(-unit,-unit,unit,unit));
		p4.setAccentColor(Color4.rgb255(255,255,255));

		float radius=getWidth()/2;
		FloatQueryAnimation anim=new FloatQueryAnimation<CircleSprite>(ring,"radius");
		anim.transform(0,0,Easing.None);
		anim.transform(radius,500,Easing.OutQuad);
		ComplexAnimationBuilder builder=ComplexAnimationBuilder.start(anim);
		{
			FloatQueryAnimation anim2=new FloatQueryAnimation<MainCircleView>(MainCircleView.this,"inner");
			anim2.transform(0,200,Easing.None);
			anim2.transform(radius*0.9f,300,Easing.OutQuad);
			builder.together(anim2,0);
		}
		{
			FloatQueryAnimation piecesAnim=new FloatQueryAnimation<MainCircleView>(MainCircleView.this,"pieceOriginOffset");
			piecesAnim.transform(0,0,Easing.None);
			piecesAnim.transform(radius,600,Easing.OutExpo);
			builder.together(piecesAnim);
		}

		ComplexAnimation camin=builder.build();
		camin.start();
		setAnimation(camin);
	}
	
	@Override
	public void onInitialLayouted(){
		// TODO: Implement this method
		super.onInitialLayouted();
		
		post(new Runnable(){
				@Override
				public void run(){
					// TODO: Implement this method
					startOpeningAnim();
					//getContext().toast("开始动画");
				}
			},1000);
	}

	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		super.onDraw(canvas);
		p1.draw(canvas);
		p2.draw(canvas);
		p3.draw(canvas);
		p4.draw(canvas);
		
		//centerRing.draw(canvas);
		pinkCover.draw(canvas);
		ring.draw(canvas);
	}
}
