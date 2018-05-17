package com.edplan.nso.storyboard.renderer;

import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.graphics.opengl.BlendType;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.framework.graphics.opengl.buffer.BufferUtil;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.its.IVec2;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class OsbRenderer
{
	public static final int MAX_VERTEX_COUNT=40000;

	public static final int MAX_INDICES_COUNT=30000;

	private OsbShader shader;

	private float[] anchorArray;

	private float[] textureCoordArray;
	
	private float[] positionXAry,positionYAry;
	private FloatBuffer positionXBuffer,positionYBuffer;

	private float[] color0Array,color1Array;

	private FloatBuffer anchorBuffer,textureCoordBuffer,color0Buffer,color1Buffer;

	private FastVertex[] vertexs;

	private int idx;

	private int[] indices;

	private IntBuffer indicesBuffer;

	private int idcx;

	private GLTexture texture;

	private BlendType blendType;

	private boolean isRendering=false;

	private BaseCanvas frameCanvas;

	public OsbRenderer(){
		shader=new OsbShader();
		ensureSize(32,32*3);
	}

	/**
	 *用来确保可以放置的顶点数和索引数，
	 *原来的数据可能被刷新！所以请在start()前就确保绘制的数量
	 */
	public void ensureSize(int vertexCount,int indiceCount){
		vertexCount=Math.min(MAX_VERTEX_COUNT,vertexCount);
		indiceCount=(indiceCount/3+1)*3;
		indiceCount=Math.min(MAX_INDICES_COUNT,indiceCount);
		if(vertexs==null||vertexs.length<vertexCount){
			if(vertexs!=null){
				final int preLength=vertexs.length;
				vertexs=Arrays.copyOf(vertexs,vertexCount);
				for(int i=preLength;i<vertexs.length;i++){
					vertexs[i]=new FastVertex(i);
				}
			}else{
				vertexs=new FastVertex[vertexCount];
				for(int i=0;i<vertexs.length;i++){
					vertexs[i]=new FastVertex(i);
				}
			}
			anchorArray=new float[Vec2.FLOATS*vertexCount];
			textureCoordArray=new float[Vec2.FLOATS*vertexCount];
			color0Array=new float[Color4.FLOATS*vertexCount];
			color1Array=new float[Color4.FLOATS*vertexCount];

			anchorBuffer=BufferUtil.createFloatBuffer(anchorArray.length);
			textureCoordBuffer=BufferUtil.createFloatBuffer(textureCoordArray.length);
			color0Buffer=BufferUtil.createFloatBuffer(color0Array.length);
			color1Buffer=BufferUtil.createFloatBuffer(color1Array.length);
		}
		if(indices==null||indices.length<indiceCount){
			indices=new int[indiceCount];
			indicesBuffer=BufferUtil.createIntBuffer(indices.length);
		}
	}

	public void addIndices(short... ind){
		if(idcx+ind.length>=indices.length)
			flush();
		for(int i=0;i<ind.length;i++){
			indices[idcx]=ind[i];
			idcx++;
		}
	}

	public void getNextVertexs(FastVertex[] ary){
		if(idx+ary.length>vertexs.length){
			flush();
		}
		for(int i=0;i<ary.length;i++){
			ary[i]=vertexs[idx++];
		}
	}

	public void setCurrentTexture(AbstractTexture t){
		if(t.getTexture()!=texture){
			flush();
			texture=t.getTexture();
		}
	}

	public void setBlendType(BlendType blendType){
		if(this.blendType!=blendType){
			flush();
			this.blendType=blendType;
			frameCanvas.getBlendSetting().setBlendType(blendType);
		}
	}

	public BlendType getBlendType(){
		return blendType;
	}

	public void resetIdxData(){
		idx=0;
		idcx=0;
		anchorBuffer.clear();
		textureCoordBuffer.clear();
		color0Buffer.clear();
		color1Buffer.clear();
		indicesBuffer.clear();
	}

	public void start(BaseCanvas canvas){
		if(isRendering)
			throw new RuntimeException("A LegacyRenderer can't call start when rendering");
		isRendering=true;
		frameCanvas=canvas;
		blendType=frameCanvas.getBlendSetting().getBlendType();
		resetIdxData();
	}

	public void flush(){
		if(texture==null||idcx==0)return;
		//vao.bind();
		shader.useThis();
		shader.bindTexture(texture);
		shader.loadCamera(frameCanvas.getCamera());

		updateBuffer(anchorArray,idx,anchorBuffer,2);
		updateBuffer(textureCoordArray,idx,textureCoordBuffer,2);
		updateBuffer(color0Array,idx,color0Buffer,4);
		
		indicesBuffer.position(0);
		indicesBuffer.put(indices,0,idcx);
		indicesBuffer.position(0);
		GLWrapped.drawElements(GLWrapped.GL_TRIANGLES,idcx,GLES20.GL_UNSIGNED_SHORT,indicesBuffer);
		resetIdxData();
	}
	
	public static void updateBuffer(float[] ary,int indx,FloatBuffer bf,int floatCount){
		bf.position(0);
		bf.put(ary,0,indx*floatCount);
		bf.position(0);
	}

	public void end(){
		if(!isRendering)
			throw new RuntimeException("A LegacyRenderer can't call start when rendering");
		flush();
		isRendering=false;
	}

	public class FastVertex
	{
		public static final int FLOAT_COUNT=9;

		public final short index;

		//定义了材质坐标
		protected final int u,v;

		protected final int anchorX,anchorY;

		public final TextureCoordPointer TextureCoord;

		public FastVertex(int index){
			this.index=(short)index;
			int offset;

			TextureCoord=new TextureCoordPointer();

			offset=index*Vec2.FLOATS;
			this.anchorX=offset++;
			this.anchorY=offset++;

			offset=index*Vec2.FLOATS;
			this.u=offset++;
			this.v=offset++;
		}

		public class TextureCoordPointer implements IVec2
		{
			@Override
			public void set(Vec2 vc){
				textureCoordArray[u]=vc.x;
				textureCoordArray[v]=vc.y;
			}

			@Override
			public void set(float xv,float yv){
				// TODO: Implement this method
				textureCoordArray[u]=xv;
				textureCoordArray[v]=yv;
			}

			@Override
			public float getX(){
				// TODO: Implement this method
				return textureCoordArray[u];
			}

			@Override
			public float getY(){
				// TODO: Implement this method
				return textureCoordArray[v];
			}

			@Override
			public void setX(float v){
				// TODO: Implement this method
				textureCoordArray[u]=v;
			}

			@Override
			public void setY(float vl){
				// TODO: Implement this method
				textureCoordArray[v]=vl;
			}
		}
	}
}
