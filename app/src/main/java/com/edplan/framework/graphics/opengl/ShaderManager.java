package com.edplan.framework.graphics.opengl;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.main.GameContext;
import com.edplan.framework.MContext;
import java.io.IOException;
import com.edplan.framework.resource.IResource;

public class ShaderManager
{
	private static final String PATH_Texture3DShader="StdTexture3DShader";
	
	private static ShaderManager shaderManager;
	
	private Texture3DShader texture3DShader;
	
	private IResource res;
	
	public ShaderManager(IResource _res){
		init(_res);
	}
	
	public void init(IResource _res){
		res=_res;
		loadShader();
	}
	
	private void loadShader(){
		try {
			texture3DShader=
				Texture3DShader.create(
					res.loadText(PATH_Texture3DShader + ".vs"),
					res.loadText(PATH_Texture3DShader + ".fs"));
		}catch(IOException e){
			throw new GLException("err load shader from asset!",e);
		}
	}
	
	public Texture3DShader getStdTexture3DShader(){
		return texture3DShader;
	}
	
	public static void initStatic(MContext context){
		shaderManager=new ShaderManager(context.getAssetResource().subResource("shaders"));
	}
	
	public static ShaderManager get(){
		return shaderManager;
	}
}
