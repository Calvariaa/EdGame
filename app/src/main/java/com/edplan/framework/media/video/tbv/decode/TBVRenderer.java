package com.edplan.framework.media.video.tbv.decode;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.media.video.tbv.TBV;
import com.edplan.framework.media.video.tbv.TextureBasedVideo;
import java.io.IOException;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.media.video.tbv.element.DataDrawBaseTexture;

public class TBVRenderer
{
	private TextureBasedVideo video;
	
	private boolean hasReacheEndFrame=false;
	
	private TBVInputStream in;
	
	private AbstractTexture[] textures;
	
	private TBV.Header header;
	
	private TBV.FrameHeader frameHeader;
	
	private TBV.EventHeader eventHeader;
	
	private float currentPlayTime=0;
	
	private GLCanvas2D canvas;
	
	private boolean frameIsRead=false;
	
	public TBVRenderer(){
		
	}
	
	public void postFrame(float currentPlayTime){
		if(hasReacheEndFrame)return;
		this.currentPlayTime=currentPlayTime;
		try {
			handlerFrame();
		} catch (IOException e) {
			e.printStackTrace();
			onErr(e);
		}
	}
	
	protected void handlerFrame() throws IOException{
		if(frameHeader!=null&&frameHeader.startTime>currentPlayTime){
			return;
		}
		handlerNewFrame(false);
	}
	
	protected void handlerNewFrame(boolean forceClear) throws IOException{
		frameHeader=frameIsRead?TBV.FrameHeader.read(in, frameHeader):frameHeader;
		frameIsRead=false;
		switch(frameHeader.flag){
			case TBV.Frame.START_FRAME:
			case TBV.Frame.NORMAL_FRAME:{
				if(currentPlayTime>frameHeader.endTime){
					skipCurrentFrame();
					handlerNewFrame(forceClear||frameHeader.clearCanvas);
				}
				renderNewFrame(forceClear);
			}break;
			case TBV.Frame.END_FRAME:{
					hasReacheEndFrame=true;
					return;
			}
			default:return;
		}
	}
	
	private void skipCurrentFrame() throws IOException{
		if(!frameIsRead){
			in.skip(frameHeader.blockSize);
			frameIsRead=true;
		}
	}
	
	protected void renderNewFrame(boolean forceClear) throws IOException{
		frameIsRead=true;
		if(frameHeader.clearCanvas||forceClear){
			canvas.clearBuffer();
		}
		if(frameHeader.blockSize==0)return;
		
		in.mark(frameHeader.blockSize);
		
		try{
			eventHeader=TBV.EventHeader.read(in,eventHeader);
			switch(eventHeader.eventType){
				case TBV.FrameEvent.CANVAS_OPERATION:{
					operateCanvas();
				}break;
				case TBV.FrameEvent.CHANGE_SETTING:{
					changeSetting();
				}break;
				case TBV.FrameEvent.DRAW_BASE_TEXTURE:{
					drawBaseTexture();
				}break;
				case TBV.FrameEvent.DRAW_COLOR_VERTEX:{
					drawColorVertex();
				}break;
			}
		}catch(IOException ie){
			ie.printStackTrace();
			throw ie;
		}catch(Exception e){
			e.printStackTrace();
			onErr(e);
			frameIsRead=false;
			in.reset();
		}
	}
	
	
	protected void operateCanvas(){
		
	}
	
	protected void changeSetting(){
		
	}
	
	DataDrawBaseTexture dataDrawBaseTexture;
	protected void drawBaseTexture() throws IOException{
		dataDrawBaseTexture=DataDrawBaseTexture.read(in,dataDrawBaseTexture);
		
	}
	
	protected void drawColorVertex(){
		
	}
	
	public void onErr(Throwable e){
		
	}
}
