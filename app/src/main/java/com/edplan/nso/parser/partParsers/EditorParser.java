package com.edplan.nso.parser.partParsers;
import com.edplan.superutils.U;
import com.edplan.nso.filepart.PartEditor;
import com.edplan.nso.beatmapComponent.Bookmarks;
import com.edplan.nso.OsuFilePart;

public class EditorParser implements PartParser<PartEditor>
{
	private PartEditor part;
	
	public EditorParser(){
		part=new PartEditor();
	}

	@Override
	public PartEditor getPart(){
		// TODO: Implement this method
		return part;
	}

	@Override
	public boolean parse(String l){
		// TODO: Implement this method
		if(l==null||l.trim().length()==0){
			return true;
		}else{
			String[] entry=U.divide(l,l.indexOf(":"));
			String v=entry[1];
			switch(entry[0]){
				case PartEditor.BeatDivisor:
					part.setBeatDivisor(U.toInt(v));
					return true;
				case PartEditor.Bookmarks:
					part.setBookmarks(Bookmarks.parse(v));
					return true;
				case PartEditor.DistanceSpacing:
					part.setDistanceSpacing(U.toFloat(v));
					return true;
				case PartEditor.GridSize:
					part.setGridSize(U.toInt(v));
					return true;
				case PartEditor.TimelineZoom:
					part.setTimelineZoom(U.toFloat(v));
					return true;
				default:
					//key not find
					return false;
			}
		}
	}


	
	
}
