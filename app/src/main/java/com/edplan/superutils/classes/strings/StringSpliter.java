package com.edplan.superutils.classes.strings;
import com.edplan.superutils.classes.strings.StringSpliter.ErrIndexException;

public class StringSpliter
{
	public String res;
	public String[] sp;
	public int i1;
	
	public StringSpliter(String res,String sps){
		this.res=res;
		sp=res.split(sps);
		reset();
	}
	
	public int length(){
		return sp.length;
	}
	
	public String getNow(){
		if(hasNext()){
			return sp[i1];
		}else{
			return null;
		}
	}
	
	public String next(){
		if(hasNext()){
			String r=sp[i1];
			i1++;
			if(i1==sp.length){
				i1=-1;
			}
			return r;
		}else{
			return null;
		}
	}
	
	public boolean hasNext(){
		return i1!=-1;
	}
	
	public void reset(){
		i1=0;
	}
	
	public void setRes(String res){
		this.res=res;
	}

	public String getRes(){
		return res;
	}
	
	public class ErrIndexException extends Exception{
		public ErrIndexException(String msg){
			super(msg);
		}
	}
	
}
