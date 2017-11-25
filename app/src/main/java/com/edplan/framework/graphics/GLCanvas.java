package com.edplan.framework.graphics;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.batch.Texture3DBatch;

public class GLCanvas
{
	private BufferedLayer layer;
	
	public GLCanvas(BufferedLayer layer){
		this.layer=layer;
	}
	
	public void setLayer(BufferedLayer layer){
		this.layer=layer;
	}
	
	public BufferedLayer getLayer(){
		return layer;
	}
	
	public void drawTexture3DBatch(Texture3DBatch batch){
		
	}
	
}
