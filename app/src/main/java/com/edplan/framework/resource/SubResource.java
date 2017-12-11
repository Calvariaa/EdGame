package com.edplan.framework.resource;
import java.io.IOException;
import java.io.InputStream;

public class SubResource extends IResource
{
	private String rPath;
	
	private IResource res;
	
	public SubResource(IResource res,String path){
		this.res=res;
		if(res instanceof SubResource){
			this.rPath=((SubResource)res).getRPath()+"/"+path;
		}else{
			this.rPath=path;
		}
	}

	public void setRPath(String rPath) {
		this.rPath=rPath;
	}

	public String getRPath() {
		return rPath;
	}

	@Override
	public InputStream openInput(String path) throws IOException {
		// TODO: Implement this method
		return res.openInput(getRPath()+"/"+path);
	}
}
