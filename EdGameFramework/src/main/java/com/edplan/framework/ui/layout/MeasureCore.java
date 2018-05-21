package com.edplan.framework.ui.layout;
import com.edplan.framework.ui.EdView;
import android.util.Log;

public class MeasureCore
{
	public static void measureChildWithMargin(
		EdView view,
		float paddingHorizon,float paddingVertical,
		long parentWidthSpec,long parentHeightSpec,
		float widthUsed,float heightUsed
	){
		final EdLayoutParam param=view.getLayoutParam();
		if(param instanceof MarginLayoutParam){
			final MarginLayoutParam mparam=(MarginLayoutParam)param;
			final long widthSpec=getChildMeasureSpec(
				parentWidthSpec,
				paddingHorizon+mparam.getMarginHorizon()+widthUsed,
				param.width);
			final long heightSpec=getChildMeasureSpec(
				parentHeightSpec,
				paddingVertical+mparam.getMarginVertical()+heightUsed,
				param.height);
			view.measure(widthSpec,heightSpec);
		}else{
			final long widthSpec=getChildMeasureSpec(
				parentWidthSpec,
				paddingHorizon+widthUsed,
				param.width);
			final long heightSpec=getChildMeasureSpec(
				parentHeightSpec,
				paddingVertical+heightUsed,
				param.height);
			view.measure(widthSpec,heightSpec);
		}
		
	}
	
	public static void measureChild(
		EdView view,
		float paddingHorizon,float paddingVertical,
		long parentWidthSpec,long parentHeightSpec
	){
		final EdLayoutParam param=view.getLayoutParam();
		final long widthSpec=getChildMeasureSpec(
			parentWidthSpec,
			paddingHorizon,
			param.width);
		final long heightSpec=getChildMeasureSpec(
			parentHeightSpec,
			paddingVertical,
			param.height);
		view.measure(widthSpec,heightSpec);
		//Log.v("measure","view:"+view.getName()+" "+EdMeasureSpec.toString(widthSpec)+" : "+EdMeasureSpec.toString(heightSpec));
	}
	
	public static long getChildMeasureSpec(long spec,float padding,long childDimension){
		final int mode=EdMeasureSpec.getMode(spec);
		final float size=Math.max(EdMeasureSpec.getSize(spec)-padding,0);
		final int pmode=Param.getMode(childDimension);
		float rsize=0;
		int rmode=0;
		switch(mode){
			case EdMeasureSpec.MODE_DEFINEDED:
				switch(pmode){
					case Param.MATCH_PARENT:
						rsize=size;
						rmode=EdMeasureSpec.MODE_DEFINEDED;
						break;
					case Param.WRAP_CONTENT:
						rsize=Param.getSize(childDimension);
						rmode=EdMeasureSpec.MODE_AT_MOST;
						break;
					default:
						rsize=Param.getSize(childDimension);
						rmode=EdMeasureSpec.MODE_DEFINEDED;
						break;
				}
				break;
			case EdMeasureSpec.MODE_AT_MOST:
				switch(pmode){
					case Param.MATCH_PARENT:
					case Param.WRAP_CONTENT:
						rsize=size;
						rmode=EdMeasureSpec.MODE_AT_MOST;
						break;
					default:
						rsize=Param.getSize(childDimension);
						rmode=EdMeasureSpec.MODE_DEFINEDED;
						break;
				}
				break;
			default:
				switch(pmode){
					case Param.MATCH_PARENT:
					case Param.WRAP_CONTENT:
						rsize=0;
						rmode=EdMeasureSpec.MODE_NONE;
						break;
					default:
						rsize=Param.getSize(childDimension);
						rmode=EdMeasureSpec.MODE_DEFINEDED;
						break;
				}
				break;
		}
		return EdMeasureSpec.makeupMeasureSpec(rsize,rmode);
	}
}
