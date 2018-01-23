package com.edplan.framework.resource;
import java.io.IOException;
import java.io.InputStream;

public class SubResource extends IResource
{
	private String rPath;
	
	private IResource rootRes;
	
	public SubResource(IResource res,String path){
		if(res instanceof SubResource){
			this.rootRes=((SubResource)res).getRootRes();
			this.rPath=((SubResource)res).getRPath()+"/"+path;
		}else{
			this.rootRes=res;
			this.rPath=path;
		}
	}

	public IResource getRootRes(){
		return rootRes;
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
		return rootRes.openInput(getRPath()+"/"+path);
	}
}
