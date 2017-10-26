package com.edplan.superutils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class AdvancedBufferedReader
{
	protected BufferedReader reader;
	
	protected String bufferedString=null;
	protected boolean hasEnd=false;
	protected int lineCount=0;
	
	public AdvancedBufferedReader(BufferedReader r){
		reader=r;
	}
	
	public AdvancedBufferedReader(File f) throws FileNotFoundException{
		this(new FileReader(f));
	}
	
	public AdvancedBufferedReader(InputStream in){
		this(new InputStreamReader(in));
	}
	
	public AdvancedBufferedReader(Reader r){
		this(new BufferedReader(r));
	}
	
	public int getLineCount(){
		return lineCount;
	}
	
	public String readLine() throws IOException{
		if(hasEnd())return null;
		bufferedString=reader.readLine();
		if(bufferedString==null){
			hasEnd=true;
		}else{
			lineCount++;
		}
		return bufferedString;
	}
	
	public String bufferedString(){
		return bufferedString;
	}
	
	public boolean hasEnd(){
		return hasEnd;
	}
	
	
}
