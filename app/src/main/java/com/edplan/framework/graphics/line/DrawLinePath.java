package com.edplan.framework.graphics.line;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.graphics.opengl.drawui.GLDrawable;
import com.edplan.framework.graphics.opengl.wrapped.GLShader;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.FMath;

public class DrawLinePath extends GLDrawable
{
	public static final int MAXRES=24;
	
	private LinePath path;
	
	private GLShader fs;
	
	private GLShader vs;
	
	private GLTexture texture;
	
	private Vec3Buffer vertexBuffer;
	
	private Vec3Buffer texturePointBuffer;
	
	private Vec2 textureStart=new Vec2(0,0);
	
	private Vec2 textureEnd=new Vec2(1,0);
	
	private DrawInfo info;
	
	private void addLineCap(Vec2 org,float theta,float thetaDiff){
		final float step=FMath.Pi/MAXRES;
		
		float dir=Math.signum(thetaDiff);
		thetaDiff*=dir;
		
		int amountPoints=(int)Math.ceil(thetaDiff/step);
		
		if(dir<0)
			theta+=FMath.Pi;
		
		Vec2 current=Vec2.atCircle(theta).zoom(path.getWidth()).add(org);
		
		
		
	}
	
	public void updateBuffers(){
		
	}
	
	@Override
	public void render(DrawInfo info) {
		// TODO: Implement this method
		
	}

}
