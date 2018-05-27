package com.edplan.fontawesome;
import com.edplan.framework.ui.widget.LinearLayout;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.Orientation;
import com.edplan.framework.ui.text.font.FontAwesome;
import com.edplan.framework.ui.widget.FontAwesomeTextView;
import com.edplan.framework.ui.layout.MarginLayoutParam;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.widget.TextView;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.text.font.bmfont.BMFont;

public class AwesomeView extends LinearLayout
{
	public FontAwesomeTextView text;
	
	public TextView name;
	
	public AwesomeView(MContext c){
		super(c);
		setOrientation(Orientation.DIRECTION_L2R);
		setChildoffset(30);
		setGravity(Gravity.Center);
		{
			name=new TextView(c);
			name.setTextSize(50);
			name.setFont(BMFont.getFont(BMFont.Noto_Sans_CJK_JP_Medium));
			MarginLayoutParam p=new MarginLayoutParam();
			p.height=Param.MODE_WRAP_CONTENT;
			p.width=Param.MODE_WRAP_CONTENT;
			addView(name,p);
		}
	}
	
	public void setFontAwesome(int start,int end){
		StringBuilder sb=new StringBuilder();
		for(int i=start;i<end;i++){
			sb.append(FontAwesome.get(i).charvalue+":"+FontAwesome.get(i).name());
			sb.append('\n');
		}
		name.setText(sb.toString());
	}
}
