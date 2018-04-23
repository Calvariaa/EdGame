package com.edplan.framework.ui.layout;
import com.edplan.framework.ui.EdView;

public class MeasureCore
{
	public static void measureChildWithMargin(
		EdView view,
		float paddingHorizon,float paddingVertical,
		long parentWidthSpec,float widthUsed,
		long parentHeightSpec,float heightUsed
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
		long parentWidthSpec,float widthUsed,
		long parentHeightSpec,float heightUsed
	){
		LayoutParam param=view.getLayoutParam();
		long widthSpec=getChildMeasureSpec(
			parentWidthSpec,
			paddingHorizon+widthUsed,
			param.width);
		long heightSpec=getChildMeasureSpec(
			parentHeightSpec,
			paddingVertical+heightUsed,
			param.height);
		view.measure(widthSpec,heightSpec);
	}
	
	public static long getChildMeasureSpec(long spec,float padding,long childDimension){
		int mode=MeasureSpec.getMode(spec);
		float size=Math.max(MeasureSpec.getSize(spec)-padding,0);
		float psize=Param.getSize(childDimension);
		int pmode=Param.getMode(childDimension);
		float rsize=0;
		int rmode=0;
		switch(mode){
			case MeasureSpec.MODE_DEFINEDED:
				switch(pmode){
					case Param.MATCH_PARENT:
						rsize=size;
						rmode=MeasureSpec.MODE_DEFINEDED;
						break;
					case Param.WRAP_CONTENT:
						rsize=psize;
						rmode=MeasureSpec.MODE_AT_MOST;
						break;
					default:
						rsize=psize;
						rmode=MeasureSpec.MODE_DEFINEDED;
						break;
				}
				break;
			case MeasureSpec.MODE_AT_MOST:
				switch(pmode){
					case Param.MATCH_PARENT:
					case Param.WRAP_CONTENT:
						rsize=size;
						rmode=MeasureSpec.MODE_AT_MOST;
						break;
					default:
						rsize=psize;
						rmode=MeasureSpec.MODE_DEFINEDED;
						break;
				}
				break;
			default:
				switch(pmode){
					case Param.MATCH_PARENT:
					case Param.WRAP_CONTENT:
						rsize=0;
						rmode=MeasureSpec.MODE_NONE;
						break;
					default:
						rsize=psize;
						rmode=MeasureSpec.MODE_DEFINEDED;
						break;
				}
				break;
		}
		
		return MeasureSpec.makeupMeasureSpec(rsize,rmode);
	}
	
	public static class Param{
		public static final int SHIFT_SIZE=32;
		public static final long SIZE_MASK=1<<SHIFT_SIZE-1;
		public static final long MODE_MASK=3<<SHIFT_SIZE;

		public static final int NONE=0;
		public static final int MATCH_PARENT=1;
		public static final int WRAP_CONTENT=2;
		
		public static long intToLongMode(int mode){
			return ((long)mode)<<SHIFT_SIZE;
		}

		public static long makeupParam(float size,int mode){
			return intToLongMode(mode)|Float.floatToRawIntBits(size);
		}

		public static float getSize(long param){
			return Float.intBitsToFloat((int)(param&SIZE_MASK));
		}

		public static int getMode(long param){
			return (int)((param&MODE_MASK)<<SHIFT_SIZE);
		}

		public static long adjustSize(long raw,float add){
			return makeupParam(getSize(raw)+add,getMode(raw));
		}

		public static long setSize(long raw,float size){
			return makeupParam(size,getMode(raw));
		}
	}
	
	public static class MeasureSpec{
		public static final int SHIFT_SIZE=32;
		public static final long SIZE_MASK=1<<SHIFT_SIZE-1;
		public static final long MODE_MASK=3<<SHIFT_SIZE;

		public static final int MODE_NONE=1;
		public static final int MODE_AT_MOST=2;
		public static final int MODE_DEFINEDED=3;

		
		public static long intToLongMode(int mode){
			return ((long)mode)<<SHIFT_SIZE;
		}
		
		public static long makeupMeasureSpec(float size,int mode){
			return intToLongMode(mode)|Float.floatToRawIntBits(size);
		}

		public static float getSize(long spec){
			return Float.intBitsToFloat((int)(spec&SIZE_MASK));
		}

		public static int getMode(long spec){
			return (int)((spec&MODE_MASK)<<SHIFT_SIZE);
		}

		public static long adjustSize(long raw,float add){
			return makeupMeasureSpec(getSize(raw)+add,getMode(raw));
		}

		public static long setSize(long raw,float size){
			return makeupMeasureSpec(size,getMode(raw));
		}
	}
}
