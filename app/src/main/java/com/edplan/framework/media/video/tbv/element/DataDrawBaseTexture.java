package com.edplan.framework.media.video.tbv.element;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.media.video.tbv.decode.TBVInputStream;
import java.io.IOException;

public class DataDrawBaseTexture
{
	public TextureVertex3D[] vertexs;
	public int length;
	
	public DataDrawBaseTexture(int buffersize){
		vertexs=new TextureVertex3D[buffersize];
		for(int i=0;i<vertexs.length;i++){
			vertexs[i]=new TextureVertex3D();
		}
	}

	public static DataDrawBaseTexture read(TBVInputStream in,DataDrawBaseTexture d) throws IOException{
		int length=in.readInt();
		if(d==null){
			d=new DataDrawBaseTexture(length+20);
		}else if(d.vertexs.length<length){
			d=new DataDrawBaseTexture((int)((length+1)*1.5));
		}
		d.length=length;
		for(int i=0;i<length;i++){
			in.readTextureVertex3D(d.vertexs[i]);
		}
		return d;
	}
	
}
