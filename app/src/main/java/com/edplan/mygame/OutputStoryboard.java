package com.edplan.mygame;
import com.edplan.framework.media.video.tbv.TBV;
import com.edplan.framework.media.video.tbv.TBVJson;
import com.edplan.framework.media.video.tbv.encode.TBVEncoder;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.ruleset.amodel.playing.PlayField;
import com.edplan.nso.storyboard.PlayingStoryboard;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.json.JSONException;
import com.edplan.framework.graphics.opengl.CanvasUtil;
import com.edplan.framework.graphics.opengl.objs.texture.AutoPackTexturePool;
import java.io.File;
import com.edplan.framework.interfaces.Function;
import com.edplan.framework.media.video.tbv.TBVException;
import java.io.IOException;
import com.edplan.framework.graphics.opengl.BaseCanvas;

public class OutputStoryboard
{
	public PreciseTimeline timeline;
	
	public PlayingStoryboard storyboard;
	
	public double renderTime=20 * 1000;
	
	public double frameDeltaTime=1000/60d;
	
	public TBVEncoder encoder;
	
	public OutputThread t;
	
	public OutputStoryboard(PlayingStoryboard sb){
		storyboard=sb;
		timeline=storyboard.getTimeline();
		try {
			encoder=new TBVEncoder(new FileOutputStream("/storage/emulated/0/MyDisk/bin/tbv/test1.tbv"));
			encoder.initial(1080*16/9,1080);
			final TBV.Header.Builder b=encoder.getHeaderBuilder();
			try {
				float osuScale=PlayField.BASE_Y/1080;
				String canavsOp=CanvasUtil.buildOperation()
					.translate(1080*16/9/2-PlayField.BASE_X/2/osuScale,0)
					.scale(osuScale).toString();
				b.setJson("{"+TBVJson.OperatCanvas+":\""+canavsOp+"\"}");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			((AutoPackTexturePool)storyboard.pool).writeToDir(
				new File("/storage/emulated/0/MyDisk/bin/tbv/textures"),
				"pool",
				new Function<AutoPackTexturePool.OutputNode>(){
					@Override
					public void invoke(AutoPackTexturePool.OutputNode t) {
						// TODO: Implement this method
						String relativePath="textures/"+t.file.getName();
						b.addTexture(relativePath,t.tex);
					}
				});
			try {
				encoder.loadBack(b);
			} catch (TBVException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		t=new OutputThread();
		t.start();
	}
	
	public double progress(){
		return timeline.frameTime()/renderTime;
	}
	
	boolean f=false;
	
	public boolean finished(){
		return f;
	}
	
	public class OutputThread extends Thread{

		@Override
		public void run() {
			// TODO: Implement this method
			super.run();
			try {
				while(true){
					if(timeline.frameTime()>renderTime){
						encoder.toNewFrame(frameDeltaTime,true,TBV.Frame.END_FRAME);
						timeline.onLoop(frameDeltaTime);
						encoder.currentFrameFinish();
						break;
					}else{
						//System.out.println("start frame");
						encoder.toNewFrame(frameDeltaTime,true,TBV.Frame.NORMAL_FRAME);
						timeline.onLoop(frameDeltaTime);
						BaseCanvas canvas=encoder.getCanvas();
						canvas.prepare();
						canvas.enablePost();
						int blend=canvas.getBlendSetting().save();
						storyboard.draw(canvas);
						canvas.restoreToCount(blend);
						canvas.disablePost();
						canvas.unprepare();
						encoder.currentFrameFinish();
						//System.out.println("end frame");
					}
				}
			} catch (TBVException e) {
				e.printStackTrace();
			} catch (IOException io){
				io.printStackTrace();
			}
			f=true;
		}
	}
}
