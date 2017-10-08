package com.edplan.simpleGame.view;
import android.graphics.Paint;
import android.graphics.Canvas;
import com.edplan.simpleGame.inputs.Pointer;

public class MProgressBar extends BaseWidget
{
	MButton dragButton;
	
	MCallback callback;
	
	public boolean dragable=true;
	
	Paint pPaint;
	Paint bPaint;
	
	public float progress=0;
	
	public MProgressBar(){
		setWidth(500);
		setHeight(40);
		dragButton=new MButton();
		pPaint=new Paint();
		pPaint.setAntiAlias(true);
		pPaint.setARGB(255,50,150,240);
		bPaint=new Paint();
		bPaint.setAntiAlias(false);
		bPaint.setARGB(70,50,150,240);
	}

	public void setDragable(boolean dragable){
		this.dragable=dragable;
	}

	public boolean isDragable(){
		return dragable;
	}

	public void setCallback(MCallback callback){
		this.callback=callback;
	}

	public MCallback getMCallback(){
		return callback;
	}

	public void setProgress(float progress){
		this.progress=progress;
	}

	public float getProgress(){
		return progress;
	}
	
	@Override
	public void draw(Canvas canvas){
		// TODO: Implement this method
		super.draw(canvas);
		//canvas.drawRect(0,getTop(),getRight(),getBottom(),pPaint);
		dragButton.setHeight(getHeight()*0.8f);
		dragButton.setWidth(dragButton.getHeight()*0.4f);
		dragButton.setCenter(progress*getWidth(),getHeight()/2);
		canvas.drawRect(0,getHeight()*0.2f,getWidth(),getHeight()*0.8f,bPaint);
		canvas.drawRect(0,getHeight()*0.2f,progress*getWidth(),getHeight()*0.8f,pPaint);
		canvas.save();
		dragButton.drawButton(dragButton.clipCanvasToThis(canvas));
		canvas.restore();
	}
	
	private float calProgress(float x){
		float p=(x-getLeft())/getWidth();
		if(p<0)p=0;
		if(p>1)p=1;
		return p;
	}

	
	@Override
	public boolean catchPointer(Pointer p){
		// TODO: Implement this method
		if(!isDragable())return false;
		Pointer cp=p.clonePointer();
		cp.transform(basePoint.x,basePoint.y);
		if(dragButton.catchPointer(cp)){
			p.registClone(cp);
			p.addCallback(new Pointer.Callback(){

					@Override
					public void onEnd(Pointer p){
						// TODO: Implement this method
					}


					@Override
					public void onMove(Pointer p){
						// TODO: Implement this method
						setProgress(calProgress(p.getX()));
						if(callback!=null)callback.onChange(getProgress());
					}

					@Override
					public void onUp(Pointer p){
						// TODO: Implement this method
					}

					@Override
					public void onCancel(Pointer p){
						// TODO: Implement this method
					}
				});
			return true;
		}else{
			if(inWidget(p.getX(),p.getY())){
				setProgress(calProgress(p.getX()));
				p.addCallback(new Pointer.Callback(){

						@Override
						public void onEnd(Pointer p){
							// TODO: Implement this method
						}


						@Override
						public void onMove(Pointer p){
							// TODO: Implement this method
							setProgress(calProgress(p.getX()));
							if(callback!=null)callback.onChange(getProgress());
						}

						@Override
						public void onUp(Pointer p){
							// TODO: Implement this method
						}

						@Override
						public void onCancel(Pointer p){
							// TODO: Implement this method
						}
					});
				if(callback!=null)callback.onChange(getProgress());
				return true;
			}else{
				return false;
			}
		}
	}
	
	public interface MCallback{
		public void onChange(float p);
	}
}
