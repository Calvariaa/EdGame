package com.edplan.framework.media.video.tbv.encode;

import com.edplan.framework.media.video.tbv.TBV;
import com.edplan.framework.media.video.tbv.TBVException;
import java.io.IOException;
import java.io.DataOutputStream;
import com.edplan.framework.utils.Tag;
import com.edplan.framework.graphics.opengl.BlendType;
import com.edplan.framework.media.video.tbv.element.DataDrawBaseTexture;
import java.util.List;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;

/**
 *操作应该严格按照规范
 *   1)初始化，将TBV.Header输出
 *   2)开始帧绘制
 *   3)Loop:
 *       1)重填帧头数据，开始缓存
 *       2)Loop:
 *         输出各种事件
 *         break：当输出帧结束时结束
 *       3)回填缓存块长度，将帧头和缓存输出
 *       break：输出结束帧
 *   结束encode
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */



public class TBVEncoder 
{
	public enum EncodeMainState{
		WriteHeader,
		FrameOutput,
		EndFrameOutput,
		EncodeDone
	}
	
	public enum EncodeFrameState{
		WriteFrameHeader,
		BufferFrameData,
		EndBufferFrameData,
		OutputFrameData,
		EndFrameOutput
	}
	
	public enum FrameEventState{
		WritingHeader
	}
	
	public EncodeMainState mainState;
	
	public EncodeFrameState frameState;
	
	private TBVOutputStream out;
	
	private TBVOutputStream bufferedOut;
	
	private BufferedByteOutputStream rawBufferedOut;
	
	public TBV.Header header=new TBV.Header();

	private TBV.FrameHeader frameHeader=new TBV.FrameHeader();

	private TBV.EventHeader eventHeader=new TBV.EventHeader();

	private float currentPlayTime=0;
	
	private double frameDeltaTime=1000/60d;
	
	
	
	public void initial(){
		rawBufferedOut=new BufferedByteOutputStream(1024*32);
		bufferedOut=new TBVOutputStream(new DataOutputStream(rawBufferedOut));
	}
	
	public void writeTBVHeader() throws TBVException, IOException{
		if(mainState!=EncodeMainState.WriteHeader){
			throw new TBVException("错误的步骤 "+mainState);
		}
		TBV.Header.write(out,header);
		mainState=EncodeMainState.FrameOutput;
	}
	
	public void checkFrameState(EncodeFrameState s) throws TBVException{
		if(frameState!=s){
			throw new TBVException("错误的步骤 "+frameState);
		}
	}
	
	public void toNewFrame(double deltaTime,boolean clearFrame) throws TBVException{
		if(deltaTime<=0){
			toNewFrame(frameDeltaTime,clearFrame);
		}
		checkOutputFrame();
		checkFrameState(EncodeFrameState.WriteFrameHeader);
		frameHeader.clearCanvas=clearFrame;
		frameHeader.startTime=currentPlayTime;
		currentPlayTime+=deltaTime;
		frameHeader.endTime=currentPlayTime;
		frameState=EncodeFrameState.BufferFrameData;
	}
	
	private void flushEventHeader(short type) throws IOException{
		eventHeader.eventType=type;
		TBV.EventHeader.write(bufferedOut,eventHeader);
	}
	
	private TBV.SettingEvent settingEvent=new TBV.SettingEvent();
	
	@Tag("frameEvent::setting")
	public void setBlendType(boolean enable,BlendType type) throws IOException{
		flushEventHeader(TBV.FrameEvent.CHANGE_SETTING);
		settingEvent.setBlend(enable,type);
		TBV.SettingEvent.write(bufferedOut,settingEvent);
	}
	
	DataDrawBaseTexture bufferedDraw=new DataDrawBaseTexture(50);
	@Tag("frameEvent::draw")
	public void drawBaseTexture(int textureId,List<TextureVertex3D> vertexs) throws IOException{
		flushEventHeader(TBV.FrameEvent.DRAW_BASE_TEXTURE);
		
	}
	
	
	public void currentFrameFinish() throws TBVException, IOException{
		checkFrameState(EncodeFrameState.BufferFrameData);
		flushEventHeader(TBV.FrameEvent.FRAME_END);
		frameState=EncodeFrameState.EndBufferFrameData;
		outputFrame();
	}
	
	
	private void outputFrame() throws TBVException, IOException{
		checkOutputFrame();
		checkFrameState(EncodeFrameState.EndBufferFrameData);
		frameHeader.blockSize=rawBufferedOut.size();
		TBV.FrameHeader.write(out,frameHeader);
		out.writeBytes(rawBufferedOut.ary,0,rawBufferedOut.size());
		rawBufferedOut.reset();
		if(frameHeader.flag==TBV.Frame.END_FRAME){
			frameState=EncodeFrameState.EndFrameOutput;
			mainState=EncodeMainState.EndFrameOutput;
			endEncode();
		}else{
			frameState=EncodeFrameState.WriteFrameHeader;
		}
	}


	
	
	public void endEncode() throws TBVException, IOException{
		if(mainState!=EncodeMainState.EndFrameOutput){
			throw new TBVException("错误的步骤 "+mainState);
		}
		mainState=EncodeMainState.EncodeDone;
	}
	
	public void checkOutputFrame() throws TBVException{
		if(mainState!=EncodeMainState.FrameOutput){
			throw new TBVException("错误的步骤 "+mainState);
		}
	}
	
	
	
	
	

	public void setFrameDeltaTime(double frameDeltaTime) {
		this.frameDeltaTime=frameDeltaTime;
	}

	public double getFrameDeltaTime() {
		return frameDeltaTime;
	}

}
