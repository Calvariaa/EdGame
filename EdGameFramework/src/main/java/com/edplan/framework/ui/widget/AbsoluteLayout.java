package com.edplan.framework.ui.widget;
import com.edplan.framework.ui.EdAbstractViewGroup;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.EdView;

public class AbsoluteLayout extends EdAbstractViewGroup
{
	public AbsoluteLayout(MContext con){
		super(con);
	}

	@Override
	public EdLayoutParam getDefaultParam(EdView view){
		// TODO: Implement this method
		final EdLayoutParam param=view.getLayoutParam();
		if(param instanceof AbsoluteParam){
			return param;
		}
		else{
			return new AbsoluteParam(param);
		}
	}

	@Override
	protected void onLayout(boolean changed,float left,float top,float right,float bottom){
		// TODO: Implement this method
	}

	@Override
	protected void onMeasure(long widthSpec,long heightSpec){
		// TODO: Implement this method

	}

	public class AbsoluteParam extends EdLayoutParam
	{
		/** 
		 *相对于当前Gravity的offset
		 */
		public float xoffset;
		public float yoffset;

		public AbsoluteParam(EdLayoutParam p){
			super(p);
		}

		public AbsoluteParam(){

		}
	}
}
