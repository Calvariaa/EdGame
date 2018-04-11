package com.edplan.nso.parser;
import android.util.Log;
import com.edplan.nso.NsoBeatmapParsingException;
import com.edplan.nso.NsoException;
import com.edplan.nso.OsuBeatmap;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.beatmap.StdBeatmapBindingData;
import com.edplan.nso.filepart.PartColours;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.nso.filepart.PartEditor;
import com.edplan.nso.filepart.PartEvents;
import com.edplan.nso.filepart.PartGeneral;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.filepart.PartMetadata;
import com.edplan.nso.filepart.PartTimingPoints;
import com.edplan.nso.filepart.PartVariables;
import com.edplan.nso.parser.partParsers.ColoursParser;
import com.edplan.nso.parser.partParsers.DifficultyParser;
import com.edplan.nso.parser.partParsers.EditorParser;
import com.edplan.nso.parser.partParsers.GeneralParser;
import com.edplan.nso.parser.partParsers.HitObjectsParser;
import com.edplan.nso.parser.partParsers.MetadataParser;
import com.edplan.nso.parser.partParsers.PartParser;
import com.edplan.nso.parser.partParsers.StoryboardPartParser;
import com.edplan.nso.parser.partParsers.TimingPointsParser;
import com.edplan.nso.ruleset.ModeManager;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.superutils.AdvancedBufferedReader;
import com.edplan.superutils.U;
import com.edplan.superutils.interfaces.StringMakeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StdBeatmapDecoder extends BaseDecoder implements StringMakeable
{
	private StdBeatmapBindingData bd;

	private int format;

	private GeneralParser generalParser;
	
	private EditorParser editorParser;
	
	private MetadataParser metadataParser;
	
	private DifficultyParser difficultyParser;
	
	private StoryboardPartParser storyboardDecoder;
	
	private TimingPointsParser timingPointsParser;
	
	private ColoursParser coloursParser;
	
	private HitObjectsParser hitObjectsParser;
	
	
	public StdBeatmapDecoder(InputStream in,String resInfo){
		super(in,resInfo);
		initialParsers();
	}
	
	public StdBeatmapDecoder(File file) throws FileNotFoundException{
		super(file);
		initialParsers();
	}
	
	
	public StdBeatmapDecoder(AdvancedBufferedReader _reader,ParsingBeatmap bdmsg){
		super(_reader,bdmsg);
		initialParsers();
	}

	public void setFormat(int format){
		this.format=format;
	}

	public int getFormat(){
		return format;
	}

	public void setGeneralParser(GeneralParser generalParser){
		this.generalParser=generalParser;
	}

	public GeneralParser getGeneralParser(){
		return generalParser;
	}

	public void setEditorParser(EditorParser editorParser){
		this.editorParser=editorParser;
	}

	public EditorParser getEditorParser(){
		return editorParser;
	}

	public void setMetadataParser(MetadataParser metadataParser){
		this.metadataParser=metadataParser;
	}

	public MetadataParser getMetadataParser(){
		return metadataParser;
	}

	public void setDifficultyParser(DifficultyParser difficultyParser){
		this.difficultyParser=difficultyParser;
	}

	public DifficultyParser getDifficultyParser(){
		return difficultyParser;
	}

	public void setTimingPointsParser(TimingPointsParser timingPointsParser){
		this.timingPointsParser=timingPointsParser;
	}

	public TimingPointsParser getTimingPointsParser(){
		return timingPointsParser;
	}

	public void setColoursParser(ColoursParser coloursParser){
		this.coloursParser=coloursParser;
	}

	public ColoursParser getColoursParser(){
		return coloursParser;
	}

	public void setHitObjectsParser(HitObjectsParser hitObjectsParser){
		this.hitObjectsParser=hitObjectsParser;
	}

	public HitObjectsParser getHitObjectsParser(){
		return hitObjectsParser;
	}
	
	public void initialParsers(){
		bd=new StdBeatmapBindingData();
		
		generalParser      =  new GeneralParser();
		editorParser       =  new EditorParser();
		metadataParser     =  new MetadataParser();
		difficultyParser   =  new DifficultyParser();
		storyboardDecoder  =  new StoryboardPartParser();
		timingPointsParser =  new TimingPointsParser();
		coloursParser      =  new ColoursParser();
		hitObjectsParser   =  new HitObjectsParser(parsingBeatmap);
		
		addParser(PartGeneral.TAG      ,generalParser      );
		addParser(PartEditor.TAG       ,editorParser       );
		addParser(PartMetadata.TAG     ,metadataParser     );
		addParser(PartDifficulty.TAG   ,difficultyParser   );
		addParser(PartEvents.TAG       ,storyboardDecoder  );
		addParser(PartTimingPoints.TAG ,timingPointsParser );
		addParser(PartColours.TAG      ,coloursParser      );
		addParser(PartHitObjects.TAG   ,hitObjectsParser   );
		addParser(PartVariables.TAG    ,storyboardDecoder.variableDecoder);
	}
	
	@Override
	protected void onParse() throws NsoException,NsoBeatmapParsingException,IOException{
		super.onParse();
		boolean hasFindFormatLine=false;
		int f=0;
		while(true){
			nextLine();
			if(reader.hasEnd()){
				break;
			}
			f=parseFormatLine(reader.getNowString());
			if(f!=-1){
				bd.setVersion(f);
				setFormat(f);
				hasFindFormatLine=true;
				break;
			}
		}

		if(!hasFindFormatLine){
			throw new NsoBeatmapParsingException("format line NOT found",parsingBeatmap);
		}
	}

	@Override
	protected void onSelectParser(PartParser parser) throws NsoException, NsoBeatmapParsingException, IOException {
		// TODO: Implement this method
		super.onSelectParser(parser);
		if(parser==hitObjectsParser){
			hitObjectsParser.initial(((PartGeneral)(generalParser.getPart())).getMode());
		}
	}
	
	public OsuBeatmap makeupBeatmap(){
		switch(generalParser.getPart().getMode()){
			case ModeManager.MODE_STD:
				StdBeatmap stdbeatmap=new StdBeatmap();
				stdbeatmap.setVersion(getFormat());
				stdbeatmap.setGeneral(generalParser.getPart());
				stdbeatmap.setMetadata(metadataParser.getPart());
				stdbeatmap.setEditor(editorParser.getPart());
				stdbeatmap.setDifficulty(difficultyParser.getPart());
				stdbeatmap.setEvent(storyboardDecoder.getPart());
				stdbeatmap.setTimingPoints(timingPointsParser.getPart());
				stdbeatmap.setColours(coloursParser.getPart());
				stdbeatmap.setHitObjects(hitObjectsParser.getPart().getStdHitObjects());
				return stdbeatmap;
			default:return null;
		}
	}
	
	
	public <T extends OsuBeatmap> T makeupBeatmap(Class<T> klass){
		return (T)makeupBeatmap();
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		StringBuilder sb=new StringBuilder();
		sb.append(reparseFormatLine(getFormat())).append(U.NEXT_LINE).append(U.NEXT_LINE);
		appendPart(sb,generalParser.getPart()      );
		appendPart(sb,editorParser.getPart()       );
		appendPart(sb,metadataParser.getPart()     );
		appendPart(sb,difficultyParser.getPart()   );
		appendPart(sb,storyboardDecoder.getPart()       );
		appendPart(sb,timingPointsParser.getPart() );
		appendPart(sb,coloursParser.getPart()      );
		appendPart(sb,hitObjectsParser.getPart()   );
		return sb.toString();
	}
	
	public static void appendPart(StringBuilder sb,OsuFilePart part){
		sb.append(reparseTag(part.getTag())).append(U.NEXT_LINE);
		sb.append(part.makeString()).append(U.NEXT_LINE).append(U.NEXT_LINE);
	}
}
