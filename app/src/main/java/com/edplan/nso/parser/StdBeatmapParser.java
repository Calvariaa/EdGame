package com.edplan.nso.parser;
import android.util.Log;
import com.edplan.nso.beatmap.StdBeatmapBindingData;
import com.edplan.nso.NsoBeatmapParsingException;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.filepart.PartColours;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.nso.filepart.PartEditor;
import com.edplan.nso.filepart.PartEvents;
import com.edplan.nso.filepart.PartGeneral;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.filepart.PartMetadata;
import com.edplan.nso.filepart.PartTimingPoints;
import com.edplan.nso.parser.partParsers.ColoursParser;
import com.edplan.nso.parser.partParsers.DifficultyParser;
import com.edplan.nso.parser.partParsers.EditorParser;
import com.edplan.nso.parser.partParsers.EventsParser;
import com.edplan.nso.parser.partParsers.GeneralParser;
import com.edplan.nso.parser.partParsers.HitObjectsParser;
import com.edplan.nso.parser.partParsers.MetadataParser;
import com.edplan.nso.parser.partParsers.PartParser;
import com.edplan.nso.parser.partParsers.TimingPointsParser;
import com.edplan.superutils.AdvancedBufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import com.edplan.nso.NsoException;
import com.edplan.superutils.interfaces.StringMakeable;
import com.edplan.nso.OsuFilePart;
import com.edplan.superutils.U;

public class StdBeatmapParser implements StringMakeable
{
	public static String FORMAT_LINE_HEAD="osu file format v";
	
	private boolean parseable;
	
	private StdBeatmapBindingData bd;
	
	private int format;
	
	private AdvancedBufferedReader reader;
	
	private ParsingBeatmap parsingBeatmap;
	
	private GeneralParser generalParser;
	
	private EditorParser editorParser;
	
	private MetadataParser metadataParser;
	
	private DifficultyParser difficultyParser;
	
	private EventsParser eventsParser;
	
	private TimingPointsParser timingPointsParser;
	
	private ColoursParser coloursParser;
	
	private HitObjectsParser hitObjectsParser;
	
	private Map<String,PartParser> parsers;
	
	private PartParser nowParser=null;
	
	public StdBeatmapParser(File file) throws FileNotFoundException{
		AdvancedBufferedReader br=new AdvancedBufferedReader(file);
		ParsingBeatmap pb=new ParsingBeatmap();
		pb.setResInfo("file://"+file.getAbsolutePath());
		setParseable(true);
		initial(br,pb);
	}
	
	public StdBeatmapParser(AdvancedBufferedReader _reader,ParsingBeatmap bdmsg){
		setParseable(true);
		initial(_reader,bdmsg);
	}

	public void setFormat(int format){
		this.format=format;
	}

	public int getFormat(){
		return format;
	}

	public void setParseable(boolean parseable){
		this.parseable=parseable;
	}

	public boolean isParseable(){
		return parseable;
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

	public void setEventsParser(EventsParser eventsParser){
		this.eventsParser=eventsParser;
	}

	public EventsParser getEventsParser(){
		return eventsParser;
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
	
	public void initial(AdvancedBufferedReader _reader,ParsingBeatmap bdmsg){
		this.reader=_reader;
		this.parsingBeatmap=bdmsg;
		bd=new StdBeatmapBindingData();
		
		generalParser      =  new GeneralParser();
		editorParser       =  new EditorParser();
		metadataParser     =  new MetadataParser();
		difficultyParser   =  new DifficultyParser();
		eventsParser       =  new EventsParser();
		timingPointsParser =  new TimingPointsParser();
		coloursParser      =  new ColoursParser();
		hitObjectsParser   =  new HitObjectsParser(parsingBeatmap);
		
		parsers=new TreeMap<String,PartParser>();
		
		
		parsers.put(PartGeneral.TAG      ,generalParser      );
		parsers.put(PartEditor.TAG       ,editorParser       );
		parsers.put(PartMetadata.TAG     ,metadataParser     );
		parsers.put(PartDifficulty.TAG   ,difficultyParser   );
		parsers.put(PartEvents.TAG        ,eventsParser       );
		parsers.put(PartTimingPoints.TAG ,timingPointsParser );
		parsers.put(PartColours.TAG      ,coloursParser      );
		parsers.put(PartHitObjects.TAG   ,hitObjectsParser   );
	}
	
	public void parse() throws IOException, NsoBeatmapParsingException, NsoException{
		
		boolean hasFindFormatLine=false;
		int f=0;
		while(true){
			nextLine();
			if(reader.hasEnd()){
				break;
			}
			f=parseFormatLine(reader.bufferedString());
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
		String tagTmp=null;
		while(true){
			nextLine();
			if(reader.hasEnd()){
				break;
			}
			tagTmp=parseTag(reader.bufferedString());
			if(tagTmp!=null){
				nowParser=parsers.get(tagTmp);
				if(nowParser==null){
					throw new NsoBeatmapParsingException("Invalid tag : "+tagTmp,parsingBeatmap);
				}
				if(nowParser==hitObjectsParser){
					hitObjectsParser.initial(((PartGeneral)(generalParser.getPart())).getMode());
				}
			}else{
				if(nowParser!=null){
					try{
						if(!nowParser.parse(reader.bufferedString())){
							throw new NsoBeatmapParsingException("Parse line err: "+reader.bufferedString(), parsingBeatmap);
						}
					}
					catch(NsoException e){
						throw e;
					}
				}
			} 
		}
	}
	
	private void nextLine() throws IOException{
		reader.readLine();
		parsingBeatmap.nextLine();
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
		appendPart(sb,eventsParser.getPart()       );
		appendPart(sb,timingPointsParser.getPart() );
		appendPart(sb,coloursParser.getPart()      );
		appendPart(sb,hitObjectsParser.getPart()   );
		return sb.toString();
	}
	
	public static void appendPart(StringBuilder sb,OsuFilePart part){
		sb.append(reparseTag(part.getTag())).append(U.NEXT_LINE);
		sb.append(part.makeString()).append(U.NEXT_LINE).append(U.NEXT_LINE);
	}
	
	public static String reparseTag(String t){
		StringBuilder sb=new StringBuilder("[");
		sb.append(t).append("]");
		return sb.toString();
	}
	
	public static String parseTag(String s){
		s=s.trim();
		if(s.isEmpty()){
			return null;
		}else{
			if(s.charAt(0)=='['&&s.charAt(s.length()-1)==']'){
				return s.substring(1,s.length()-1);
			}else{
				return null;
			}
		}
	}
	
	public static String reparseFormatLine(int f){
		return (new StringBuilder(FORMAT_LINE_HEAD)).append(f).toString();
	}
	
	public static int parseFormatLine(String s){
		if(s.startsWith(FORMAT_LINE_HEAD)){
			try{
				return Integer.parseInt(s.substring(FORMAT_LINE_HEAD.length(),s.length()));
			}catch(NumberFormatException e){
				Log.w("parsing format line",e.getMessage());
				return -1;
			}
		}else{
			return -1;
		}
	}
	
}
