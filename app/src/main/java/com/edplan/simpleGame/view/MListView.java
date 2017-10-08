package com.edplan.simpleGame.view;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.Log;
import com.edplan.simpleGame.inputs.Pointer;

public class MListView extends MViewGroup
{
	ChildMeasurer measurer;
	List<BaseWidget> children;

	public MListView(){
		children=new ArrayList<BaseWidget>();
		measurer=new BaseChildMeasurer();
	}
	
	
	//添加的child的basepoint约等于无效
	@Override
	public MListView add(BaseWidget w)
	{
		// TODO: Implement this method
		super.add(w);
		children.add(w);
		return this;
	}

	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		super.draw(canvas);
		canvas.save();
		//canvas.clipRect(basePoint.x,basePoint.y,basePoint.x+getWidth(),basePoint.y+getHeight());
		//canvas.translate(basePoint.x,basePoint.y);
		measurer.measure(this);
		for(BaseWidget w:children){
			if(w.basePoint.y>getHeight()){
				continue;
			}else if(w.basePoint.y+w.getHeight()<0){
				continue;
			}else{
				canvas.save();
				w.draw(w.clipCanvasToThis(canvas));
				canvas.restore();
			}
		}
		canvas.restore();
	}

	@Override
	public boolean catchPointer(Pointer p){
		// TODO: Implement this method
		
		//Measurer获取点击信息
		boolean mc=measurer.catchPointer(p);
		//分发给child
		
		Pointer cp=p.clonePointer();
		cp.transform(basePoint.x,basePoint.y);
		
		for(int i=children.size()-1;i>=0;i--){
			if(children.get(i).ifVisible()&&children.get(i).catchPointer(cp)){
				p.registClone(cp);
				mc=mc||true;
				//Log.v("pointer","chil catch "+p.getId());
				break;
			}
		}
		return mc;
	}

	
	/*
	@Override
	public boolean onTouch(MotionEvent event)
	{
		// TODO: Implement this method
		
		
		
		if(touching){
			//if(getCatchedPointId()!=event.getPointerId(event.getActionIndex())){
			//	return false;
			//}else if(event.getAction()==MotionEvent.ACTION_UP){
			//	touching=false;
			//	setCatchedPointId(-1);
			//}
			event.setLocation(event.getX()-basePoint.x,event.getY()-basePoint.y);
			if(!measurer.onTouch(event)){
				event.setLocation(event.getX()+basePoint.x,event.getY()+basePoint.y);
				return false;
			}else{
				return true;
			}
		}else{
			if(event.getAction()==MotionEvent.ACTION_DOWN&&inWidget(event.getX(),event.getY())){
				touching=true;
				event.setLocation(event.getX()-basePoint.x,event.getY()-basePoint.y);
				if(!measurer.onTouch(event)){
					event.setLocation(event.getX()+basePoint.x,event.getY()+basePoint.y);
					return false;
				}else{
					return true;
				}
			}
		}
		return false;
	}*/
	
	
	
	public class BaseChildMeasurer extends ChildMeasurer
	{
		public float deltaDistance=BaseDatas.dpToPixel(2);
		
		float position=0;
		
		float firstPosition=0;
		float lastPosition=0;
		
		boolean draging=false;
		
		float scrollSpeed=0;
		long latestRecode=0;
		
		boolean autoScolling=false;
		
		Pointer cp;

		@Override
		public boolean catchPointer(Pointer p){
			// TODO: Implement this method
			if(inWidget(p.getX(),p.getY())){
				if(cp==null){
					cp=p;
					lastPosition=p.getY();
					firstPosition=p.getY();
					cp.addCallback(new Pointer.Callback(){

							@Override
							public void onEnd(Pointer p){
								// TODO: Implement this method
								cp=null;
							}


							@Override
							public void onMove(Pointer p){
								// TODO: Implement this method
								if(draging){
									position-=p.getY()-lastPosition;
									if(latestRecode!=0){
										//if(autoScolling){
										//	scrollSpeed+=(lastPosition-p.getY())/(System.currentTimeMillis()-latestRecode+1)*1000*0.5f;
										//}else{
										scrollSpeed=(lastPosition-p.getY())/(System.currentTimeMillis()-latestRecode+1)*1000;
										//}
										
										latestRecode=System.currentTimeMillis();
									}else{
										latestRecode=System.currentTimeMillis();
									}
									lastPosition=p.getY();
								}else{
									if(Math.abs(p.getY()-firstPosition)>BaseDatas.dpToPixel(2)){
										draging=true;
										lastPosition=p.getY();
									}
								}
							}

							@Override
							public void onUp(Pointer p){
								// TODO: Implement this method
								if(draging){
									draging=false;
									latestRecode=0;
									if(Math.abs(scrollSpeed)>BaseDatas.dpToPixel(0)){
										autoScolling=true;
									}
								}
								
							}
							
							@Override
							public void onCancel(Pointer p){
								// TODO: Implement this method
								
							}
						});
					return true;
				}else {
					return false;
				}
			}else{
				return false;
			}
		}

		
		//@Override
		/*
		public boolean onTouch(MotionEvent event)
		{
			// TODO: Implement this method
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				lastPosition=event.getY();
				firstPosition=event.getY();
			}else if(event.getAction()==MotionEvent.ACTION_MOVE){
				if(draging){
					position-=event.getY()-lastPosition;
					if(latestRecode!=0){
						scrollSpeed=(lastPosition-event.getY())/(System.currentTimeMillis()-latestRecode+1)*1000;
						latestRecode=System.currentTimeMillis();
					}else{
						latestRecode=System.currentTimeMillis();
					}
					lastPosition=event.getY();
				}else{
					if(Math.abs(event.getY()-firstPosition)>BaseDatas.dpToPixel(2)){
						draging=true;
						lastPosition=event.getY();
					}
				}
			}else if(event.getAction()==MotionEvent.ACTION_UP){
				if(draging){
					draging=false;
					latestRecode=0;
					if(Math.abs(scrollSpeed)>BaseDatas.dpToPixel(0)){
						autoScolling=true;
					}
				}
			}
			
			for(BaseWidget w:children){
				if(w.onTouch(event)){
					return true;
				}
			}
			
			return true;
		}*/
		
		public float getPosition(){
			return position;
		}
		
		public void setPosition(float _position){
			position=_position;
		}
		
		long latestMeasure;
		
		@Override
		public void measure(MListView view)
		{
			// TODO: Implement this method
			BaseWidget w;
			BaseWidget pre;
			
			if(position<0){
				scrollSpeed=0;
				float speed=-position/5+4;
				position+=speed;
				if(position>0)position=0;
			}
			
			for(int i=0;i<view.children.size();i++){
				w=view.children.get(i);
				if(i==0){
					w.setBasePoint(0,0);
				}else{
					pre=view.children.get(i-1);
					w.setBasePoint(0,pre.basePoint.y+pre.getHeight()+deltaDistance);
				}
			}
			
			for(BaseWidget bw:view.children){
				bw.setBasePoint(bw.basePoint.x,bw.basePoint.y-position);
			}
			
			if((view.children.get(view.children.size()-1).getBottom()-view.children.get(0).getTop())<view.getHeight()){
				if(position>0){
					float speed=position/5+4;
					position-=speed;
					scrollSpeed=0;
					if(position<0)position=0;
				}
			}else if(view.children.size()>0&&view.children.get(view.children.size()-1).getBottom()<view.getHeight()){
				float d=view.getHeight()-view.children.get(view.children.size()-1).getBottom();
				float speed=d/5+5;
				scrollSpeed=0;
				if(speed>d){
					position-=d;
				}else{
					position-=speed;
				}
			}
			
			
			if(autoScolling){
				if(latestMeasure!=0){
					position+=scrollSpeed*(System.currentTimeMillis()-latestMeasure+1f)/1000;
					scrollSpeed=scrollSpeed*(float)Math.pow(0.5,(System.currentTimeMillis()-latestMeasure+1f)/1000);
					latestMeasure=System.currentTimeMillis();
					if(Math.abs(scrollSpeed)<BaseDatas.dpToPixel(1)){
						scrollSpeed=0;
						autoScolling=false;
						latestMeasure=0;
					}
				}else{
					latestMeasure=System.currentTimeMillis();
				}
			}
			
		}
	}
	
	public abstract class ChildMeasurer{
		
		public abstract boolean catchPointer(Pointer p);
		
		public abstract void measure(MListView view);
	}
	
}
