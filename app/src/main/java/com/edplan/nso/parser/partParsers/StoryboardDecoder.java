package com.edplan.nso.parser.partParsers;

import com.edplan.framework.math.Vec2;
import com.edplan.nso.NsoException;
import com.edplan.nso.filepart.PartEvents;
import com.edplan.nso.filepart.PartVariables;
import com.edplan.nso.storyboard.Storyboard;
import com.edplan.nso.storyboard.elements.CommandTimeLineGroup;
import com.edplan.nso.storyboard.elements.StoryboardSprite;
import com.edplan.superutils.U;
import com.edplan.framework.ui.animation.LoopType;
import com.edplan.nso.storyboard.elements.StoryboardAnimation;

public class StoryboardDecoder extends PartParser<PartEvents>
{
	private Storyboard storyboard=new Storyboard();
	
	public VariablesDecoder variableDecoder;
	
	private PartVariables variables;
	
	private PartEvents partEvents=new PartEvents();
	
	private StoryboardSprite storyboardSprite;
	private CommandTimeLineGroup timelineGroup;
	
	public StoryboardDecoder(){
		variableDecoder=new VariablesDecoder();
		variables=variableDecoder.getPart();
	}
	
	private boolean handleEvents(String l,int depth) throws NsoException{
		String[] spl=l.split(",");
		if(depth==0){
			storyboardSprite=null;
			Storyboard.EventObjType eventType=Storyboard.EventObjType.parse(spl[0]);
			if(eventType==null){
				setErrMessage("unknow eventType: "+spl[0]);
				return false;
			}
			switch(eventType){
				case Sprite:
				{
					Storyboard.Layer layer=parseLayer(spl[1]);
					Storyboard.Origin origin=parseOrigin(spl[2]);
					String path=cleanFilename(spl[3]);
					float x=Float.parseFloat(spl[4]);
					float y=Float.parseFloat(spl[5]);
					storyboardSprite=new StoryboardSprite(path,origin.getAnchor(),new Vec2(x,y));
					storyboard.getLayer(layer.name()).add(storyboardSprite);
				}
					break;
				case Animation:
				{
					Storyboard.Layer layer=parseLayer(spl[1]);
					Storyboard.Origin origin=parseOrigin(spl[2]);
					String path=cleanFilename(spl[3]);
					float x=Float.parseFloat(spl[4]);
					float y=Float.parseFloat(spl[5]);
					int frameCount=Integer.parseInt(spl[6]);
					double frameDelay=Double.parseDouble(spl[7]);
					Storyboard.AnimationLoopType loopType=(spl.length>8)?Storyboard.AnimationLoopType.valueOf(spl[8]):Storyboard.AnimationLoopType.LoopForever;
					storyboardSprite=new StoryboardAnimation(path,origin.getAnchor(),new Vec2(x,y),frameCount,frameDelay,loopType);
					storyboard.getLayer(layer.name()).add(storyboardSprite);
				}
					break;
				case Sample:
				{
					
				}
					break;
			}
		}else{
			
		}
		return true;
	}
	
	private Storyboard.Layer parseLayer(String value){
		return Storyboard.Layer.parse(value);
	}
	
	private Storyboard.Origin parseOrigin(String value){
		return Storyboard.Origin.parse(value);
	}
	
	private String cleanFilename(String s) throws NsoException{
		if(s.length()>=2&&s.charAt(0)=='"'&&s.charAt(s.length()-1)=='"'){
			return s.substring(1,s.length()-1);
		}else{
			//setErrMessage("err filename : "+s);
			throw new NsoException("err filename : "+s);
		}
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
			//支持使用变量
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
