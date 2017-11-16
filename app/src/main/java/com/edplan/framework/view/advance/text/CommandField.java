package com.edplan.framework.view.advance.text;
import com.edplan.framework.view.MListView;
import com.edplan.framework.view.MTextView;
import com.edplan.framework.MContext;

public class CommandField extends MListView
{
	public int defColor=0xFF777777;

	public CommandField(MContext con){
		super(con);
	}
	
	public void setDefColor(int defColor){
		this.defColor=defColor;
	}

	public int getDefColor(){
		return defColor;
	}
	
	public void addText(String s,int color){
		MTextView textView=new MTextView(getContext());
		textView.setWidth(getWidth()).setText(s).setTextColor(color).update();
		textView.setHeight(textView.getTextLayout().getHeight());
		add(textView);
		getMeasurer().toView(textView);
	}
	
	public void addText(String s){
		addText(s,getDefColor());
	}
}
