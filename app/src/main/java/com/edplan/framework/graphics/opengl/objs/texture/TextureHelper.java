package com.edplan.framework.graphics.opengl.objs.texture;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectI;

public class TextureHelper
{
	public static TextureRegion[][] split(GLTexture texture,int row,int cro){
		TextureRegion[][] rgs=new TextureRegion[row][cro];
		float deltaWidth=texture.getWidth()/row;
		float deltaHeight=texture.getHeight()/cro;
		for(int x=0;x<row;x++){
			for(int y=0;y<cro;y++){
				rgs[x][y]=new TextureRegion(texture,new RectI((int)deltaWidth*x,(int)deltaHeight*y,(int)deltaWidth,(int)deltaHeight));
			}
		}
		return rgs;
	}
}
