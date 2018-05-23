package com.edplan.framework.ui.drawable.sprite;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;

public abstract class Sprite<S extends SpriteShader> extends AbstractSprite
{
	protected S shader;
	
	private float alpha=1;
	
	private Color4 accentColor=Color4.ONE.copyNew();
	
	public Sprite(MContext c){
		super(c);
		shader=createShader();
	}

	public void setAlpha(float alpha){
		this.alpha=alpha;
	}

	public float getAlpha(){
		return alpha;
	}

	public void setAccentColor(Color4 accentColor){
		this.accentColor.set(accentColor);
		this.accentColor.toPremultipledThis();
	}

	public Color4 getAccentColor(){
		return accentColor;
	}

	protected abstract S createShader(); 
	
	@Override
	protected void startDraw(BaseCanvas canvas){
		// TODO: Implement this method
		shader.useThis();
	}

	@Override
	protected void prepareShader(BaseCanvas canvas){
		// TODO: Implement this method
		shader.loadAccentColor(accentColor);
		shader.loadAlpha(alpha);
		shader.loadCamera(canvas.getCamera());
	}

	@Override
	protected void endDraw(BaseCanvas canvas){
		// TODO: Implement this method
	}
}
