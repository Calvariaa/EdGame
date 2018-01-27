package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.MContext;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.utils.MLog;
import com.edplan.framework.graphics.opengl.objs.Vertex3D;

public class FollowpointPiece extends BasePiece
{
	private GLTexture followpoint;
	
	private Vec2 p1=new Vec2();
	
	private Vec2 p2=new Vec2();
	
	private Vec2 offCircleP1;
	
	private Vec2 offCircleP2;
	
	private boolean needDraw=true;
	
	private float progress=0;
	
	private Color4 color1=Color4.rgba(1,1,1,1);
	
	private Color4 color2=Color4.rgba(1,1,1,0);
	
	private float baseWidth=4;
	
	public FollowpointPiece(MContext c,PreciseTimeline t,Vec2 p1,Vec2 p2){
		super(c,t);
		this.p1.set(p1);
		this.p2.set(p2);
		paint.setStrokeWidth(baseWidth);
		paint.setColorMixRate(1);
	}

	public void calOffCirclePoints(){
		float dis=Vec2.length(p1,p2);
		if(dis<=getBaseSize()*2){
			needDraw=false;
		}else{
			offCircleP1=Vec2.onLineLength(p1,p2,getBaseSize());
			offCircleP2=Vec2.onLineLength(p2,p1,getBaseSize());
		}
	}

	public void setProgress(float progress) {
		this.progress=progress;
		calOffCirclePoints();
	}

	public float getProgress() {
		return progress;
	}

	public void setColor1(Color4 color1) {
		this.color1.set(color1);
	}

	public Color4 getColor1() {
		return color1;
	}

	public void setColor2(Color4 color2) {
		this.color2.set(color2);
	}

	public Color4 getColor2() {
		return color2;
	}

	@Override
	public void setBaseSize(float baseSize) {
		// TODO: Implement this method
		super.setBaseSize(baseSize);
		paint.setStrokeWidth(baseWidth*getBaseSize()/BasePiece.DEF_SIZE);
		calOffCirclePoints();
	}

	@Override
	public void setAccentColor(Color4 accentColor) {
		// TODO: Implement this method
		super.setAccentColor(accentColor);
	}

	@Override
	public void setSkin(OsuSkin skin) {
		// TODO: Implement this method
		super.setSkin(skin);
		followpoint=skin.followpoint.getRes();
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		/*
		if(tmpPaint==null)tmpPaint=new GLPaint();
		tmpPaint.set(paint);
		tmpPaint.setMixColor(Color4.White);
		//getAccentColor());
		tmpPaint.setStrokeWidth(tmpPaint.getStrokeWidth()*getScale().x);
		MLog.test.vOnce("tmpPaint-width","tmpPaint-width",tmpPaint.getStrokeWidth()+"|"+getScale()+"|"+paint.getStrokeWidth());
		MLog.test.vOnce("tmpPaint-line","tmpPaint-line","p1:"+p1+" p2:"+p2+"\n"+tmpPaint.getMixColor());
		canvas.drawLines(new float[]{p1.x,p1.y,p2.x,p2.y},tmpPaint);
		*/
		/*
		paint.setFinalAlpha(1);
		setColor1(Color4.White);
		setColor2(Color4.White);
		progress=1;*/
		if(needDraw){
			Vertex3D[] v=canvas.createLineRectVertex(offCircleP1,Vec2.onLine(offCircleP1,offCircleP2,progress),paint.getStrokeWidth(),color1);
			v[2].setColor(color2);
			v[3].setColor(color2);

			canvas.clearTmpColorBatch();
			canvas.addToColorBatch(canvas.rectToTriangles(v));
			canvas.postColorBatch(paint);
		}
	}
}
