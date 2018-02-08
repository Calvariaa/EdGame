package com.edplan.framework.ui.text.font.bmfont;
import com.edplan.superutils.U;

public class FNTInfo
{
	public static final String FACE="face";
	public static final String SIZE="size";
	public static final String BOLD="bold";
	public static final String ITALIC="italic";
	public static final String CHARSET="charset";
	public static final String UNICODE="unicode";
	public static final String STRETCHH="stretchH";
	public static final String SMOOTH="smooth";
	public static final String AA="aa";
	public static final String PADDING="padding";
	public static final String SPACING="spacing";
	public static final String OUTLINE="outline";
	
	public final String face;
	public final int size;
	public final boolean bold;
	public final boolean italic;
	public final String charset;
	public final boolean unicode;
	public final int stretchH;
	public final boolean smooth;
	public final int aa;
	public final int paddingUp,paddingRight,paddingDown,paddingLeft;
	public final int spacingHoriz,spacingVert;
	public final int outline;
	
	
	public FNTInfo(
		String face,
		int size,
		boolean bold,
		boolean italic,
		String charset,
		boolean unicode,
		int stretchH,
		boolean smooth,
		int aa,
		int paddingUp,
		int paddingRight,
		int paddingDown,
		int paddingLeft,
		int spacingHoriz,
		int spacingVert,
		int outline
	){
		this.face=face;
		this.size=size;
		this.bold=bold;
		this.italic=italic;
		this.charset=charset;
		this.unicode=unicode;
		this.stretchH=stretchH;
		this.smooth=smooth;
		this.aa=aa;
		this.paddingUp=paddingUp;
		this.paddingRight=paddingRight;
		this.paddingDown=paddingDown;
		this.paddingLeft=paddingLeft;
		this.spacingHoriz=spacingHoriz;
		this.spacingVert=spacingVert;
		this.outline=outline;
	}
	
	public static FNTInfo parse(String line){
		String[] spl=line.split(" ");
		String face=FNTHelper.parseString(FACE,1,spl[1]);
		int size=FNTHelper.parseInt(SIZE,1,spl[2]);
		boolean bold=FNTHelper.parseInt(BOLD,1,spl[3])==1;
		boolean italic=FNTHelper.parseInt(ITALIC,1,spl[4])==1;
		String charset=FNTHelper.parseString(CHARSET,1,spl[5]);
		boolean unicode=FNTHelper.parseInt(UNICODE,1,spl[6])==1;
		int stretchH=FNTHelper.parseInt(STRETCHH,1,spl[7]);
		boolean smooth=FNTHelper.parseInt(SMOOTH,1,spl[8])==1;
		int aa=FNTHelper.parseInt(AA,1,spl[9]);
		int[] padding=FNTHelper.parseIntArray(PADDING,1,spl[10]);
		int[] spacing=FNTHelper.parseIntArray(SPACING,1,spl[11]);
		int outline=FNTHelper.parseInt(OUTLINE,1,spl[12]);
		return new FNTInfo(
			face,
			size,
			bold,
			italic,
			charset,
			unicode,
			stretchH,
			smooth,
			aa,
			padding[0],padding[1],padding[2],padding[3],
			spacing[0],spacing[1],
			outline
		);
	}
}
