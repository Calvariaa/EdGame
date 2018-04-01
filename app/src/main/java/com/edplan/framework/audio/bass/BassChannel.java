package com.edplan.framework.audio.bass;
import android.util.Log;
import com.un4seen.bass.BASS;
import java.nio.ByteBuffer;
import com.edplan.framework.MContext;
import com.edplan.framework.resource.AResource;
import java.io.IOException;

public class BassChannel
{
	private Type type;
	private int chaId;
	
	
	public enum Type{
		Stream,Sample,Music
	}
	
	public int getChannelId(){
		return chaId;
	}
	
	public Type getType(){
		return type;
	}
	
	public boolean play(boolean loop){
		return BASS.BASS_ChannelPlay(getChannelId(),loop);
	}

	public boolean play(){
		return play(false);
	}
	
	public int currentPlayTimeMS(){
		return (int)(currentPlayTimeS()*1000);
	}
	
	public double currentPlayTimeS(){
		return BASS.BASS_ChannelBytes2Seconds(chaId,BASS.BASS_ChannelGetPosition(chaId,BASS.BASS_POS_BYTE));
	}

	
	public int getFFT(ByteBuffer buf){
		Log.v("ffts","try get fft data");
		return BASS.BASS_ChannelGetData(getChannelId(),buf,BASS.BASS_DATA_FFT512);
	}

	public int getFFT(float[] b){
		ByteBuffer buf=ByteBuffer.allocateDirect(1024*2);
		buf.order(null);
		int r=BASS.BASS_ChannelGetData(getChannelId(),buf,BASS.BASS_DATA_FFT1024);
		buf.asFloatBuffer().get(b);
		return r;
	}

	public boolean pause(){
		return BASS.BASS_ChannelPause(getChannelId());
	}

	public boolean stop(){
		return BASS.BASS_ChannelStop(getChannelId());
	}

	public boolean free(){
		return BASS.BASS_StreamFree(getChannelId());
	}
	
	public static BassChannel createStreamFromResource(AResource res,String path) throws IOException{
		return createStreamFromBuffer(res.loadBuffer(path));
	}
	
	public static BassChannel createStreamFromFile(String file,int offset,int length,int flags){
		return new BassChannel(BASS.BASS_StreamCreateFile(file,offset,length,flags),Type.Stream); 
	}
	
	public static BassChannel createStreamFromFile(String file){
		return createStreamFromFile(file,0,0,0);
	}
	
	public static BassChannel createStreamFromAsset(MContext context,String path){
		BASS.Asset asset=new BASS.Asset(context.getNativeContext().getAssets(),path);
		return new BassChannel(BASS.BASS_StreamCreateFile(asset,0,0,0),Type.Stream);
	}
	
	public static BassChannel createStreamFromBuffer(ByteBuffer buffer){
		return new BassChannel(BASS.BASS_StreamCreateFile(buffer,0,0,0),Type.Stream);
	}
	
	private BassChannel(int chaId,Type type){
		this.chaId=chaId;
		this.type=type;
	}
	
	static{
		Bass.prepare();
	}
}
