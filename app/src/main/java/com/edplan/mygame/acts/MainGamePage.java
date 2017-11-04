package com.edplan.mygame.acts;
import android.graphics.Color;
import android.util.Log;
import com.edplan.mygame.GameSurfaceView;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.Ruleset.std.objects.StdHitObject;
import com.edplan.nso.Ruleset.std.parser.StdHitObjectParser;
import com.edplan.nso.filepart.PartGeneral;
import com.edplan.nso.parser.StdBeatmapParser;
import com.edplan.simpleGame.view.BaseDatas;
import com.edplan.simpleGame.view.MButton;
import com.edplan.simpleGame.view.MFlatButton;
import com.edplan.simpleGame.view.MStaticViewGroup;
import com.edplan.simpleGame.view.MTextView;
import com.edplan.simpleGame.view.advance.text.CommandField;
import com.edplan.simpleGame.view.advance.widget.OsuBaseTriangleManager;
import com.edplan.simpleGame.view.advance.widget.OsuTriangleField;
import com.edplan.simpleGame.view.advance.widget.OsuTriangleManager;
import com.edplan.superutils.TestClock;
import com.edplan.superutils.U;
import java.io.File;
import java.util.Arrays;
import com.edplan.nso.osb.base.Origin;
import com.edplan.mygame.test.TestCursorField;
import com.edplan.simpleGame.MContext;

public class MainGamePage extends MStaticViewGroup
{
	public GameSurfaceView suface;
	
	public MainGamePage(MContext con,GameSurfaceView res){
		super(con);
		suface=res;
	}

	int sizedTimes=0;
	@Override
	public void sized(float w, float h){
		// TODO: Implement this method
		super.sized(w, h);
		sizedTimes++;
		if(sizedTimes<2){
			initialGame();
		}
	}

	CommandField cf;
	public void initialGame(){
		cf=new CommandField(getContext());
		cf.setWidth(getWidth()*0.9f).setHeight(getHeight());
		cf.setBasePoint(BaseDatas.dpToPixel(15),0);
		cf.addText("start command field",0xFF0077FF);
		cf.addText("------------------------------------------------------------------------------------------------");
		
		StdHitObjectParser ps=new StdHitObjectParser((new ParsingBeatmap()).setResInfo("text"));
		try{
			TestClock clock=new TestClock();
			StdHitObject h=ps.parse("326,175,974,2,0,P|329:239|392:290,1,128,2|2,0:0|0:0,0:0:0:0:");
			cf.addText(h.getClass().getCanonicalName());
			cf.addText(h.getStartX()+","+h.getStartY());
			cf.addText(StdBeatmapParser.parseFormatLine("jsjsjsnz")+"");
			
			
			
			cf.addText(StdBeatmapParser.parseFormatLine("osu file format v14")+"");
			
			cf.addText(StdBeatmapParser.parseTag("[wwwbb]")+"|");
			cf.addText(StdBeatmapParser.parseTag("[wwwbb ] ")+"|");
			
			cf.addText("------------------------------------------------------------------------------------------------");
			
			StdBeatmapParser p=new StdBeatmapParser(new File("/storage/emulated/0/MyDisk/WorkBench/data/test beatmaps/mania/Primary - in the Garden (Mat) [Julie's 4K Hard].osu"));
			//"/storage/emulated/0/MyDisk/WorkBench/data/test beatmaps/std/Petit Rabbit's - No Poi! (walaowey) [Insane].osu"));
			clock.start();
			p.parse();
			clock.end();
			cf.addText(clock.getTime()+"");
			cf.addText(((PartGeneral)(p.getGeneralParser().getPart())).getAudioFilename());
			clock.start();
			cf.addText(p.makeString());
			clock.end();
			cf.addText(clock.getTime()+"");
			cf.addText("------------------------------------------------------------------------------------------------");
			String l="AudioFilename: No Poi!.mp3";
			String[] entry=U.divide(l,l.indexOf(":"));
			cf.addText(Arrays.toString(entry));
			//cf.addText(Origin.valueOf("@ssg").toString());
			
			
			/*
			String testString="osu file format v14";
			int tmp=0;
			clock.start();
			for(int i=0;i<100000;i++){
				testString.startsWith("osu file format v");
				tmp=Integer.parseInt(testString.substring(17,testString.length()));
				//StdBeatmapParser.isFormatLine(testString);
			}
			clock.end();
			
			cf.addText(clock.getTime()+"");
			
			System.gc();
			
			clock.start();
			for(int i=0;i<100000;i++){
				//testString.startsWith("osu file format v");
				//StdBeatmapParser.isFormatLine(testString);
			}
			clock.end();

			cf.addText(clock.getTime()+"");
			*/
			
		}
		catch(Exception e){
			Log.e("parsing","nnn",e);
			cf.addText(e.getClass().getCanonicalName()+" Msg: "+e.getMessage()+" Class: "+e.getClass().getCanonicalName()); 
			for(StackTraceElement el:e.getStackTrace()){
				cf.addText("---"+el.toString());
			}
		}

		
		/*
		Thread t=new Thread(new Runnable(){
				@Override
				public void run(){
					// TODO: Implement this method
					int index=0;
					while(true)
					try{
						Thread.currentThread().sleep(800);
						synchronized(cf){
							cf.addText(index+"| add text : "+System.currentTimeMillis());
							index++;
						}
					}
					catch(InterruptedException e){}
				}
			});
		
		t.start();
		*/
		
		TestCursorField tcf=new TestCursorField(getContext());
		tcf.setHeight(getHeight());
		tcf.setWidth(getWidth());
		setUpTriangleBackground();
		add(cf);
		add(tcf);
		/*
		setUpTitleField();
		setUpMainButtonField();*/
	}
	
	MStaticViewGroup dataBar;
	MFlatButton barBackground;
		MTextView medalCount;
		MTextView crystalCount;

	public void setUpDataBar(){
		
	}
	
	OsuTriangleManager otm;
	OsuTriangleField otf;
	
	public void setUpTriangleBackground(){
		otm=new OsuBaseTriangleManager();
		otf=new OsuTriangleField(getContext(),otm);
		otf.setHeight(getHeight());
		otf.setWidth(getWidth());
		
		for(int i=0;i<50;i++){
			otm.add();
		}
		
		add(otf);
	}
	
	MStaticViewGroup titleField;
	MFlatButton titleBackground;
		MTextView titleTextView;
	
	public void setUpTitleField(){
		titleField=new MStaticViewGroup(getContext());
		titleBackground=new MFlatButton(getContext());
			titleTextView=new MTextView(getContext());
	}

	MStaticViewGroup mainButtonField;
	MButton fieldBackground;
		MButton startPlayButton;
		MButton backpackButton;
		MButton storeButton;
		MButton settingButton;
	
	public void setUpMainButtonField(){
		mainButtonField=new MStaticViewGroup(getContext());
		fieldBackground=new MButton(getContext());
		startPlayButton=new MButton(getContext());
		backpackButton=new MButton(getContext());
		storeButton=new MButton(getContext());
		settingButton=new MButton(getContext());

		float w1=getWidth()*0.3f;
		float h1=getHeight()*0.08f;
		float px=w1*0.05f;
		float py=h1*0.15f;
		int ba=140;
		int bc=Color.argb(255,0,198,254);
		startPlayButton
			.setColor(bc)
			.setIgnoreTextAlpha(true)
			.setAlpha(ba)
			.setText("⚔️PLAY️")
			.setBasePoint(px,py)
			.setWidth(w1)
			.setHeight(h1);
		backpackButton
			.setColor(bc)
			.setIgnoreTextAlpha(true)
			.setAlpha(ba)
			.setText("📦背包")
			.setBasePoint(px,startPlayButton.getBottom())
			.setWidth(w1)
			.setHeight(h1);
		storeButton
			.setColor(bc)
			.setIgnoreTextAlpha(true)
			.setAlpha(ba)
			.setText("🛒商店")
			.setBasePoint(px,backpackButton.getBottom())
			.setWidth(w1)
			.setHeight(h1);
		settingButton
			.setColor(bc)
			.setIgnoreTextAlpha(true)
			.setAlpha(ba)
			.setText("⚙️设置")
			.setBasePoint(px,storeButton.getBottom())
			.setWidth(w1)
			.setHeight(h1);
		float fw=w1*1.1f;
		float fh=settingButton.getBottom()+py;
		fieldBackground
			.setColor(Color.argb(30,255,255,255))
			.setBasePoint(0,0)
			.setWidth(fw)
			.setHeight(fh);

		mainButtonField
			.setWidth(fw)
			.setHeight(fh)
			.setCenter(getWidth()/2,getHeight()*(0.45f));

		mainButtonField
			.add(fieldBackground)
			.add(startPlayButton,backpackButton,storeButton,settingButton);
		add(mainButtonField);
	}
	
	
	
	public void setSuface(GameSurfaceView suface){
		this.suface=suface;
	}

	public GameSurfaceView getSuface(){
		return suface;
	}
}
