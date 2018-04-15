package com.edplan.framework.media.video.tbv.element;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.media.video.tbv.decode.TBVInputStream;
import java.io.IOException;
import com.edplan.framework.media.video.tbv.encode.TBVOutputStream;
import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.batch.BaseBatch;
import com.edplan.framework.graphics.opengl.batch.base.IHasPosition;
import com.edplan.framework.graphics.opengl.batch.base.IHasColor;
import com.edplan.framework.graphics.opengl.batch.base.IHasTexturePosition;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.Vec3;
import com.edplan.framework.graphics.opengl.objs.Color4;
import java.util.List;

public class DataDrawBaseTexture extends BaseBatch implements IHasPosition,IHasColor,IHasTexturePosition
{
	public int textureId;
	public TextureVertex3D[] vertexs;
	public int length;
	
	public Vec2Buffer texturePositionBuffer;
	public Vec3Buffer positionBuffer;
	public Color4Buffer colorBuffer;
	
	
	public DataDrawBaseTexture(int buffersize){
		vertexs=new TextureVertex3D[buffersize];
		Vec2[] tl=new Vec2[vertexs.length];
		Vec3[] pl=new Vec3[vertexs.length];
		Color4[] cl=new Color4[vertexs.length];
		TextureVertex3D v;
		for(int i=0;i<vertexs.length;i++){
			v=new TextureVertex3D();
			vertexs[i]=v;
			tl[i]=v.texturePoint;
			pl[i]=v.position;
			cl[i]=v.color;
		}
		texturePositionBuffer=new Vec2Buffer(tl);
		positionBuffer=new Vec3Buffer(pl);
		colorBuffer=new Color4Buffer(cl);
	}

	@Override
	public int getVertexCount() {
		// TODO: Implement this method
		return length;
	}
	
	@Override
	public Vec3Buffer makePositionBuffer() {
		// TODO: Implement this method
		positionBuffer.limit(true,0,length);
		return positionBuffer;
	}

	@Override
	public Color4Buffer makeColorBuffer() {
		// TODO: Implement this method
		colorBuffer.limit(true,0,length);
		return colorBuffer;
	}

	@Override
	public Vec2Buffer makeTexturePositionBuffer() {
		// TODO: Implement this method
		texturePositionBuffer.limit(true,0,length);
		return texturePositionBuffer;
	}

	public static DataDrawBaseTexture read(TBVInputStream in,DataDrawBaseTexture d) throws IOException{
		int id=in.readInt();
		int length=in.readInt();
		if(d==null){
			d=new DataDrawBaseTexture(length+20);
		}else if(d.vertexs.length<length){
			d=new DataDrawBaseTexture((int)((length+1)*1.5));
		}
		d.textureId=id;
		d.length=length;
		for(int i=0;i<length;i++){
			in.readTextureVertex3D(d.vertexs[i]);
		}
		return d;
	}
	
	public static DataDrawBaseTexture load(int id,List<TextureVertex3D> l,DataDrawBaseTexture d){
		if(d==null){
			d=new DataDrawBaseTexture(l.size()+20);
		}else if(d.vertexs.length<l.size()){
			d=new DataDrawBaseTexture((int)((l.size()+1)*1.5));
		}
		d.textureId=id;
		d.length=l.size();
		for(int i=0;i<d.length;i++){
			d.vertexs[i].set(l.get(i));
		}
		return d;
	}
	
	public static void write(TBVOutputStream out,DataDrawBaseTexture d) throws IOException{
		out.writeInt(d.textureId);
		out.writeInt(d.length);
		for(int i=0;i<d.length;i++){
			out.writeTextureVertex3D(d.vertexs[i]);
		}
	}
	
}
