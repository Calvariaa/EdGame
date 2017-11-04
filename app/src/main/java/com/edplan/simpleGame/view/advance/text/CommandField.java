package com.edplan.simpleGame.view.advance.text;
import com.edplan.simpleGame.view.MListView;
import com.edplan.simpleGame.view.MTextView;
import com.edplan.simpleGame.MContext;

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
