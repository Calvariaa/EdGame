package com.edplan.nso.resource;

public class ResourceInfo<T>
{
	private static int hashIndex;
	
	private final int hash;
	
	public String path;
	public T res;
	
	public ResourceInfo(String path){
		this.path=path;
		hash=hashIndex++;
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

	@Override
	public int hashCode()
	{
		// TODO: Implement this method
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO: Implement this method
		if(obj!=null&&obj instanceof ResourceInfo){
			return ((ResourceInfo)obj).hash==hash;
		}else{
			return false;
		}
	}
}
