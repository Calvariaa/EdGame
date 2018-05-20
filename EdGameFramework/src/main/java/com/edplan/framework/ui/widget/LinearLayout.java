package com.edplan.framework.ui.widget;
import com.edplan.framework.ui.EdAbstractViewGroup;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.Orientation;

public class LinearLayout extends EdAbstractViewGroup
{
	protected int orientation;
	
	protected float childoffset;
	
	public LinearLayout(MContext c){
		super(c);
		orientation=Orientation.DIRECTION_L2R;
	}

	public void setOrientation(int orientation){
		this.orientation=orientation;
	}

	public int getOrientation(){
		return orientation;
	}
	
	@Override
	protected void onLayout(boolean changed,float left,float top,float right,float bottom){
		// TODO: Implement this method
	}

	@Override
	protected void onMeasure(long widthSpec,long heightSpec){
		// TODO: Implement this method
	}
}
