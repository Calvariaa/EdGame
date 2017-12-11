package com.edplan.framework.resource;
import android.graphics.Bitmap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.graphics.BitmapFactory;



public abstract class IResource
{
	
	public abstract InputStream openInput(String path) throws IOException;
	
	public BufferedReader openBufferedReader(String path) throws IOException{
		return new BufferedReader(new InputStreamReader(openInput(path)));
	}
	
	public IResource subResource(String path){
		return new SubResource(this,path);
	}
	
	public Bitmap loadBitmap(String path) throws IOException{
		InputStream in=openInput(path);
		Bitmap bmp=BitmapFactory.decodeStream(in);
		in.close();
		return bmp;
	}
	
	public String loadText(String path) throws IOException{
		BufferedReader r=openBufferedReader(path);
		StringBuilder sb=new StringBuilder();
		String s;
		while((s=r.readLine())!=null){
			sb.append(s);
		}
		r.close();
		return sb.toString();
	}
}
