package com.edplan.nso.resource;

public class ResourceInfo<T>
{
	private static Integer hashIndex;
	
	private final int hash;
	
	public String path;
	public T res;
	
	public ResourceInfo(String path){
		this.path=path;
		synchronized(hashIndex){
			this.hash=hashIndex;
			hashIndex=hashIndex+1;
		}
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
