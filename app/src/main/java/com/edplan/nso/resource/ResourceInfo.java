package com.edplan.nso.resource;

public class ResourceInfo<T>
{
	public String path;
	public T res;
	
	public ResourceInfo(String path){
		this.path=path;
	}

	public void setPath(String path) {
		this.path=path;
	}

	public String getPath() {
		return path;
	}

	public void setRes(T res) {
		this.res=res;
	}

	public T getRes() {
		return res;
	}
}
