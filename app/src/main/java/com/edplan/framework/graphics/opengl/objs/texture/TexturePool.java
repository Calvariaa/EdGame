package com.edplan.framework.graphics.opengl.objs.texture;
import java.util.HashMap;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;

public class TexturePool
{
	private HashMap<String,AbstractTexture> pool=new HashMap<String,AbstractTexture>();
	
	private TextureLoader loader;
	
	public TexturePool(TextureLoader loader){
		this.loader=loader;
	}
	
	public AbstractTexture getTexture(String msg){
		if(pool.containsKey(msg)){
			return pool.get(msg);
		}else{
			AbstractTexture t=loader.load(msg);
			pool.put(msg,t);
			return t;
		}
	}
	
	//全部清除，但是不会直接释放纹理
	public void clear(){
		pool.clear();
	}
	
	public interface TextureLoader{
		public AbstractTexture load(String msg);
	}
}
