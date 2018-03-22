package com.edplan.framework.ui.text.font.drawing;
import java.util.ArrayList;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.ui.text.font.bmfont.BMFont;
import com.edplan.framework.ui.text.font.bmfont.FNTChar;
import com.edplan.framework.math.RectF;
import com.edplan.framework.ui.text.font.bmfont.FNTKerning;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.math.Vec3;

public class TextVertexPrinter
{
	public static final char NO_PREVIOUS_CHAR=0;
	/**
	 *@field textSize:实际绘制时的大小
	 *@field currentBaseY:当前绘制的起始Y坐标
	 *@field currentX:当前绘制的起始X
	 *@field scale:缩放，设置textSize时自动计算
	 */
	private float textSize;
	
	private float lineHeight;
	
	private float currentBaseY;
	
	private float currentX;
	
	private float scale;
	
	private GLPaint paint;
	
	private ArrayList<Texture3DBatch> batchs;
	
	private char preChar=0;
	
	private BMFont font;
	
	public TextVertexPrinter(BMFont font,float startX,float startY,GLPaint paint){
		this.font=font;
		this.paint=paint;
		initial(startX,startY);
	}
	
	public void initial(float startX,float startY){
		batchs=new ArrayList<Texture3DBatch>();
		this.lineHeight=font.getCommon().lineHeight;
		recalScale();
		currentX=startX;
		currentBaseY=startY;
	}
	
	private void recalScale(){
		scale=textSize/lineHeight;
	}
	
	public void setTextSize(float s){
		textSize=s;
		recalScale();
	}
	
	public void printChar(char c){
		FNTChar fntc=font.getFNTChar(c);
		if(fntc!=null){
			RectF area=calCharArea(fntc);
			float xadvance=fntc.xadvance;
			if(preChar!=NO_PREVIOUS_CHAR){
				FNTKerning kerning=font.getKerning(preChar,c);
				if(kerning!=null){
					area.move(kerning.amount,0);
					xadvance+=kerning.amount;
				}
			}
			xadvance*=scale;
			Texture3DBatch batch=getBatchByPage(fntc.page);
			
			preChar=c;
		}else{
			printErrCharacter();
		}
	}
	
	private Texture3DBatch getBatchByPage(int page){
		return batchs.get(page);
	}
	
	private RectF calCharArea(FNTChar fntc){
		float x=currentX;
		float h=fntc.height*scale;
		float y=currentBaseY-h;
		RectF area=RectF.xywh(x,y,fntc.width*scale,h);
		//y方向的offset和绘制坐标系的方向相反
		area.move(fntc.xoffset*scale,-fntc.yoffset*scale);
		return area;
	}
	
	public void printErrCharacter(){
		
	}
}
