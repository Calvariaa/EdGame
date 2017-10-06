package com.edplan.simpleGame.view;
import java.util.List;
import java.util.ArrayList;
import android.view.MotionEvent;
import android.graphics.Canvas;
import com.edplan.simpleGame.utils.TouchUtils;
import com.edplan.simpleGame.inputs.TouchEventHelper;
import com.edplan.simpleGame.inputs.Pointer;

public class MStaticViewGroup extends MViewGroup
{
	TouchEventHelper touchHelper;
	List<BaseWidget> children;
	
	public MStaticViewGroup(){
		children=new ArrayList<BaseWidget>();
		touchHelper=new TouchEventHelper();
	}

	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		super.draw(canvas);
		canvas.save();
		
		if(getHeight()!=0&&getWidth()!=0){
			clipCanvasToThis(canvas);
		}
		
		for(BaseWidget w:children){
			if(w.ifVisible()){
				if(w.isClipCanvas()){
					canvas.save();
					w.draw(w.clipCanvasToThis(canvas));
					canvas.restore();
				}else{
					w.draw(canvas);
				}
			}
		}
		canvas.restore();
	}
	
	
	
	
	@Override
	public void add(BaseWidget w)
	{
		// TODO: Implement this method
		children.add(w);
	}

	@Override
	public boolean catchPointer(Pointer p){
		// TODO: Implement this method
		
		return super.catchPointer(p);
	}

	@Override
	public boolean onTouch(MotionEvent event)
	{
		// TODO: Implement this method
		if(touchHelper.sendEvent(event)){
			//已被捕捉或直接发送给了pointer
			Pointer p=touchHelper.getCatchedPointer();
			if(p!=null){
				for(BaseWidget w:children){
					if(w.ifVisible()){
						if(w.catchPointer(p)){
							touchHelper.catchPointer(p);
						}
					}
				}
			}
			return true;
		}else{
			return false;
		}
	}
}
