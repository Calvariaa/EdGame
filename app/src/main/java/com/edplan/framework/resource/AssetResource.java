package com.edplan.framework.resource;
import java.io.InputStream;
import android.content.Context;
import android.content.res.Resources;
import java.io.IOException;
import android.content.res.AssetManager;
import android.util.Log;
import java.util.Arrays;

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

	@Override
	public String[] list(String dir) throws IOException
	{
		// TODO: Implement this method
		return res.list(dir);
	}
}
