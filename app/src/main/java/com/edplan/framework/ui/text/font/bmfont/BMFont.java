package com.edplan.framework.ui.text.font.bmfont;
import com.edplan.framework.resource.IResource;
import com.edplan.framework.ui.text.font.bmfont.BMFontDescription;
import com.edplan.framework.ui.text.font.bmfont.FNTPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *单个字体，包含所有face相同的字体文件
 */
public class BMFont
{
	private static char CHAR_NOT_FOUND=8709;
	
	private String face;
	
	private FNTInfo info;
	
	private FNTCommon common;

	private ArrayList<LoadedPage> pages=new ArrayList<LoadedPage>();

	private HashMap<Character,FNTChar> charmap=new HashMap<Character,FNTChar>();

	private HashMap<KerningPair,FNTKerning> kerningmap=new HashMap<KerningPair,FNTKerning>();

	BMFont(){

	}

	public FNTInfo getInfo() {
		return info;
	}

	public FNTCommon getCommon() {
		return common;
	}
	
	public FNTChar getFNTChar(char c){
		return charmap.get(c);
	}
	
	public FNTKerning getKerning(char first,char second){
		return kerningmap.get(new KerningPair(first,second));
	}
	
	public LoadedPage getPage(int page){
		return pages.get(page);
	}
	
	public void addFont(IResource res,String fontFile){
		try {
			addFont(res, BMFontDescription.fromStream(res.openInput(fontFile)));
		} catch (IOException e) {
			throw new BMFontException("err io: "+e.getMessage(),e);
		}
	}
	
	public void addFont(IResource res,BMFontDescription description){
		if(!face.equals(description.getInfo().face)){
			throw new BMFontException("only font with the same face can be added"); 
		}
		int pageOffset=pages.size();
		for(FNTPage page:description.getPages()){
			LoadedPage loadedPage=new LoadedPage();
			loadedPage.id=pageOffset+page.id;
			try {
				loadedPage.texture=res.loadTexture(page.file);
				pages.add(loadedPage);
			} catch (IOException e) {
				throw new BMFontException("err io: "+e.getMessage(),e);
			}
		}
		for(FNTChar c:description.chars.values()){
			c.page+=pageOffset;
			c.rawTextureArea=pages
								 .get(c.page)
								  .texture
								   .toTextureRect(
								   	c.x,c.y,c.x+c.width,c.y+c.height
								   );
			charmap.put(c.id,c);
		}
		for(Map.Entry<KerningPair,FNTKerning> e:description.kernings.entrySet()){
			kerningmap.put(e.getKey(),e.getValue());
		}
	}
	
	protected void initialFont(BMFontDescription desc){
		info=desc.getInfo();
		common=desc.getCommon();
		face=info.face;
	}

	/**
	 *
	 */
	public static BMFont loadFont(IResource res,String fontFile) throws IOException{
		BMFont font=new BMFont();
		BMFontDescription desc=BMFontDescription.fromStream(res.openInput(fontFile));
		font.initialFont(desc);
		font.addFont(res,desc);
		return font;
	}
}
