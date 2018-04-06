package com.edplan.framework.timing;
import com.edplan.framework.audio.bass.BassChannel;

/**
 *一个同步音频的Timeline
 */
public class AudioTimeline extends PreciseTimeline
{
	private int preTime=0;
	
	private BassChannel channel;
	
	public AudioTimeline(BassChannel channel){
		this.channel=channel;
	}

	@Override
	public void onLoop(double deltaTime)
	{
		// TODO: Implement this method
		int cur=channel.currentPlayTimeMS();
		super.onLoop(cur-preTime);
		preTime=cur;
	}
	
}
