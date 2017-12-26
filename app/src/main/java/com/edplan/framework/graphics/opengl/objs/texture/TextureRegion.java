package com.edplan.framework.graphics.opengl.objs.texture;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;

public class TextureRegion
{
	private GLTexture texture;
	
	private RectF area;
	
	public TextureRegion(GLTexture t,RectF area){
		this.texture=t;
		this.area=area;
	}
	
	public Vec2 toTexturePosition(float x,float y) {
		return texture.toTexturePosition(area.getX1()+x,area.getY1()+y);
	}
	
	public GLTexture getTexture(){
		return texture;
	}
	
	public int getTextureId(){
		return getTexture().getTextureId();
	}
	
	public float getWidth(){
		return area.getWidth();
	}
	
	public float getHeight(){
		return area.getHeight();
	}
	
	
	
	
}
