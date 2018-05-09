package com.edplan.framework.ui.layout;
import com.edplan.framework.ui.EdView;

public class MeasureCore
{
	public static void measureChildWithMargin(
		EdView view,
		float paddingHorizon,float paddingVertical,
		long parentWidthSpec,long parentHeightSpec,
		float widthUsed,float heightUsed
	){
		MarginLayoutParam param=(MarginLayoutParam)view.getLayoutParam();
		long widthSpec=getChildMeasureSpec(
			parentWidthSpec,
			paddingHorizon+param.getMarginHorizon()+widthUsed,
			param.width);
		long heightSpec=getChildMeasureSpec(
			parentHeightSpec,
			paddingVertical+param.getMarginVertical()+heightUsed,
			param.height);
		view.measure(widthSpec,heightSpec);
	}
	
	public static void measureChild(
		EdView view,
		float paddingHorizon,float paddingVertical,
		long parentWidthSpec,long parentHeightSpec
	){
		EdLayoutParam param=view.getLayoutParam();
		long widthSpec=getChildMeasureSpec(
			parentWidthSpec,
			paddingHorizon,
			param.width);
		long heightSpec=getChildMeasureSpec(
			parentHeightSpec,
			paddingVertical,
			param.height);
		view.measure(widthSpec,heightSpec);
	}
	
	public static long getChildMeasureSpec(long spec,float padding,long childDimension){
		int mode=EdMeasureSpec.getMode(spec);
		float size=Math.max(EdMeasureSpec.getSize(spec)-padding,0);
		//float psize=Param.getSize(childDimension);
		int pmode=Param.getMode(childDimension);
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
