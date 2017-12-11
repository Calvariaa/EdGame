package com.edplan.framework.resource;
import java.io.InputStream;
import android.content.Context;
import android.content.res.Resources;
import java.io.IOException;
import android.content.res.AssetManager;

public class AssetResource extends IResource
{
	private AssetManager res;
	
	public AssetResource(AssetManager res){
		this.res=res;
	}
	
	public AssetResource(Context con){
		this(con.getAssets());
	}
	
	@Override
	public InputStream openInput(String path) throws IOException {
		// TODO: Implement this method
		return res.open(path);
	}
}
