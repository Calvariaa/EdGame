package com.edplan.framework.ui.text.font.bmfont;
import com.edplan.superutils.U;

public class FNTHelper
{
	public static int parseInt(String head,int adv,String res){
		return U.toInt(res.substring(head.length()+adv));
	}
}
