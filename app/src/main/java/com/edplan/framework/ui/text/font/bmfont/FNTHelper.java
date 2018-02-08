package com.edplan.framework.ui.text.font.bmfont;
import com.edplan.superutils.U;

public class FNTHelper
{
	public static String parseString(String head,int adv,String res){
		String s=res.substring(head.length()+adv);
		return s.substring(1,s.length()-1);
	}
	
	public static int parseInt(String head,int adv,String res){
		return U.toInt(res.substring(head.length()+adv).trim());
	}
	
	public static int[] parseIntArray(String head,int adv,String res){
		String[] list=res.substring(head.length()+adv).trim().split(",");
		int[] ilist=new int[list.length];
		for(int i=0;i<ilist.length;i++){
			ilist[i]=U.toInt(list[i]);
		}
		return ilist;
	}
}
