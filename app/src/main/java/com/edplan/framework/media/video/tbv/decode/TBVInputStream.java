package com.edplan.framework.media.video.tbv.decode;
import java.io.DataInputStream;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.graphics.opengl.BlendType;

public class TBVInputStream
{
	private DataInputStream in;
	
	public int readInt() throws IOException{
		return in.readInt();
	}
	
	public long readLong() throws IOException{
		return in.readLong();
	}
	
	public short readShort() throws IOException{
		return in.readShort();
	}
	
	public byte[] readByteArray(int length) throws IOException{
		byte[] buffer=new byte[length];
		in.readFully(buffer);
		return buffer;
	}
	
	public boolean readBoolean() throws IOException{
		return in.readBoolean();
	}
	
	public float readFloat() throws IOException{
		return in.readFloat();
	}
	
	public double readDouble() throws IOException{
		return in.readDouble();
	}
	
	public Color4 readARGB255() throws IOException{
		return Color4.argb255(readInt());
	}
	
	public TextureVertex3D readTextureVertex3D() throws IOException{
		float x=readFloat();
		float y=readFloat();
		float z=readFloat();
		Color4 color=readARGB255();
		float tx=readFloat();
		float ty=readFloat();
		return new TextureVertex3D(tx,ty,x,y,z,color);
	}
	
	public BlendType readBlendType() throws IOException{
		int i=readInt();
		return (i<0||i>=BlendTypes.length)?BlendType.Normal:BlendTypes[i];
	}
	
	public String readString() throws IOException{
		int byteLength=readInt();
		byte[] bytes=readByteArray(byteLength);
		return new String(bytes,"UTF-8");
	}
	
	public JSONObject readJSONObject() throws JSONException, IOException{
		return new JSONObject(readString());
	}
	
	public static BlendType[] BlendTypes=new BlendType[]{
		BlendType.Normal,
		BlendType.Additive,
		BlendType.Delete
	};
	
	
}
