package com.edplan.nso.beatmapComponent;
import java.util.ArrayList;
import com.edplan.superutils.interfaces.StringMakeable;
import android.graphics.Color;
import com.edplan.superutils.U;
import java.util.TreeMap;
import java.util.Map;

public class Colours implements StringMakeable
{
	private TreeMap<Integer,Integer> colours;
	
	public Colours(){
		colours=new TreeMap<Integer,Integer>();
	}
	
	public void addColour(int index,int c){
		colours.put(index,c);
	}

	@Override
	public String makeString(){
		// TODO: Implement this method
		StringBuilder sb=new StringBuilder();
		int i;
		for(Map.Entry<Integer,Integer> e:colours.entrySet()){
			i=e.getValue();
			sb.append("Combo").append(e.getKey()).append(" : ");
			sb.append(Color.red(i)).append(",");
			sb.append(Color.green(i)).append(",");
			sb.append(Color.blue(i)).append(U.NEXT_LINE);
		}
		return sb.toString();
	}
}
