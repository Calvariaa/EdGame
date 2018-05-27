package com.edplan.osulab.ui.pieces;
import com.edplan.framework.ui.widget.RelativeLayout;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.osulab.ui.UiConfig;
import com.edplan.framework.ui.drawable.sprite.RoundedRectSprite;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.math.RectF;
import com.edplan.framework.graphics.opengl.BaseCanvas;

public class LabButton extends RelativeContainer
{
	private Color4 accentColor=UiConfig.Color.BLUE.copyNew();
	
	private RoundedRectSprite roundedRect;
	
	private float scale=1;
	
	public LabButton(MContext c){
		super(c);
		setClickable(true);
		setBackground(accentColor);
		roundedRect=new RoundedRectSprite(c);
	}

	public void setScale(float scale){
		this.scale=scale;
		roundedRect.setScale(scale);
	}

	public float getScale(){
		return scale;
	}
	
	public void setRoundedRadius(float r){
		roundedRect.setRoundedRadius(r);
	}
	
	public float getRoundedRadius(){
		return roundedRect.getRoundedRadius();
	}

	@Override
	protected void postLayer(BaseCanvas canvas,BufferedLayer layer,RectF area,GLPaint paint){
		// TODO: Implement this method
		roundedRect.setPosition(canvas.getWidth()/2f,canvas.getHeight()/2f);
		roundedRect.setArea(RectF.ltrb(-canvas.getWidth()/2f,-canvas.getHeight()/2f,canvas.getWidth()/2,canvas.getHeight()/2));
		roundedRect.setRect(RectF.ltrb(-canvas.getWidth()/2f,-canvas.getHeight()/2f,canvas.getWidth()/2,canvas.getHeight()/2));
		roundedRect.setAccentColor(paint.getMixColor());
		roundedRect.setTexture(layer.getTexture());
		roundedRect.draw(canvas);
	}
}
