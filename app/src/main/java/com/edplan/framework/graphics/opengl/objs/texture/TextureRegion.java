package com.edplan.framework.graphics.opengl.objs.texture;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;

public class TextureRegion extends AbstractTexture
{
	private GLTexture texture;
	
	private RectF area;
	
	public TextureRegion(GLTexture t,RectF area){
		this.texture=t;
		this.area=area;
	}

	public void setArea(RectF _area) {
		this.area.set(_area);
	}

	public RectF getArea() {
		return area;
	}
	
	@Override
	public Vec2 toTexturePosition(float x,float y) {
		return texture.toTexturePosition(area.getX1()+x,area.getY1()+y);
	}
	
	@Override
	public GLTexture getTexture(){
		return texture;
	}
	
	@Override
	public int getTextureId(){
		return getTexture().getTextureId();
	}
	
	@Override
	public int getWidth(){
		return (int)area.getWidth();
	}
	
	@Override
	public int getHeight(){
		return (int)area.getHeight();
	}
	
}
