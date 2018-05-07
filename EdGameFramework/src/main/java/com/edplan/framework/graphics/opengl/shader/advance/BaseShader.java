package com.edplan.framework.graphics.opengl.shader.advance;
import android.util.Log;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.VertexAttrib;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformColor4;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformFloat;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformMat2;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformMat4;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformSample2D;
import java.lang.reflect.Field;

public abstract class BaseShader extends GLProgram
{
	protected BaseShader(GLProgram program,boolean initial){
		super(program.getVertexShader(),program.getFragmentShader(),program.getProgramId());
		if(initial)loadPointer();
	}
	
	protected final void loadPointer(){
		try{
			Class klass=this.getClass();
			Field[] fields=klass.getFields();
			int sampleIndex=0;
			for(Field f:fields){
				if(f.isAnnotationPresent(PointerName.class)){
					String name=f.getAnnotation(PointerName.class).value();
					f.setAccessible(true);
					Log.v("load","cc "+UniformColor4.class);
					Log.v("load","dclass "+f.getType());
					if(f.getType().equals(VertexAttrib.class)){
						AttribType type=f.getAnnotation(AttribType.class);
						f.set(this,VertexAttrib.findAttrib(this,name,type.value()));
					}else if(f.getType().equals(UniformMat2.class)){
						f.set(this,UniformMat2.findUniform(this,name));
					}else if(f.getType().equals(UniformMat4.class)){
						f.set(this,UniformMat4.findUniform(this,name));
					}else if(f.getType().equals(UniformColor4.class)){
						f.set(this,UniformColor4.findUniform(this,name));
					}else if(f.getType().equals(UniformSample2D.class)){
						f.set(this,UniformSample2D.findUniform(this,name,sampleIndex));
						sampleIndex++;
					}else if(f.getType().equals(UniformFloat.class)){
						f.set(this,UniformFloat.findUniform(this,name));
					}
				}
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new GLException("reflect err illaccs: "+e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new GLException("reflect err illagr: "+e.getMessage(),e);
		}
	}
	
	public static @interface PointerName{
		public String value();
	}
	
	public static @interface AttribType{
		public VertexAttrib.Type value();
	}
}
