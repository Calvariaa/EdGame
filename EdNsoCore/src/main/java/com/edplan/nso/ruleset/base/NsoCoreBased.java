package com.edplan.nso.ruleset.base;
import com.edplan.nso.NsoCore;

public class NsoCoreBased
{
	private NsoCore core;
	
	public NsoCoreBased(NsoCore core){
		this.core=core;
	}

	public NsoCore getCore(){
		return core;
	}
}
