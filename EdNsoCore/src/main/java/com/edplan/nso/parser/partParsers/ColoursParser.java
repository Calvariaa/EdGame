package com.edplan.nso.parser.partParsers;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.filepart.PartColours;
import com.edplan.superutils.U;

public class ColoursParser extends PartParser<PartColours>
{
	public static String COLOUR_HEAD="Combo";
	
	private PartColours part;
	
	public ColoursParser(){
		part=new PartColours();
	}
	
	@Override
	public PartColours getPart(){
		// TODO: Implement this method
		return part;
	}
	
	@Override
	public boolean parse(String l){
		// TODO: Implement this method
		l=l.trim();
		if(l.isEmpty()){
			return true;
		}else{
			String[] ls=U.divide(l,l.indexOf(":"));
			if(ls[0].startsWith(COLOUR_HEAD)){
				int index=U.toInt(ls[0].substring(COLOUR_HEAD.length(),ls[0].length()));
				String[] rgb=ls[1].split(",");
				part.addColour(index,U.toInt(rgb[0]),U.toInt(rgb[1]),U.toInt(rgb[2]));
				return true;
			}else{
				return false;
			}
		}
	}
}
