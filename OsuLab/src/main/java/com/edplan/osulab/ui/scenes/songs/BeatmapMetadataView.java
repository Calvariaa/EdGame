package com.edplan.osulab.ui.scenes.songs;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.framework.MContext;
import com.edplan.nso.ruleset.amodel.BeatmapMetadata;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.widget.TextureView;
import com.edplan.nso.ruleset.amodel.BeatmapSetMetadata;
import com.edplan.framework.ui.widget.adapter.DataAdaptable;

public class BeatmapMetadataView extends RelativeContainer implements DataAdaptable<BeatmapSetMetadata>
{
	private BeatmapSetMetadata metadata;
	
	private TextureView modeIcon;
	
	private float positionOffset;
	
	public BeatmapMetadataView(MContext c){
		super(c);
		setRounded(ViewConfiguration.dp(10)).setShadow(ViewConfiguration.dp(8),Color4.rgba(1,1,1,0.7f),Color4.Alpha);
	}

	public void setPositionOffset(float positionOffset){
		this.positionOffset=positionOffset;
	}

	public float getPositionOffset(){
		return positionOffset;
	}

	/**
	 *通过这个方法来让显示具体内容
	 */
	@Override
	public void adapt(BeatmapSetMetadata t){
		// TODO: Implement this method
	}

}
