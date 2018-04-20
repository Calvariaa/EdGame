package com.edplan.nso.filepart;
import com.edplan.nso.OsuFilePart;
import com.edplan.nso.beatmapComponent.Colours;
import android.graphics.Color;

public class PartColours implements OsuFilePart
{
	public static final String TAG="Colours";
	
	private Colours colours;
	
	public PartColours(){
		colours=new Colours();
	}
	
	public void addColour(int index,int c){
		colours.addColour(index,c);
	}
	
	public void addColour(int index,int r,int g,int b){
		colours.addColour(index,Color.argb(255,r,g,b));
	}
	
	@Override
	public String getTag(){
		// TODO: Implement this method
		return TAG;
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		return (colours!=null)?colours.makeString():"{@Colours}";
	}

}
