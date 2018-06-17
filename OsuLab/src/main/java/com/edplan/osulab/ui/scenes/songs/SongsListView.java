package com.edplan.osulab.ui.scenes.songs;

import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.interfaces.Setter;
import com.edplan.framework.ui.EdContainer;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.inputs.ScrollEvent;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.EdMeasureSpec;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.MarginLayoutParam;
import com.edplan.framework.ui.layout.Orientation;
import com.edplan.framework.ui.widget.component.Scroller;
import com.edplan.framework.ui.EdAbstractViewGroup;

public class SongsListView extends EdAbstractViewGroup
{
	protected float childoffset;

	protected float startOffset;

	protected float scrollRate;

	protected float maxChildrenSize=500;

	protected boolean enableGravityCenter=false;

	public SongsListView(MContext c){
		super(c);
		setScrollableFlag(ScrollEvent.DIRECTION_VERTICAL);
	}

	protected Scroller scroller=new Scroller(new Setter<Float>(){
			@Override
			public void set(Float t){
				// TODO: Implement this method
				scrollRate=t;
				invalidate(FLAG_INVALIDATE_LAYOUT);
			}
		});

	@Override
	public boolean onScroll(ScrollEvent event){
		// TODO: Implement this method
		final float offset=-event.getScrollY();
		switch(event.getState()){
			case ScrollEvent.STATE_START:
				scroller.setStartValue(getScrollRateMin());
				scroller.setEndValue(getScrollRateMax());
				scroller.start(scrollRate);
				scroller.update();
				return true;
			case ScrollEvent.STATE_SCROLLING:
				scroller.add(offset,event.getDeltaTime());
				scroller.update();
				return true;
			case ScrollEvent.STATE_END:
				scroller.end();
				scroller.update();
				return true;
			case ScrollEvent.STATE_CANCEL:
				scroller.cancel();
				scroller.update();
				return true;
		}
		return false;
	}

	public void setEnableGravityCenter(boolean enableGravityCenter){
		this.enableGravityCenter=enableGravityCenter;
	}

	public boolean isEnableGravityCenter(){
		return enableGravityCenter;
	}

	public void setStartOffset(float startOffset){
		this.startOffset=startOffset;
	}

	public float getStartOffset(){
		return startOffset;
	}

	public float getLatestUsedSpace(){
		return latestUsedSpace;
	}

	public void setChildoffset(float childoffset){
		this.childoffset=childoffset;
	}

	public float getChildoffset(){
		return childoffset;
	}

	protected float getScrollRateMax(){
		if(getScrollableFlag()==ScrollEvent.DIRECTION_HORIZON){
			if(latestUsedSpace<getMeasuredWidth()){
				return 0;
			}
			return latestUsedSpace-getMeasuredWidth();
		}else{
			if(latestUsedSpace<getMeasuredHeight()){
				return 0;
			}
			return latestUsedSpace-getMeasuredHeight();
		}
	}

	protected float getScrollRateMin(){
		return 0;
	}

	/*
	@Override
	protected void drawContainer(BaseCanvas canvas){
		// TODO: Implement this method
		final int count=getChildrenCount();
		for(int i=0;i<count;i++){
			final EdView view=getChildAt(i);
			if(view.getVisiblility()==VISIBILITY_SHOW){
				if(view.getTop()>getMeasuredHeight())break;
				if(view.getBottom()<0)continue;
				final int savedcount=canvas.save();
				try{
					canvas.translate(view.getLeft(),view.getTop());
					canvas.clip(view.getWidth(),view.getHeight());
					view.onDraw(canvas);
				}finally{
					canvas.restoreToCount(savedcount);
				}
			}
		}
	}
	*/

	@Override
	public EdLayoutParam adjustParam(EdView view,EdLayoutParam param){
		// TODO: Implement this method
		if(param instanceof MarginLayoutParam){
			return param;
		}else{
			return new MarginLayoutParam(param);
		}
	}

	private float latestUsedSpace;

	protected void layoutVertical(float left,float top,float right,float bottom){
		final int count=getChildrenCount();

		final float parentLeft=getPaddingLeft();
		final float parentTop=getPaddingTop();
		final float parentBottom=bottom-top-getPaddingBottom();
		final float parentRight=right-left-getPaddingRight();
		float heightUsed=parentTop+startOffset-scrollRate;
		final int gravity=getGravity();
		if(enableGravityCenter)if(gravity==Gravity.Center){
				final float cx=(parentLeft+parentRight)/2;
				heightUsed=(parentTop+parentBottom-latestUsedSpace)/2;
				for(int i=0;i<count;i++){
					final EdView view=getChildAt(i);
					if(view.getVisiblility()!=VISIBILITY_GONE){
						final MarginLayoutParam param=(MarginLayoutParam)view.getLayoutParam();
						final float dx=param.xoffset+cx-view.getMeasuredWidth()/2+param.marginLeft;
						final float dy=param.yoffset+heightUsed+param.marginTop;
						view.layout(dx,dy,view.getMeasuredWidth()+dx,view.getMeasuredHeight()+dy);
						heightUsed+=param.getMarginVertical()+view.getMeasuredHeight()+childoffset;
					}
				}
				return;
			}
		switch(gravity&Gravity.MASK_HORIZON){
			case Gravity.RIGHT:{
					for(int i=0;i<count;i++){
						final EdView view=getChildAt(i);
						if(view.getVisiblility()!=VISIBILITY_GONE){
							final MarginLayoutParam param=(MarginLayoutParam)view.getLayoutParam();
							final float dx=param.xoffset+parentRight-param.marginRight;
							final float dy=param.yoffset+heightUsed+param.marginTop;
							view.layout(dx,dy,view.getMeasuredWidth()+dx,view.getMeasuredHeight()+dy);
							heightUsed+=param.getMarginVertical()+view.getMeasuredHeight()+childoffset;
						}
					}
				}break;
			case Gravity.CENTER_HORIZON:{
					final float cx=(parentLeft+parentRight)/2;
					for(int i=0;i<count;i++){
						final EdView view=getChildAt(i);
						if(view.getVisiblility()!=VISIBILITY_GONE){
							final MarginLayoutParam param=(MarginLayoutParam)view.getLayoutParam();
							final float dx=param.xoffset+cx-view.getMeasuredWidth()/2+param.marginLeft;
							final float dy=param.yoffset+heightUsed+param.marginTop;
							view.layout(dx,dy,view.getMeasuredWidth()+dx,view.getMeasuredHeight()+dy);
							heightUsed+=param.getMarginVertical()+view.getMeasuredHeight()+childoffset;
						}
					}
				}break;
			case Gravity.LEFT:
			default:{
					for(int i=0;i<count;i++){
						final EdView view=getChildAt(i);
						if(view.getVisiblility()!=VISIBILITY_GONE){
							final MarginLayoutParam param=(MarginLayoutParam)view.getLayoutParam();
							final float dx=param.xoffset+parentLeft+param.marginLeft;
							final float dy=param.yoffset+heightUsed+param.marginTop;
							view.layout(dx,dy,view.getMeasuredWidth()+dx,view.getMeasuredHeight()+dy);
							heightUsed+=param.getMarginVertical()+view.getMeasuredHeight()+childoffset;
						}
					}
				}break;
		}
	}

	@Override
	protected void onLayout(boolean changed,float left,float top,float right,float bottom){
		// TODO: Implement this method
		scroller.update();
		layoutVertical(left,top,right,bottom);
	}

	protected void measureVertical(long widthSpec,long heightSpec){
		final int count=getChildrenCount();
		float heightUsed=0;
		final long adjustedSpec=EdMeasureSpec.makeupMeasureSpec(Math.max(EdMeasureSpec.getSize(heightSpec),maxChildrenSize),EdMeasureSpec.MODE_AT_MOST);
		for(int i=0;i<count;i++){
			final EdView view=getChildAt(i);
			final EdLayoutParam param=view.getLayoutParam();
			final float marginVertical=(param instanceof MarginLayoutParam)?(((MarginLayoutParam)param).getMarginVertical()):0;
			if(view.getVisiblility()!=VISIBILITY_GONE){
				measureChildWithMarginIgnorePadding(view,widthSpec,adjustedSpec,getPaddingHorizon(),heightUsed);
				heightUsed+=view.getMeasuredHeight()+marginVertical;
				if(i!=count-1)heightUsed+=childoffset;
			}
		}
		latestUsedSpace=heightUsed;
		float xd;
		float yd;
		{
			final int mmode=EdMeasureSpec.getMode(widthSpec);
			switch(mmode){
				case EdMeasureSpec.MODE_DEFINEDED:
					xd=EdMeasureSpec.getSize(widthSpec);
					break;
				case EdMeasureSpec.MODE_AT_MOST:
					xd=Math.min(EdMeasureSpec.getSize(widthSpec),getPaddingHorizon()+getDefaultMaxChildrenMeasuredWidthWithMargin());
					break;
				case EdMeasureSpec.MODE_NONE:
				default:
					xd=getPaddingHorizon()+getDefaultMaxChildrenMeasuredWidthWithMargin();
					break;
			}
		}
		{
			final int mmode=EdMeasureSpec.getMode(heightSpec);
			switch(mmode){
				case EdMeasureSpec.MODE_DEFINEDED:
					yd=EdMeasureSpec.getSize(heightSpec);
					break;
				case EdMeasureSpec.MODE_AT_MOST:
					yd=Math.min(EdMeasureSpec.getSize(heightSpec),getPaddingVertical()+heightUsed);
					break;
				case EdMeasureSpec.MODE_NONE:
				default:
					yd=Math.max(getPaddingHorizon()+heightUsed,EdMeasureSpec.getSize(heightSpec));
					break;
			}
		}
		//Log.v("layout","measure ScrollContainer Vertical, height used "+heightUsed+", set dimensition "+xd+";"+yd+" spec:"+EdMeasureSpec.toString(widthSpec)+":"+EdMeasureSpec.toString(heightSpec));
		setMeasuredDimensition(xd,yd);
	}

	@Override
	protected void onMeasure(long widthSpec,long heightSpec){
		// TODO: Implement this method
		measureVertical(widthSpec,heightSpec);
	}
}
