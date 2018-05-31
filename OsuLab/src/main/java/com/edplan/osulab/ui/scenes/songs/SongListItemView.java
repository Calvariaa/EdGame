package com.edplan.osulab.ui.scenes.songs;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.framework.MContext;
import com.edplan.nso.ruleset.base.BeatmapMetadata;
import com.edplan.framework.ui.ViewConfiguration;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.widget.TextureView;
import com.edplan.nso.ruleset.base.BeatmapSetMetadata;
import com.edplan.framework.ui.widget.adapter.DataAdaptable;
import com.edplan.framework.ui.widget.component.ISelectable;

public class SongListItemView extends RelativeContainer implements DataAdaptable<BeatmapSetMetadata>,ISelectable
{
	private float positionOffset;
	
	public SongListItemView(MContext c){
		super(c);
		setRounded(ViewConfiguration.dp(10)).setShadow(ViewConfiguration.dp(8),Color4.rgba(1,1,1,0.7f),Color4.Alpha);
	}

	public void setPositionOffset(float positionOffset){
		this.positionOffset=positionOffset;
	}

	public float getPositionOffset(){
		return positionOffset;
	}
	
	@Override
	public void setSelect(boolean b){
		// TODO: Implement this method
	}

	@Override
	public boolean isSelected(){
		// TODO: Implement this method
		return false;
	}

	/**
	 *通过这个方法来让显示具体内容
	 */
	@Override
	public void adapt(BeatmapSetMetadata t){
		// TODO: Implement this method
	}

}
