package com.edplan.nso.parser.partParsers;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.filepart.PartEvents;

public class EventsParser implements PartParser
{
	private PartEvents events;
	
	public EventsParser(){
		events=new PartEvents();
	}
	
	@Override
	public boolean parse(String l){
		// TODO: Implement this method
		return true;
	}

	@Override
	public OsuFilePart getPart(){
		// TODO: Implement this method
		return events;
	}

	@Override
	public void applyDefault(){
		// TODO: Implement this method
	}
}
