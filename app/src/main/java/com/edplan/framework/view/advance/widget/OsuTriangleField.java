package com.edplan.framework.view.advance.widget;
import android.graphics.Bitmap;
import com.edplan.framework.view.BaseWidget;
import android.graphics.Canvas;
import com.edplan.framework.MContext;

public class OsuTriangleField extends BaseWidget
{
	public OsuTriangleManager triangleManager;
	
	public OsuTriangleField(MContext con,OsuTriangleManager om){
		super(con);
		triangleManager=om;
	}

	public void setTriangleManager(OsuTriangleManager triangleManager){
		this.triangleManager=triangleManager;
	}

	public OsuTriangleManager getTriangleManager(){
		return triangleManager;
	}

	@Override
	public void draw(Canvas canvas){
		// TODO: Implement this method
		super.draw(canvas);
		triangleManager.measure(canvas,getContext().getFrameDeltaTime());
		for(OsuTriangle t:triangleManager.getTriangles()){
			t.drawOnCanvas(canvas);
		}
	}
	
	@Override
	public OsuTriangleField setWidth(float width){
		// TODO: Implement this method
		super.setWidth(width);
		triangleManager.setWidth(getWidth());
		return this;
	}

	@Override
	public OsuTriangleField setHeight(float height){
		// TODO: Implement this method
		super.setHeight(height);
		triangleManager.setHeight(getHeight());
		return this;
	}

}
