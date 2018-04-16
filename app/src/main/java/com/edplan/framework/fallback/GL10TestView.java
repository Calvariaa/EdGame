package com.edplan.framework.fallback;
import android.opengl.GLES10;
import android.util.Log;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GL10Canvas2D;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.media.bass.BassChannel;
import com.edplan.framework.resource.AResource;
import com.edplan.framework.resource.DirResource;
import com.edplan.framework.timing.AudioTimeline;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.text.font.bmfont.BMFont;
import com.edplan.framework.ui.text.font.drawing.TextPrinter;
import com.edplan.nso.NsoException;
import com.edplan.nso.parser.StdBeatmapDecoder;
import com.edplan.nso.parser.StoryboardDecoder;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.nso.ruleset.amodel.playing.PlayField;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.nso.ruleset.std.playing.StdPlayingBeatmap;
import com.edplan.nso.storyboard.PlayingStoryboard;
import com.edplan.nso.storyboard.Storyboard;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONObject;

public class GL10TestView extends EdView implements GLES10Drawable
{

	private StdBeatmap beatmap;

	private StdPlayingBeatmap playingBeatmap;

	private Storyboard storyboard;

	private PlayingStoryboard playingStoryboard;

	private PreciseTimeline timeline;

	private BassChannel audio;

	private OsuSkin skin;


	float a=0;
	long lt=0;

	int delt=0;

	BMFont font;

	TestData test;


	public GL10TestView(MContext c){
		super(c);
	}

	@Override
	public void onCreate(){
		if(true)return;
		test=new TestData();
		try {
			AResource res=getContext().getAssetResource().subResource("font");
			font=BMFont.loadFont(
				res,
				//			"Exo2.0-Regular.fnt");

				"Noto-CJK-Basic.fnt");
			//font.setErrCharacter(BMFont.CHAR_NOT_FOUND);
			font.addFont(res,"Noto-Basic.fnt");
			font.setErrCharacter('⊙');
			TextPrinter.addFont(font,"default");

		} catch (IOException e) {
			e.printStackTrace();
		}


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

			StdBeatmapDecoder bparser=new StdBeatmapDecoder(
				//getContext()
				//.getAssetResource()
				//.subResource("osu/test/beatmap")
				test.testFloder().openInput(
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

			audio=BassChannel.createStreamFromFile(
				test.dir+"/"+
				//.createStreamFromResource(getContext().getAssetResource(),"osu/test/beatmap/"+
				//.createStreamFromAsset(getContext(),"osu/test/beatmap/"+
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
				//new AdjustableTimeline(1/5f);
				playingBeatmap=new StdPlayingBeatmap(getContext(),beatmap,timeline,skin);
				Log.v("osu","objs: "+playingBeatmap.getHitObjects().size()+" first: "+playingBeatmap.getHitObjects().get(0).getStartTime());
				//# osb test
				if(test.enableStoryboard){
					File osb=new File(//"/storage/emulated/0/ADM/470977 Mili - world.execute(me)/Mili - world.execute(me); (Exile-).osb");
						//"/storage/emulated/0/ADM/186911 Function Phantom - Neuronecia2/Function Phantom - Neuronecia (Amamiya Yuko).osb");
						//"/storage/emulated/0/ADM/151720 ginkiha - EOS1/ginkiha - EOS (alacat).osb");
						//"/storage/emulated/0/ADM/389179 Jay Chou - Fa Ru Xue3/Jay Chou - Fa Ru Xue (KaedekaShizuru).osb");
						"/storage/emulated/0/osu!droid/Songs/470977 Mili - world.execute(me)/Mili - world.execute(me); (Exile-).osb");
					StoryboardDecoder decoder=new StoryboardDecoder(
						//osb);
						test.testFloder().openInput(test.getOsbPath()),"ahh");

					try {
						long s=System.currentTimeMillis();
						decoder.parse();
						storyboard=decoder.getStoryboard();
						if(beatmap!=null&&test.applyBeatmap){
							storyboard.applyBeatmap(beatmap);
						}
						System.out.println("end edcode osb: "+storyboard.getObjectCount());
						playingStoryboard=new PlayingStoryboard(getContext(),timeline,storyboard,
																//new DirResource(osb.getParentFile()));
																test.testFloder().subResource(test.testBeatmapFloder+""));
						//new DirResource(osb.getParentFile()));
						System.out.println("end transform to playing state");
						System.out.println("storyboard parse done in: "+(System.currentTimeMillis()-s)+"ms");

						JSONObject jobj=new JSONObject("{\"a\":100}");
						System.out.println(jobj.toString());


						/*
						 for(int i=0;i<5;i++){
						 BaseDrawableSprite spr=(BaseDrawableSprite) playingStoryboard.getLayer(Storyboard.Layer.Foreground.name()).getSprites().get(i);
						 System.out.println(spr.getAnimations());
						 }
						 */

					} catch (Exception e) {
						e.printStackTrace();
					}

					//# end osb test
				}
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	GLTexture[] l={GLTexture.Red,GLTexture.Blue,GLTexture.White};
	float t=0;
	@Override
	public void drawGL10(GL10Canvas2D canvas) {
		// TODO: Implement this method
		canvas.drawColor(Color4.Black);
		canvas.clearBuffer();
		
		GLES10.glFrontFace(GL10.GL_CW);
		GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES10.glLoadIdentity();
		canvas.getData().getShaders().getTexture3DShader().loadMatrix(canvas.getCamera());
		//c.draw();
		//GLES10.glFlush();
		
		tang.draw(null);
		//c.draw();
		
		t+=getContext().getFrameDeltaTime();
		
		int idx=((int)t/1000)%l.length;
		
		
		canvas.drawTexture(skin.approachCircle.getRes(),RectF.xywh(0,0,100f,100f),Color4.ONE,Color4.ONE,1);
		/*
		canvas.drawTexture(l[idx],RectF.xywh(100,100,100f,100f),Color4.ONE,Color4.ONE,1);
		canvas.drawTexture(GLTexture.White,RectF.xywh(200,200,100f,100f),Color4.ONE,Color4.ONE,1);
		canvas.postDraw();
		*/
		
		if(true)return;
		
		float osuScale=PlayField.BASE_Y/canvas.getHeight();
		canvas.translate(canvas.getWidth()/2-PlayField.BASE_X/2/osuScale,0);
		canvas.scaleContent(osuScale);
		canvas.translate(PlayField.PADDING_X,PlayField.PADDING_Y);
		canvas.clip(new Vec2(PlayField.CANVAS_SIZE_X,PlayField.CANVAS_SIZE_Y));
		
		canvas.save();
		canvas.translate(-PlayField.PADDING_X,-PlayField.PADDING_Y);
		//if(test.enableStoryboard&&!test.watchPool)
		//	playingStoryboard.drawGL10(canvas);
		canvas.restore();
		
		
		float lengthScale=1;
		float deltaTime=(float)getContext().getFrameDeltaTime();
		for(int i=timelist.length-1;i>0;i--){
			timelist[i]=timelist[i-1];
		}
		timelist[0]=deltaTime;
		for(int i=1;i<timelist.length;i++){
			//canvas.drawLine(10,55+6*i,10+timelist[i]*lengthScale,55+6*i,ntp);
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
		
		
		float baseLine=300;
		GLPaint textPaint=new GLPaint();
		textPaint.setMixColor(Color4.rgba(1,0,0,1));
		TextPrinter printer=new TextPrinter(font,100,baseLine,textPaint);
		printer.setTextSize(70);
		printer.printString("DEVELOPMENT BUILD\nosu!lab 2018.4.9");
		printer.toNextLine();
		printer.printString("fps: "+(int)(1000/avg));
		printer.toNextLine();
		printer.printString(timeline.frameTime()+"");
		printer.toNextLine();
		if(test.enableStoryboard){
			printer.printString(playingStoryboard.objectsInField()+"\n");
			printer.printString("new: "+playingStoryboard.newApply()+"");
		}
		printer.toNextLine();
		printer.printString(timeline.animationCount()+"");
		//printer.setTextSize(50);
		//printer.printString("osu!lab");
		//printer.toNextLine();
		//printer.toNextLine();
		//printer.printString("D̨Á̶̢T̛͝͡AÈ̶R͢͢Ŕ͡0R͟͟");
		//printer.drawGL10(canvas);
		
	}

	public float[] timelist=new float[40];

	public Cube c=new Cube();
	
	public Triangle tang=new Triangle();
	
	public class Triangle {
		private FloatBuffer myVertexBuffer;
		private FloatBuffer myColorBuffer;
		private ByteBuffer myIndexBuffer;
		int vCount;
		int iCount;
		float yAngle;
		float zAngle;

		public Triangle(){
			final float UNIT_SIZE=100f;
			float z=0;
			float[] vertices={
				-4*UNIT_SIZE,4*UNIT_SIZE,z,
				-4*UNIT_SIZE,-4*UNIT_SIZE,z,
				4*UNIT_SIZE,-4*UNIT_SIZE,z,
				4*UNIT_SIZE,4*UNIT_SIZE,z,
				-4*UNIT_SIZE,4*UNIT_SIZE,z,
				-4*UNIT_SIZE,-4*UNIT_SIZE,z,
				4*UNIT_SIZE,-4*UNIT_SIZE,z,
				4*UNIT_SIZE,4*UNIT_SIZE,z,
				0,0,0
			};
			vCount=vertices.length/3;
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
			vbb.order(ByteOrder.nativeOrder());
			myVertexBuffer=vbb.asFloatBuffer();
			myVertexBuffer.put(vertices);
			myVertexBuffer.position(0);
			final float one=1;
			float[] colors={
				one,one,0,0,
				0,one,one,0,
				one,0,one,0,
				one,one,one,0,
				one,one,one,0,
				one,one,0,0,
				0,one,one,0,
				one,0,one,0,
				0,0,0,0
			};
			ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);
			cbb.order(ByteOrder.nativeOrder());
			myColorBuffer=cbb.asFloatBuffer();
			myColorBuffer.put(colors);
			myColorBuffer.position(0);
			byte[] indices={
				0,2,1,0,3,2
				//,
				//5,6,7,5,7,4,
				//0,5,4,0,1,5,
				//2,3,7,2,7,6,
				//0,4,7,0,7,3,
				//5,1,2,5,2,6
			};
			iCount=indices.length;
			myIndexBuffer=ByteBuffer.allocateDirect(indices.length);
			myIndexBuffer.order(ByteOrder.nativeOrder());
			myIndexBuffer.put(indices);
			myIndexBuffer.position(0);
		}

		public void draw(GL10 gl){
			GLES10.glShadeModel(GLES10.GL_SMOOTH);
			GLES10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			GLES10.glEnableClientState(GL10.GL_COLOR_ARRAY);
			//GLES10.glRotatef(1,1,0,0);
			//GLES10.glRotatef(zAngle,0,0,1);
			GLES10.glVertexPointer(
				3,
				GL10.GL_FLOAT,
				0,
				myVertexBuffer
			);
			GLES10.glColorPointer(
				4,
				GL10.GL_FLOAT,
				0,
				myColorBuffer
			);
			GLES10.glDrawElements(
				GL10.GL_TRIANGLES,
				iCount,
				GL10.GL_UNSIGNED_BYTE,
				myIndexBuffer
			);
		}
	}
	
	class Cube
	{
		public Cube()
		{
			int one = 0x10000;
			int vertices[] = {
                -one, -one, -one,
                one, -one, -one,
                one,  one, -one,
                -one,  one, -one,
                -one, -one,  one,
                one, -one,  one,
                one,  one,  one,
                -one,  one,  one,
			};

			int colors[] = {
                0,    0,    0,  one,
                0,    0,    0,  one,
                one,  one,    0,  one,
                0,  one,    0,  one,
                one,    one,  one,  one,
                one,    one,  one,  one,
                one,  one,  one,  one,
                0,  one,  one,  one,
			};

			byte indices[] = {
                0, 4, 5,    0, 5, 1,
                1, 5, 6,    1, 6, 2,
                2, 6, 7,    2, 7, 3,
                3, 7, 4,    3, 4, 0,
                4, 7, 6,    4, 6, 5,
                3, 0, 1,    3, 1, 2
			};

			// Buffers to be passed to gl*Pointer() functions
			// must be direct, i.e., they must be placed on the
			// native heap where the garbage collector cannot
			// move them.
			//
			// Buffers with multi-byte datatypes (e.g., short, int, float)
			// must have their byte order set to native order

			ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer = vbb.asIntBuffer();
			mVertexBuffer.put(vertices);
			mVertexBuffer.position(0);

			ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
			cbb.order(ByteOrder.nativeOrder());
			mColorBuffer = cbb.asIntBuffer();
			mColorBuffer.put(colors);
			mColorBuffer.position(0);

			mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
			mIndexBuffer.put(indices);
			mIndexBuffer.position(0);
		}

		public void draw()
		{
			GLES10.glFrontFace(GL10.GL_CW);
			GLES10.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
			GLES10.glColorPointer(4, GL10.GL_FIXED, 0, mColorBuffer);
			GLES10.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
		}

		private IntBuffer   mVertexBuffer;
		private IntBuffer   mColorBuffer;
		private ByteBuffer  mIndexBuffer;
	}
	
	

	public class TestData{
		public String testPath="osu/test/beatmap";

		public String dir="/storage/emulated/0/MyDisk/WorkBench/bin/testdata";

		public int testBeatmapFloder=13;

		public boolean enableStoryboard=true;

		public boolean enablePlayField=true;

		public boolean watchPool=false;

		public boolean applyBeatmap=true;

		public AResource res=new DirResource("/storage/emulated/0/MyDisk/WorkBench/bin/testdata");

		public String getBeatmapPath(){
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

		public String getOsbPath(){
			try {
				String[] list=res.list(testBeatmapFloder + "");
				for(String s:list){
					if(s.endsWith(".osb")){
						return testBeatmapFloder+"/"+s;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public AResource testFloder(){
			return res;
		}

	}
	
	
}
