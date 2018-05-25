package com.edplan.framework.ui.widget;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.Gravity;

public class TextView extends EdView
{
	private String text="";
	
	private int gravity=Gravity.Center;
	
	public TextView(MContext c){
		super(c);
	}

	public void setText(String text){
		if(!this.text.equals(text)){
			this.text=text;
			invalidate(FLAG_INVALIDATE_LAYOUT|FLAG_INVALIDATE_MEASURE);
		}
	}

	public String getText(){
		return text;
	}
	
	protected void rebuildTextBuffer(){
		
	}

	@Override
	protected void onMeasure(long widthSpec,long heightSpec){
		// TODO: Implement this method
		//super.onMeasure(widthSpec,heightSpec);
		
	}
}
