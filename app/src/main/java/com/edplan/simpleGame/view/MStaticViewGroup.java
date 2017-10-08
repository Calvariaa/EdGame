package com.edplan.simpleGame.view;
import java.util.List;
import java.util.ArrayList;
import android.view.MotionEvent;
import android.graphics.Canvas;
import com.edplan.simpleGame.utils.TouchUtils;
import com.edplan.simpleGame.inputs.TouchEventHelper;
import com.edplan.simpleGame.inputs.Pointer;
import android.util.Log;

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
		
		//if(getHeight()!=0&&getWidth()!=0){
		//	clipCanvasToThis(canvas);
		//}
		
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
	public MStaticViewGroup add(BaseWidget w)
	{
		// TODO: Implement this method
		super.add(w);
		children.add(w);
		return this;
	}

	@Override
	public boolean catchPointer(Pointer p){
		// TODO: Implement this method
		BaseWidget w;
		Pointer cp=p.clonePointer();
		cp.transform(basePoint.x,basePoint.y);
		for(int i=children.size()-1;i>=0;i--){
			w=children.get(i);
			if(w.ifVisible()){
				if(w.catchPointer(cp)){
					p.registClone(cp);
					touchHelper.catchPointer(p);
					return true;
				}
			}
		}
		return super.catchPointer(p);
	}

	@Override
	public boolean onTouch(MotionEvent event)
	{
		// TODO: Implement this method
		if(touchHelper.sendEvent(event)){
			//已被捕捉或直接发送给了pointer
			Pointer p=touchHelper.getCatchedPointer();
			//Log.v("pointer","测试新点");
			if(p!=null){
				//Log.v("pointer","获取新点 "+p.getX()+"|"+p.getY());
				BaseWidget w;
				for(int i=children.size()-1;i>=0;i--){
					w=children.get(i);
					if(w.ifVisible()){
						if(w.catchPointer(p)){
							touchHelper.catchPointer(p);
							break;
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
