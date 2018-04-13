package com.edplan.framework.graphics.opengl.objs.texture;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.RectI;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.graphics.opengl.objs.Color4;
import java.io.File;
import android.graphics.Bitmap;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.edplan.framework.graphics.layer.BufferedLayer;

public class AutoPackTexturePool extends TexturePool
{
	private int packWidth=1000;
	private int packHeight=1000;
	private int maxWidth=800;
	private int maxHeight=500;
	
	private List<BufferedLayer> packs=new ArrayList<BufferedLayer>();
	private List<AbstractTexture> packedTextures=new ArrayList<AbstractTexture>();
	private BufferedLayer currentPack;
	private GLCanvas2D packCanvas;
	private GLPaint rawPaint=new GLPaint();
	private int currentX;
	private int currentY;
	private int lineMaxY;
	
	private MContext context;
	
	public AutoPackTexturePool(TextureLoader loader,MContext context){
		super(loader);
		this.context=context;
		toNewPack();
	}

	public List<AbstractTexture> getPackedTextures() {
		return packedTextures;
	}

	public List<BufferedLayer> getPacks() {
		return packs;
	}
	
	public void writeToDir(File dir,String s){
		int i=-1;
		for(BufferedLayer t:packs){
			i++;
			try {
				File f=new File(dir,s+i+".png");
				f.createNewFile();
				t.getTexture().getTexture().toBitmap().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(f));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("err compress "+i);
			}
		}
	}
	
	public void toNewPack(){
		currentPack=new BufferedLayer(context,packWidth,packHeight,true);
		//GLTexture.createGPUTexture(packWidth,packHeight);
		packs.add(currentPack);
		packCanvas=new GLCanvas2D(currentPack);
		packCanvas.prepare();
		packCanvas.drawColor(Color4.Alpha);
		packCanvas.clearDepthBuffer();
		packCanvas.unprepare();
		currentX=0;
		currentY=0;
		lineMaxY=0;
	}
	
	private void toNextLine(){
		currentX=0;
		currentY=lineMaxY;
	}
	
	private AbstractTexture tryAddInLine(AbstractTexture raw){
		if(currentY+raw.getHeight()<currentPack.getWidth()){
			packCanvas.prepare();
			packCanvas.drawTexture(raw,RectF.xywh(currentX,currentY,raw.getWidth(),raw.getHeight()),rawPaint);
			packCanvas.unprepare();
			AbstractTexture t=new TextureRegion(currentPack.getTexture().getTexture(),RectI.xywh(currentX,currentY,raw.getWidth(),raw.getHeight()));
			currentX+=raw.getWidth();
			lineMaxY=Math.max(lineMaxY,currentY+raw.getHeight());
			packedTextures.add(t);
			return t;
		}else{
			toNewPack();
			return tryAddToPack(raw);
		}
	}
	
	private AbstractTexture tryAddToPack(AbstractTexture raw){
		if(currentX+raw.getWidth()<currentPack.getWidth()){
			return tryAddInLine(raw);
		}else{
			toNextLine();
			return tryAddToPack(raw);
		}
	}
	
	private int getMaxWidth(){
		return Math.min(packWidth,maxWidth);
	}
	
	private int getMaxHeight(){
		return Math.min(packHeight,maxHeight);
	}

	@Override
	protected AbstractTexture loadTexture(String msg) {
		// TODO: Implement this method
		AbstractTexture raw=super.loadTexture(msg);
		if(raw.getWidth()>=getMaxWidth()||raw.getHeight()>=getMaxHeight()){
			return raw;
		}else{
			return tryAddToPack(raw);
		}
	}
}
