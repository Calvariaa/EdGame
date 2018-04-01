package com.edplan.framework.graphics.opengl;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.main.GameContext;
import com.edplan.framework.MContext;
import java.io.IOException;
import com.edplan.framework.resource.AResource;
import com.edplan.framework.graphics.opengl.shader.advance.RectTextureShader;
import com.edplan.framework.graphics.opengl.shader.advance.RoundedRectTextureShader;
import com.edplan.framework.graphics.opengl.shader.advance.ColorShader;
import com.edplan.framework.graphics.opengl.objs.Vertex3D;
import com.edplan.framework.graphics.opengl.batch.BaseColorBatch;
import com.edplan.framework.graphics.opengl.batch.RectVertexBatch;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;

public class ShaderManager
{
	public static class PATH{
		private static final String PATH_Texture3DShader="StdTexture3DShader";
		private static final String PATH_RectShader_VS="StdRectShader.vs";
		private static final String PATH_RectShader_FS="StdRectShader.fs";
		private static final String PATH_RoundedRectShader_FS="StdRoundedRectShader.fs";
		private static final String PATH_ColorShader="StdColorShader";
	}
	
	private static ShaderManager shaderManager;
	
	private Texture3DShader<Texture3DBatch> texture3DShader;
	
	private RectTextureShader<RectVertexBatch> rectShader;
	
	private RoundedRectTextureShader<RectVertexBatch> roundedRectShader;
	
	private ColorShader<BaseColorBatch> colorShader;
	
	private AResource res;
	
	public ShaderManager(AResource _res){
		init(_res);
	}
	
	public void init(AResource _res){
		res=_res;
		loadShader();
	}
	
	private void loadShader(){
		try {
			texture3DShader=
				Texture3DShader.createT3S(
					res.loadText(PATH.PATH_Texture3DShader + ".vs"),
					res.loadText(PATH.PATH_Texture3DShader + ".fs"),
					Texture3DBatch.class);
			rectShader=
				RectTextureShader.createRTS(
					res.loadText(PATH.PATH_RectShader_VS),
					res.loadText(PATH.PATH_RectShader_FS),
					RectVertexBatch.class);
			roundedRectShader=
				RoundedRectTextureShader.createRRTS(
					res.loadText(PATH.PATH_RectShader_VS),
					res.loadText(PATH.PATH_RoundedRectShader_FS),
					RectVertexBatch.class);
			colorShader=
				ColorShader.createCS(
					res.loadText(PATH.PATH_ColorShader + ".vs"),
					res.loadText(PATH.PATH_ColorShader + ".fs"),
					BaseColorBatch.class);
		}catch(IOException e){
			e.printStackTrace();
			throw new GLException("err load shader from asset! msg: "+e.getMessage(),e);
		}
	}
	
	public void setColorShader(ColorShader<BaseColorBatch> colorShader) {
		this.colorShader=colorShader;
	}

	public ColorShader<BaseColorBatch> getColorShader() {
		return colorShader;
	}
	
	public void setRectShader(RectTextureShader<RectVertexBatch> rectShader) {
		this.rectShader=rectShader;
	}

	public RectTextureShader<RectVertexBatch> getRectShader() {
		return rectShader;
	}

	public void setRoundedRectShader(RoundedRectTextureShader<RectVertexBatch> roundedRectShader) {
		this.roundedRectShader=roundedRectShader;
	}

	public RoundedRectTextureShader<RectVertexBatch> getRoundedRectShader() {
		return roundedRectShader;
	}
	
	public Texture3DShader<Texture3DBatch> getTexture3DShader(){
		return texture3DShader;
	}
	
	public static void initStatic(MContext context){
		shaderManager=new ShaderManager(context.getAssetResource().subResource("shaders"));
	}
	
	public static ShaderManager get(){
		return shaderManager;
	}
}
