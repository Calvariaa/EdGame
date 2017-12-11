package com.edplan.framework.resource.advance;
import com.edplan.framework.resource.AssetResource;
import android.content.res.AssetManager;
import com.edplan.framework.resource.IResource;
import com.edplan.framework.resource.DefR;

public class ApplicationAssetResource extends AssetResource
{
	private IResource shaderResource;
	
	private IResource textureResource;
	
	public ApplicationAssetResource(AssetManager m){
		super(m);
		shaderResource=this.subResource(DefR.shaders);
		textureResource=this.subResource(DefR.textures);
	}
}
