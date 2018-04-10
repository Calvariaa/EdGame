package com.edplan.framework.test;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import com.edplan.framework.MContext;
import com.edplan.framework.audio.bass.BassChannel;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.line.DrawLinePath;
import com.edplan.framework.graphics.line.LinePath;
import com.edplan.framework.graphics.line.PathMeasurer;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.resource.AResource;
import com.edplan.framework.timing.AudioTimeline;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.animation.AbstractAnimation;
import com.edplan.framework.ui.animation.AnimState;
import com.edplan.framework.ui.animation.LoopType;
import com.edplan.framework.ui.drawable.BitmapDrawable;
import com.edplan.framework.ui.text.font.bmfont.BMFont;
import com.edplan.framework.ui.text.font.drawing.TextPrinter;
import com.edplan.framework.utils.MLog;
import com.edplan.nso.NsoException;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.parser.StdBeatmapParser;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.nso.ruleset.amodel.playing.PlayField;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.nso.ruleset.std.objects.StdSlider;
import com.edplan.nso.ruleset.std.objects.drawables.StdSliderPathMaker;
import com.edplan.nso.ruleset.std.parser.StdHitObjectParser;
import com.edplan.nso.ruleset.std.playing.StdPlayField;
import com.edplan.nso.ruleset.std.playing.StdPlayingBeatmap;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitCircle;
import java.io.IOException;
import com.edplan.framework.timing.AdjustableTimeline;

public class TestView extends EdView
{
	
	private StdPlayField playField;
	
	private StdBeatmap beatmap;

	private StdPlayingBeatmap playingBeatmap;

	private PreciseTimeline timeline;
	
	private BassChannel audio;
	
	private OsuSkin skin;
	
	private Vec2 point=new Vec2();
	
	//private DrawableStdSlider sld2;
	
	private BufferedLayer newLayer;
	
	
	private GLTexture testPng;
	private GLTexture cardPng;
	private GLTexture sliderTex;
	
	private DrawableStdHitCircle firstObj;
	
	float a=0;
	long lt=0;
	
	int delt=0;
	
	BMFont font;
	
	public TestView(MContext context){
		super(context);
		
		try {
			AResource res=getContext()
				.getAssetResource()
				.subResource("font");
			font=BMFont.loadFont(
						res,
			//			"Exo2.0-Regular.fnt");
			
						"Noto-CJK-Basic.fnt");
			//font.setErrCharacter(BMFont.CHAR_NOT_FOUND);
			font.addFont(res,"Noto-Basic.fnt");
			font.setErrCharacter('⊙');
		} catch (IOException e) {
			e.printStackTrace();
		}

		TestData test=new TestData();
		System.out.println(test.getBeatmapPath());
		System.out.println(test.getSongPath());
		
		try {
			skin=new OsuSkin();
			skin.load(
				getContext()
				 .getAssetResource()
				  .subResource("osu")
				   .subResource("skins")
				    .subResource("default"));
			
			StdBeatmapParser bparser=new StdBeatmapParser(
												getContext()
												 .getAssetResource()
												  .subResource("osu/test/beatmap").openInput(
			//"t+pazolite with siromaru - Chambarising (Bloodmoon) [Stream Practice (160)].osu"
			//"Suzuki Konomi - Cyber Thunder Cider (Nattu) [Niat's Cider].osu"
			//"Petit Rabbit's - No Poi! (walaowey) [[ -Scarlet- ]'s Extra].osu"
			//"UNDEAD CORPORATION - The Empress (Plutia) [STARBOW BREAK!].osu"
			//"Mai Zang - Si Ye Cao De Huang Xiang (Axarious) [Despair].osu"
			//"Osu!Droid Tieba Challenge - Evolution Pack (Believer_zzr) [Future Cider].osu"
			//"Petit Rabbit's - No Poi! (walaowey) [[ -Scarlet- ]'s Extra].osu"
			//"Yueporu feat. Hatsune Miku - Kurikaeshi Hitotsubu (Zweib) [Hitotsubu].osu"
			//"1/Chino (CV.Minase Inori) - Okashina yume o Ohitotsu douzo (- Skanbis -) [Asuka_-'s insane].osu"
			//"2/Halozy - Kikoku Doukoku Jigokuraku (Hollow Wings) [Notch Hell].osu"
			//"3/IAHN - Transform (Original Mix) (Monstrata) [Aspire].osu"
			//"4/IOSYS - Marisa wa Taihen na Mono wo Nusunde Ikimashita (DJPop) [TAG4].osu"
			//"5/Luo Tianyi - Xiao Ji Bibi ~ Remix ~ (MoleAkarin) [Bi~bi~].osu"
			//"6/xi - FREEDOM DiVE (Nakagawa-Kanon) [FOUR DIMENSIONS].osu"
			//"7/Panda Eyes & Teminite - Highscore (Fort) [Game Over].osu"
			//"8/Helblinde - The Solace of Oblivion (ProfessionalBox) [Aspire].osu"
			//"9/Knife Party - Centipede (Sugoi-_-Desu) [This isn't a map, just a simple visualisation].osu"
			test.getBeatmapPath()
												   ),
												"test case beatmap: " 
												 );
												 
			audio=BassChannel//.createStreamFromResource(getContext().getAssetResource(),"osu/test/beatmap/"+
			.createStreamFromAsset(getContext(),"osu/test/beatmap/"+
			//"test.mp3"
			//"cloverfantasy.mp3"
			//"audio.mp3"
			//"hust.mp3"
			//"No Poi!.mp3"
			//"1/audio.mp3"
			//"2/Kikoku Doukoku Jigokuraku.mp3"
			//"3/audio.mp3"
			//"4/Marisa wa Taihen na Mono wo Nusunde Ikimashita.mp3"
			//"5/bibibi.mp3"
			//"6/Freedom Dive.mp3"
			//"7/Teminite & Panda Eyes - Highscore.mp3"
			//"8/audio.mp3"
			//"9/02-knife_party-centipede.mp3"
			test.getSongPath()
			);
			try
			{
				Log.v("parse-osu","start parse");
				bparser.parse();
				Log.v("parse-osu","end parse");
				beatmap=bparser.makeupBeatmap(StdBeatmap.class);
				beatmap.getDifficulty().setCircleSize(beatmap.getDifficulty().getCircleSize());
				timeline=new AudioTimeline(audio);
				//new PreciseTimeline();
				//new AdjustableTimeline(1f);
				playingBeatmap=new StdPlayingBeatmap(getContext(),beatmap,timeline,skin);
				Log.v("osu","objs: "+playingBeatmap.getHitObjects().size()+" first: "+playingBeatmap.getHitObjects().get(0).getStartTime());
				playField=new StdPlayField(getContext(),timeline);
				playField.applyBeatmap(playingBeatmap);
			}
			catch (NsoException e)
			{
				e.printStackTrace();
				throw new RuntimeException("err",e);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			
			testPng=skin.hitcircle.getRes();
			/*
				GLTexture.decodeStream(
				context
				.getAssetResource()
				.getTextureResource()
				.openInput
				("ic_launcher.png"));*/
			cardPng=GLTexture.decodeResource(context.getAssetResource().getTextureResource(),
											 "card.jpg");
			//"default-bg.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	boolean ifend=false;
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		super.draw(canvas);
		
		int audioOffset=(int)(timeline.frameTime()-audio.currentPlayTimeMS());
		
		a+=0.005;
		float c=Math.abs(a%2-1);
		
		MLog.test.runOnce("test-play", new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					post(new Runnable(){
							@Override
							public void run()
							{
								// TODO: Implement this method
								getContext().getUiLooper().addLoopableBeforeDraw(timeline);
								audio.play();
							}
					},2000);
				}
			});
		
		
		
		canvas.drawColor(Color4.gray(0.4f));
		//new Color4(c,c,c,1.0f));
		canvas.clearDepthBuffer();
		 
		float dh=50;
		GLPaint paint=new GLPaint();
		//paint.setColorMixRate(1f);
		paint.setFinalAlpha(1f);
		paint.setMixColor(new Color4(1,1,1,1));
		paint.setPadding(10);
		paint.setVaryingColor(new Color4(1,1,1,1));
		paint.setRoundedRadius(30);
		paint.setGlowFactor
		//(0.1f);
		(0.9f+c*0.3f*0);
		paint.setGlowColor(Color4.rgba(0.3f,0.3f,0.3f,0.9f));
		lt=System.currentTimeMillis();

		
		//c=0.3f;


		//转换到osu field

		canvas.save();
		
		
		if(newLayer==null)newLayer=new BufferedLayer(getContext(),canvas.getLayer().getWidth(),canvas.getLayer().getHeight(),true);
		GLCanvas2D newCanvas=new GLCanvas2D(newLayer);
		newCanvas.prepare();
		
		newCanvas.drawColor(Color4.rgba(0,0,0,0));
		newCanvas.clearDepthBuffer();
		
		float osuScale=PlayField.BASE_Y/canvas.getHeight();
		newCanvas.translate(newCanvas.getWidth()/2-PlayField.BASE_X/2/osuScale,0);
		newCanvas.scaleContent(osuScale);
		newCanvas.translate(PlayField.PADDING_X,PlayField.PADDING_Y);
		newCanvas.clip(new Vec2(PlayField.CANVAS_SIZE_X,PlayField.CANVAS_SIZE_Y));
		
		GLPaint testLine=new GLPaint();
		testLine.setMixColor(Color4.rgba(0,0,1,0.5f));
		testLine.setStrokeWidth(2);
		newCanvas.drawLine(0,0,newCanvas.getWidth(),newCanvas.getHeight(),testLine);
		newCanvas.drawLine(newCanvas.getWidth(),0,0,newCanvas.getHeight(),testLine);
		newCanvas.drawLine(0,0,newCanvas.getWidth(),0,testLine);
		newCanvas.drawLine(newCanvas.getWidth(),0,newCanvas.getWidth(),newCanvas.getHeight(),testLine);
		newCanvas.drawLine(0,0,0,newCanvas.getHeight(),testLine);
		newCanvas.drawLine(0,newCanvas.getHeight(),newCanvas.getWidth(),newCanvas.getHeight(),testLine);
		//paint.setMixColor(Color4.rgb(1,0.5f,0.5f));
		
		GLPaint tp=new GLPaint();
		tp.setVaryingColor(Color4.rgb(1,1,1));
		//tp.setColorMixRate(1);
		tp.setStrokeWidth(5);
		tp.setFinalAlpha(1);
		//newCanvas.drawLines(new float[]{0,0,PlayField.CANVAS_SIZE_X,PlayField.CANVAS_SIZE_Y},tp);
		
		playField.draw(newCanvas);
		//firstObj.draw(newCanvas);
		newCanvas.unprepare();

		//paint.setColorMixRate(0);
		
		GLPaint newPaint=new GLPaint();
		//newPaint.setFinalAlpha(postAlpha);
		//newPaint.setMixColor(new Color4(1,0.5f,0.5f,1));
		AbstractTexture texture=newLayer.getTexture();
		//canvas.drawTexture(texture,new RectF(0,0,texture.getWidth(),texture.getHeight()),new RectF(0,0,canvas.getWidth(),canvas.getHeight()),newPaint);
		//canvas.drawTextureAnchorCenter(testPng,new Vec2(canvas.getWidth()/2,canvas.getHeight()/2),new Vec2(canvas.getWidth()/2,canvas.getHeight()/2),newPaint);
		canvas.drawTextureAnchorCenter(texture,new Vec2(canvas.getWidth()/2,canvas.getHeight()/2),new Vec2(canvas.getWidth()/2,canvas.getHeight()/2),newPaint);
		//canvas.drawTexture(GLTexture.Black,RectF.xywh(0,0,canvas.getWidth(),canvas.getHeight()),newPaint);
		//newLayer.recycle();
		
		canvas.restore();

		paint.setFinalAlpha(0.5f);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,80,80),
			new RectF(point.x-40,point.y-40,80,80),
			paint);
		canvas.save();
		canvas.translate(500+testPng.getWidth()/2,500+testPng.getHeight()/2);
		//.post((new Mat4()).translate(10,10,0));

		//canvas.drawColor(Color4.gray(0.5f));
		/*
		canvas.drawTexture(
			testPng,
			new RectF(0,0,testPng.getWidth(),testPng.getHeight()),
			new RectF(-testPng.getWidth()/2,-testPng.getHeight()/2,testPng.getWidth(),testPng.getHeight()),
			new Color4(1,1,1,1),
			new Color4(1,1,1,1),
			0f,
			1,
			1f);*/

		canvas.restore();
		
		/*
		GLPaint fp=new GLPaint();
		fp.setMixColor(Color4.gray(1));
		fp.setStrokeWidth(50);
		canvas.drawLines(new float[]{0,0,500,500},fp);*/
		
		/*
		GLPaint fp=new GLPaint();
		fp.setColorMixRate(1);
		fp.setMixColor(Color4.gray(1));
		fp.setStrokeWidth(50);
		canvas.drawLines(new float[]{0,0,500,500},fp);*/
		/*
		long ct=System.currentTimeMillis();
		for(BitmapDrawable bmd:bmds){
			//bmd.text="fps: "+1000f/getContext().getFrameDeltaTime();
			bmd.deltaTime=delt;
			bmd.draw(canvas);
			//break;
		}
		delt=(int)(System.currentTimeMillis()-ct);*/
		float deltaTime=(float)getContext().getFrameDeltaTime();
		GLPaint ntp=new GLPaint();
		ntp.setStrokeWidth(1.2f);
		//ntp.setColorMixRate(1);
		ntp.setMixColor(Color4.White);
		canvas.save();
		//canvas.clipRect(c.getWidth()-200,c.getHeight()-600,c.getWidth(),c.getHeight());
		//canvas.translate();
		//canvas.getWidth()-200,canvas.getHeight()-600);
		//c.drawText("DeltaTime: "+deltaTime,10,30,tp);
		float lengthScale=5;
		ntp.setMixColor(Color4.rgba(0,1,0,1));
		canvas.drawLine(10,50,10+18*6,50,ntp);
		ntp.setMixColor(Color4.rgba(1,1,0,1));
		canvas.drawLine(10,55,10+deltaTime*lengthScale,55,ntp);
		ntp.setMixColor(Color4.White);
		for(int i=timelist.length-1;i>0;i--){
			timelist[i]=timelist[i-1];
		}
		timelist[0]=deltaTime;
		for(int i=1;i<timelist.length;i++){
			canvas.drawLine(10,55+6*i,10+timelist[i]*lengthScale,55+6*i,ntp);
		}
		float avg=0;
		float max=0;
		float min=99999;
		for(float t:timelist){
			avg+=t;
			if(t<min){
				min=t;
			}
			if(t>max){
				max=t;
			}
		}
		avg/=timelist.length;
		ntp.setStrokeWidth(3);
		ntp.setMixColor(Color4.rgba(1,0,0,1));
		canvas.drawLine(10+avg*lengthScale,40,10+avg*lengthScale,60+6*timelist.length,ntp);
		//ntp.setStrokeWidth(2);
		//ntp.setMixColor(Color4.rgba(0,1,0,1));
		//canvas.drawLine(10+max*lengthScale,40,10+max*lengthScale,60+6*timelist.length,ntp);
		ntp.setStrokeWidth(2);
		ntp.setMixColor(Color4.rgba(0,0,1,1));
		canvas.drawLine(10+min*lengthScale,40,10+min*lengthScale,60+6*timelist.length,ntp);
		canvas.restore();
		
		//canvas.drawTexture(cardPng,RectF.xywh(0,0,canvas.getWidth(),canvas.getHeight()),newPaint);
		
		
		canvas.setCanvasAlpha(0.5f);
		float baseLine=300;
		GLPaint textPaint=new GLPaint();
		textPaint.setMixColor(Color4.rgba(1,1,0,1));
		TextPrinter printer=new TextPrinter(font,100,baseLine,textPaint);
		printer.setTextSize(70);
		printer.printString("DEVELOPMENT BUILD\nosu!lab 2018.4.9");
		printer.toNextLine();
		printer.printString("fps: "+(int)(1000/avg));
		printer.toNextLine();
		printer.printString(timeline.frameTime()+"");
		printer.toNextLine();
		printer.printString(audioOffset+"");
		printer.toNextLine();
		printer.printString(playField.objectInFieldCount()+"");
		//printer.toNextLine();
		//printer.setTextSize(50);
		//printer.printString("osu!lab");
		//printer.toNextLine();
		//printer.toNextLine();
		//printer.printString("D̨Á̶̢T̛͝͡AÈ̶R͢͢Ŕ͡0R͟͟");
		printer.draw(canvas);
		GLPaint baseLinePaint=new GLPaint();
		baseLinePaint.setMixColor(Color4.rgba(1,0,0,0.6f));
		baseLinePaint.setStrokeWidth(2);
		//baseLinePaint.setColorMixRate(1);
		canvas.drawLine(0,baseLine,1000,baseLine,baseLinePaint);
		baseLinePaint.setMixColor(Color4.rgba(0,1,0,0.6f));
		canvas.drawLine(0,baseLine+font.getCommon().base*printer.getScale(),1000,baseLine+font.getCommon().base*printer.getScale(),baseLinePaint);
	}
	
	public float[] timelist=new float[40];
	
	
	public class TestData{
		public String testPath="osu/test/beatmap";
		
		public int testBeatmapFloder=1;
		
		public String getBeatmapPath(){
			AResource res=getContext().getAssetResource().subResource(testPath);
			try {
				String[] list=res.list(testBeatmapFloder + "");
				for(String s:list){
					System.out.println(s);
					if(s.endsWith(".osu")){
						return testBeatmapFloder+"/"+s;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public String getSongPath(){
			AResource res=getContext().getAssetResource().subResource(testPath);
			try {
				String[] list=res.list(testBeatmapFloder + "");
				for(String s:list){
					if(s.endsWith(".mp3")){
						return testBeatmapFloder+"/"+s;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	
}
