package com.edplan.framework.ui.drawable.sprite;
import com.edplan.framework.MContext;

public class ColorRectSprite extends RectSprite<ColorSpriteShader>
{
	public ColorRectSprite(MContext c){
		super(c);
	}

	@Override
	protected ColorSpriteShader createShader(){
		// TODO: Implement this method
		return ColorSpriteShader.get();
	}
}

