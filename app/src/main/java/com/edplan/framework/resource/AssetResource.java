package com.edplan.framework.resource;
import java.io.InputStream;
import android.content.Context;
import android.content.res.Resources;
import java.io.IOException;
import android.content.res.AssetManager;
import android.util.Log;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.HashMap;
import java.util.Map;

public class AssetResource extends IResource
{
	private AssetManager res;
	
	private HashMap<String,AssetResInfo> resMap=new HashMap<String,AssetResInfo>();
	
	public AssetResource(AssetManager res){
		this.res=res;
		initial();
	}
	
	public AssetResource(Context con){
		this(con.getAssets());
	}
	
	private void initial(){
		try {
			addSubRes("");
			//Log.v("res-list",Arrays.toString(list("osu")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Map.Entry<String,AssetResInfo> e:resMap.entrySet()){
			Log.v("res-list",e.getValue().path+(e.getValue().isFile?"":"/"));
		}
		
	}
	
	/**
	 *返回这个path是否是文件
	 */
	private boolean addSubRes(String path) throws IOException{
		String[] l=list(path);
		String tmp;
		for(String s:l){
			tmp=path+((path.length()!=0)?"/":"")+s;
			resMap.put(tmp,new AssetResInfo(tmp,addSubRes(tmp)));
		}
		return l.length==0;
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

	@Override
	public boolean contain(String file) {
		// TODO: Implement this method
		return resMap.containsKey(file);
	}
	
	public class AssetResInfo{
		public final String path;
		public final boolean isFile;
		public AssetResInfo(String path,boolean isFile){
			this.path=path;
			this.isFile=isFile;
		}
	}
}
