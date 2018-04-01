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
import com.edplan.framework.graphics.opengl.objs.GLTexture;

public abstract class AResource
{
	
	public BufferedReader openBufferedReader(String path) throws IOException{
		InputStream in=openInput(path);
		if(in==null)return null;
		return new BufferedReader(new InputStreamReader(in));
	}
	
	public AResource subResource(String path){
		return new SubResource(this,path);
	}
	
	public GLTexture loadTexture(String path) throws IOException{
		return GLTexture.decodeResource(this,path);
	}
	
	public Bitmap loadBitmap(String path) throws IOException{
		InputStream in=openInput(path);
		if(in==null)return null;
		Bitmap bmp=BitmapFactory.decodeStream(in);
		in.close();
		return bmp;
	}
	
	public String loadText(String path) throws IOException{
		BufferedReader r=openBufferedReader(path);
		if(r==null)return null;
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
		System.out.println(in.available());
		if(in==null)return null;
		ByteBuffer buffer=ByteBuffer.allocateDirect(in.available());
		buffer.order(ByteOrder.nativeOrder());
		byte[] bu=new byte[in.available()];
		int l;
		while((l=in.read(bu))!=-1){
			buffer.put(bu,0,l);
		}
		in.close();
		return buffer;
	}
	
	public abstract String[] list(String dir) throws IOException;
	
	public abstract boolean contain(String file);
	
	//当path的res不存在的时候应该返回null
	public abstract InputStream openInput(String path) throws IOException;
}
