package com.edplan.simpleGame.view;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import java.util.ArrayList;
import java.util.List;
import com.edplan.simpleGame.view.Game.Background;
import com.edplan.simpleGame.MContext;

/**
 *Stage，用于承载Actor的类，直接使用父容器的Canvas，不进行裁剪
 *
 *Actor位置相对于Stage，与实际绘图无直接关联
 *在实际绘图时，Actor可以获取实际绘图的Rect
 *
 *
 */


public class Stage extends BaseWidget
{
	Background background;
	
	Matrix stageMatrix;
	Camera stageCamera;
	
	List<Actor> actors;
	
	public Stage(MContext con){
		super(con);
		actors=new ArrayList<Actor>();
		stageMatrix=new Matrix();
		stageCamera=new Camera();
		setClipCanvas(false);
	}

	public void setBackground(Background background){
		this.background=background;
	}

	public Background getBackground(){
		return background;
	}
	
	public synchronized void addActor(Actor ac){
		actors.add(ac);
	}
	
	public Stage saveCamera(){
		stageCamera.save();
		return this;
	}
	
	public Stage translate(float tx,float ty,float tz){
		stageCamera.translate(tx,ty,tz);
		return this;
	}
	
	public Stage rotate(float rx,float ry,float rz){
		stageCamera.rotate(rx,ry,rz);
		return this;
	}
	
	public Matrix getMatrix(){
		return stageMatrix;
	}
	
	public Camera getCamera(){
		return stageCamera;
	}
	
	public void applyCamera(){
		stageCamera.getMatrix(stageMatrix);
	}

	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		super.draw(canvas);
		canvas.save();
		canvas.concat(stageMatrix);
		
		if(background!=null)background.drawBackground(canvas);
		
		for(Actor a:actors){
			if(a.isVisible()){
				a.draw(canvas);
			}
		}
		
		canvas.restore();
	}
	
	
	public Canvas clipCanvasToActor(Canvas canvas,Actor child){
		return canvas;
	}
	
}
