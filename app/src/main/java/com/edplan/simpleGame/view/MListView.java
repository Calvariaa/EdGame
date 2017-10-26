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
		measurer=new BaseChildMeasurer(this);
	}

	public void setMeasurer(ChildMeasurer measurer){
		this.measurer=measurer;
	}

	public ChildMeasurer getMeasurer(){
		return measurer;
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
	public synchronized void draw(Canvas canvas)
	{
		// TODO: Implement this method
		super.draw(canvas);
		canvas.save();
		//canvas.clipRect(basePoint.x,basePoint.y,basePoint.x+getWidth(),basePoint.y+getHeight());
		//canvas.translate(basePoint.x,basePoint.y);
		measurer.measure();
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

	public class BaseChildMeasurer extends ChildMeasurer
	{
		public MListView view;
		public float deltaDistance=BaseDatas.dpToPixel(2);
		
		float position=0;
		
		float firstPosition=0;
		float lastPosition=0;
		
		boolean draging=false;
		
		float scrollSpeed=0;
		long latestRecode=0;
		
		boolean autoScolling=false;
		float a_scrollSpeed=0;
		
		Pointer cp;
		
		public BaseChildMeasurer(MListView _view){
			view=_view;
		}

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
										a_scrollSpeed=(lastPosition-p.getY())/(System.currentTimeMillis()-latestRecode+1)*1000;
										//}
										
										latestRecode=System.currentTimeMillis();
									}else{
										latestRecode=System.currentTimeMillis();
									}
									lastPosition=p.getY();
								}else{
									if(Math.abs(p.getY()-firstPosition)>BaseDatas.dpToPixel(2)){
										draging=true;
										p.cancelChildren();
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
									if(Math.abs(scrollSpeed)>BaseDatas.dpToPixel(5)){
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

		public float getPosition(){
			return position;
		}
		
		public void setPosition(float _position){
			position=_position;
		}

		@Override
		public void toView(BaseWidget viewTo){
			// TODO: Implement this method
			baseMeasure();
			if(viewTo.getBottom()>view.getHeight()){
				setPosition(viewTo.getBottom()-view.getHeight());
			}
		}
		
		long latestMeasure;
		
		public void baseMeasure(){
			BaseWidget w;
			BaseWidget pre;
			for(int i=0;i<view.children.size();i++){
				w=view.children.get(i);
				if(i==0){
					w.setBasePoint(0,0);
				}else{
					pre=view.children.get(i-1);
					w.setBasePoint(0,pre.basePoint.y+pre.getHeight()+deltaDistance);
				}
			}
		}
		
		@Override
		public void measure()
		{
			// TODO: Implement this method
			
			baseMeasure();
			
			if(position<0){
				scrollSpeed=0;
				float speed=-position/5+4;
				position+=speed;
				if(position>0)position=0;
			}
			
			if((view.children.get(view.children.size()-1).getBottom()-view.children.get(0).getTop())<view.getHeight()){
				if(position>0){
					float speed=position/5+4;
					position-=speed;
					scrollSpeed=0;
					if(position<0)position=0;
				}
			}else if(view.children.size()>0&&view.children.get(view.children.size()-1).getBottom()<view.getHeight()){
				float d=view.getHeight()-(view.children.get(view.children.size()-1).getBottom()-position);
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
					position+=a_scrollSpeed*(System.currentTimeMillis()-latestMeasure+1f)/1000;
					a_scrollSpeed=a_scrollSpeed*(float)Math.pow(0.5,(System.currentTimeMillis()-latestMeasure+1f)/1000);
					latestMeasure=System.currentTimeMillis();
					if(Math.abs(a_scrollSpeed)<BaseDatas.dpToPixel(1)){
						scrollSpeed=0;
						autoScolling=false;
						latestMeasure=0;
					}
				}else{
					latestMeasure=System.currentTimeMillis();
				}
			}
			
			for(BaseWidget bw:view.children){
				bw.setBasePoint(bw.basePoint.x,bw.basePoint.y-position);
			}
		}
	}
	
	public abstract class ChildMeasurer{
		
		public abstract boolean catchPointer(Pointer p);
		
		public abstract void measure();
		
		public abstract void toView(BaseWidget view);
	}
	
}
