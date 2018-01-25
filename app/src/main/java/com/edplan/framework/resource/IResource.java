package com.edplan.framework.resource;
import android.graphics.Bitmap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.graphics.BitmapFactory;
import java.nio.ByteBuffer;
import java.io.BufferedInputStream;
import java.nio.ByteOrder;

public abstract class IResource
{
	
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
	
	public ByteBuffer loadBuffer(String path) throws IOException{
		InputStream in=openInput(path);
		ByteBuffer buffer=ByteBuffer.allocateDirect(in.available());
		buffer.order(ByteOrder.nativeOrder());
		byte[] bu=new byte[1024*16];
		int l;
		while((l=in.read(bu))!=-1){
			buffer.put(bu,0,l);
		}
		in.close();
		return buffer;
	}
	
	public abstract String[] list(String dir) throws IOException;
	
	public abstract boolean contain(String file);
	
	public abstract InputStream openInput(String path) throws IOException;
}
