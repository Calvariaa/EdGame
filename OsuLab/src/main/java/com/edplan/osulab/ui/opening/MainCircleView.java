package com.edplan.osulab.ui.opening;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.ui.drawable.sprite.CircleSprite;
import com.edplan.framework.math.RectF;

public class MainCircleView extends EdView
{
	
	private CircleSprite ring;
	
	public MainCircleView(MContext c){
		super(c);
		ring=new CircleSprite(c);
	}

	float time=0;
	float dt=3000;
	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		super.onDraw(canvas);
		
		time+=getContext().getFrameDeltaTime();
		time%=dt;
		
		ring.setPosition(canvas.getWidth()/2,canvas.getHeight()/2);
		ring.setArea(RectF.ltrb(-canvas.getWidth()/2,-canvas.getHeight()/2,canvas.getWidth()/2,canvas.getHeight()/2));
		ring.setInnerRadius(0);//canvas.getWidth()/4);
		ring.setRadius(canvas.getWidth()/4*(1+time/dt));
		ring.draw(canvas);
	}
}
