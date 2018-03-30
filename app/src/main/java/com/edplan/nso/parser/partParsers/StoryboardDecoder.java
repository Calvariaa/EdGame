package com.edplan.nso.parser.partParsers;

import com.edplan.nso.NsoException;
import com.edplan.nso.filepart.PartVariables;
import com.edplan.superutils.U;
import com.edplan.nso.filepart.PartEvents;

public class StoryboardDecoder extends PartParser<PartEvents>
{
	public VariablesDecoder variableDecoder;
	
	private PartVariables variables;
	
	private PartEvents partEvents=new PartEvents();
	
	public StoryboardDecoder(){
		variableDecoder=new VariablesDecoder();
		variables=variableDecoder.getPart();
	}
	
	private boolean handleEvents(String l,int depth){
		return true;
	}
	
	private static final int BLOCK_LOOP_COUNT=20;

	@Override
	public boolean parse(String l) throws NsoException {
		// TODO: Implement this method
		if(l.trim().isEmpty()||l.startsWith("//")){
			//在Storyboard部分，支持//注释方法（仅限于以//开头的行）
			return true;
		}else{
			int depth=0;
			while(l.charAt(0)==' '||l.charAt(0)=='_'){
				depth++;
				l=l.substring(1);
			}
			int loops=0;
			while(l.indexOf('$')>=0){
				String origin=l;
				String[] spl=l.split("'");
				for(int i=0;i<spl.length;i++){
					String item=spl[i];
					if(item.startsWith("$")&&(item=variables.getVariable(item))!=null){
						spl[i]=item;
					}
				}
				l=U.join(spl,",");
				if(l.equals(origin)){
					break;
				}
				loops++;
				if(loops>BLOCK_LOOP_COUNT){
					setErrMessage("MAYBE A ENDLESS LOOP! loops="+loops);
					return false;
				}
			}
			return handleEvents(l,depth);
		}
	}

	@Override
	public PartEvents getPart() {
		// TODO: Implement this method
		return partEvents;
	}
	
	public static class VariablesDecoder extends PartParser<PartVariables>
	{
		private PartVariables part=new PartVariables();

		@Override
		public boolean parse(String l) throws NsoException {
			// TODO: Implement this method
			String[] spl=U.divide(l,l.indexOf('='));
			if(spl==null){
				return false;
			}
			part.addVariable(spl[0],spl[1]);
			return true;
		}

		@Override
		public PartVariables getPart() {
			// TODO: Implement this method
			return part;
		}
	}
}
