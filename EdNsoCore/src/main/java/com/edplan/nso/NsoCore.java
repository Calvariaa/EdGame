package com.edplan.nso;
import java.util.HashMap;
import com.edplan.nso.ruleset.base.Ruleset;
import com.edplan.nso.ruleset.base.beatmap.BeatmapStorage;
import com.edplan.framework.MContext;

public class NsoCore
{
	private static NsoCore instance;
	
	private MContext context;
	
	private NsoConfig config;
	
	private HashMap<String,Ruleset> rulesets=new HashMap<String,Ruleset>();
	
	private HashMap<Class,Ruleset> class2ruleset=new HashMap<Class,Ruleset>();
	
	private BeatmapStorage beatmapStorage;
	
	private NsoCore(MContext context,NsoConfig conf){
		this.config=conf;
		this.context=context;
		this.beatmapStorage=new BeatmapStorage(this);
	}

	protected void setConfig(NsoConfig config){
		this.config=config;
	}

	public NsoConfig getConfig(){
		return config;
	}

	public void setContext(MContext context){
		this.context=context;
	}

	public MContext getContext(){
		return context;
	}
}
