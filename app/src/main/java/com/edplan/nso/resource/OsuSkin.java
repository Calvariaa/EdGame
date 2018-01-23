package com.edplan.nso.resource;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.resource.IResource;
import com.edplan.nso.resource.annotation.AResType;
import com.edplan.nso.resource.annotation.AResPath;
import java.lang.reflect.Field;
import java.io.IOException;
import android.util.Log;

public class OsuSkin
{
	@AResType(ResType.TEXTURE)
	@AResPath("hitcircle.png")
	public TextureInfo hitcircle;
	
	public OsuSkin(){
		initial();
	}
	
	public void initial(){
		try{
			Class klass=OsuSkin.class;
			Field[] fields=klass.getFields();
			for(Field f:fields){
				Log.v("load","check "+f);
				if(f.isAnnotationPresent(AResType.class)){
					Log.v("load","check ok. "+f);
					Log.v("load","parse annotation. "+f);
					AResType type=(AResType)f.getAnnotation(AResType.class);
					AResPath path=(AResPath)f.getAnnotation(AResPath.class);
					switch(type.value()){
						case TEXTURE:
							f.setAccessible(true);
							f.set(this,new TextureInfo(path.value()));
							Log.v("load","create info {"+path+"}. "+f);
							break;
						default:break;
					}
				}
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new OsuResException("reflect err illaccs: "+e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new OsuResException("reflect err illagr: "+e.getMessage(),e);
		}
	}
	

	public GLTexture getHitcircleTexture() {
		return hitcircle.getRes();
	}
	
	public void load(IResource res){
		try{
			Class klass=OsuSkin.class;
			Field[] fields=klass.getFields();
			for(Field f:fields){
				if(f.isAnnotationPresent(AResType.class)){
					AResType type=(AResType)f.getAnnotation(AResType.class);
					switch(type.value()){
						case TEXTURE:
							f.setAccessible(true);
							TextureInfo info=(TextureInfo)f.get(this);
							try {
								loadRes(info, res);
							} catch (IOException e) {
								e.printStackTrace();
								throw new OsuResException("res io err : "+e.getMessage(),e);
							}
							break;
						default:break;
					}
				}
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new OsuResException("reflect err illaccs: "+e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new OsuResException("reflect err illagr: "+e.getMessage(),e);
		}
	}
	
	public void loadRes(TextureInfo info,IResource res) throws IOException{
		info.setTexture(GLTexture.decodeResource(res,info.getPath()));
	}
}
