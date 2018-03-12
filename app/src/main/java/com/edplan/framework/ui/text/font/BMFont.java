package com.edplan.framework.ui.text.font;
import com.edplan.framework.ui.text.font.bmfont.BMFontDescription;
import com.edplan.framework.resource.IResource;
import java.util.ArrayList;
import com.edplan.framework.ui.text.font.bmfont.FNTPage;

/**
 *单个字体，包含所有face相同的字体文件
 */
public class BMFont
{
	private BMFontDescription description=new BMFontDescription();
	
	private String face;
	
	private ArrayList<FNTPage> pages;
	
	BMFont(){
		
	}
	
	/**
	 *
	 */
	public static void loadFont(IResource res,String fontName){
		
	}
	
	
	
}
