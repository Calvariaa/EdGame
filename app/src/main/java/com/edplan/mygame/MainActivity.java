package com.edplan.mygame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import com.edplan.framework.animation.AnimaAdapter;
import com.edplan.framework.animation.AnimaCallback;
import com.edplan.framework.animation.MAnimation_old;
import com.edplan.framework.animation.interpolator.MaterialInterpolator;
import com.edplan.framework.view.BaseDatas;
import com.edplan.framework.view.BaseWidget;
import com.edplan.framework.view.MButton;
import com.edplan.framework.view.MListView;
import com.edplan.framework.view.MStaticViewGroup;
import com.edplan.framework.view.MTextView;
import com.edplan.framework.view.Stage;
import com.edplan.framework.view.Game.Background;
import android.graphics.Paint;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Bitmap;
import com.edplan.framework.view.MProgressBar;
import com.edplan.framework.view.advance.widget.OsuTriangleField;
import com.edplan.framework.view.advance.widget.OsuTriangleManager;
import com.edplan.framework.view.advance.widget.OsuBaseTriangleManager;
import com.edplan.superutils.classes.MLooperThread;

public class MainActivity extends Activity 
{
	GameSurfaceView gsv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		BaseDatas.initial(this);
		/*
		gsv=new MGameSurfaceView(this);
        setContentView(gsv);
		gsv.setFlag(MLooperThread.TFlag.Running);*/
    }

	@Override
	protected void onPause(){
		// TODO: Implement this method
		super.onPause();
		gsv.setFlag(MLooperThread.TFlag.Waiting);
	}

	@Override
	protected void onStop(){
		// TODO: Implement this method
		super.onStop();
		gsv.setFlag(MLooperThread.TFlag.Stop);
	}

	@Override
	protected void onResume(){
		// TODO: Implement this method
		super.onResume();
		gsv.setFlag(MLooperThread.TFlag.Running);
	}
	
	
	
	
	
	
	
	/*
	
	public class MGameSurfaceView extends GameSurfaceView{
		
		float a=0;
		
		MStaticViewGroup msg=new MStaticViewGroup();
		
		MButton b=new MButton();
		MListView listView=new MListView();
		MProgressBar pb=new MProgressBar();
		Stage mstage=new Stage();
		ActorTest ac=new ActorTest();
		MTextView tv=new MTextView();
		
		
		Bitmap testBitmap;
		
		OsuTriangleManager otm=new OsuBaseTriangleManager();
		OsuTriangleField otf=new OsuTriangleField(otm);
		
		
		public MGameSurfaceView(Context c){
			super(c);
			
			testBitmap=Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888);
			
			Canvas t=new Canvas(testBitmap);
			
			t.drawColor(0x00222222);
			
			/*
			Paint p=new Paint();

			p.setStrokeWidth(6);
			p.setARGB(255,255,30,30);
			int y=0;
			while(y<=200){
				t.drawLine(0,y,200,y,p);
				y+=20;
			}

			p.setARGB(255,30,255,30);
			int x=0;
			while(x<=200){
				t.drawLine(x,0,x,200,p);
				x+=20;
			}
			
			
			camera.translate(100,-900,0);
			camera.save();
			
			pb.setBasePoint(300,1000);
			
			mstage.addActor(ac);
			mstage.translate(0,0,0).rotate(40,0,0).applyCamera();
			mstage.getMatrix().preTranslate(-540,0);
			mstage.getMatrix().postTranslate(540,0);
			ac.setBasePoint(0,0);
			
			b.text="🏅";
			b.setWidth(400);
			b.setHeight(100);
			b.setCenter(540,0);
			
			
			listView.setBasePoint(100,100);
			listView.setWidth(500);
			listView.setHeight(1500);
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			
			
			//if(true)return;
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			listView.add(createButton());
			
			tv.setText("Edplan🔮");
			tv.setWidth(300);
			tv.setHeight(500);
			tv.setBasePoint(400,500);
			
			
			b.setOnClickListener(new BaseWidget.MOnClickListener(){
				MAnimation_old anima;
				@Override
				public void onClick(BaseWidget view){
					// TODO: Implement this method
					if(anima!=null)return;
					//Log.v("anim","creat");
					anima=new MAnimation_old();
					anima.setAdapter(new AnimaAdapter(){
							float nowP=b.basePoint.y;
							@Override
							public void setProgress(float p){
								// TODO: Implement this method
								b.setBasePoint(b.basePoint.x,p*400+nowP);
								b.setAlpha((int)(250*(1-p)+5));
								//Log.v("anim","progress "+p+"|"+(p*600+nowP));
							}
						});
					anima.setInterpolator(new MaterialInterpolator());
					anima.setDuration(700);
					anima.setCallback(new AnimaCallback(){
							long startTime;
							@Override
							public void onEnd(){
								// TODO: Implement this method
								anima=null;
								//Log.v("anim","end in "+(System.currentTimeMillis()-startTime)+"ms");
							}

							@Override
							public void onStart(){
								// TODO: Implement this method
								//Log.v("anim","start");
								startTime=System.currentTimeMillis();
							}

							@Override
							public void onProgress(float p){
								// TODO: Implement this method
								
							}

							@Override
							public void onFinish(){
								// TODO: Implement this method
								//Log.v("anim","finish");
							}

							@Override
							public void onStop(float p){
								// TODO: Implement this method
							}
						});
					anima.start();
				}
			});
			
			msg.add(otf);
			msg.add(listView);
			msg.add(tv);
			msg.add(b);
			msg.add(mstage);
			msg.add(pb); 
			
			msg.setSizedListener(new BaseWidget.MOnSizedListener(){
					@Override
					public void onSized(BaseWidget widget, float w, float h){
						// TODO: Implement this method
						otf.setWidth(w);
						otf.setHeight(h);
						if(otm.getTriangles().size()<100)for(int i=0;i<50;i++){
							otm.add();
						}
					}
				});
			
			Background background=new Background(mstage){
				@Override
				public void drawBackground(Canvas c){
					Paint p=new Paint();
					p.setAntiAlias(true);
					p.setStrokeWidth(6);
					p.setARGB(255,255,30,30);
					/*
					
					int y=0;
					while(y<2000){
						c.drawLine(0,y,2000,y,p);
						y+=100;
					}

					p.setARGB(255,30,255,30);
					int x=0;
					while(x<2000){
						c.drawLine(x,0,x,2000,p);
						x+=100;
					}
					
					
					//c.drawBitmap(testBitmap,0,0,p);
					
				}
			};
			
			mstage.setBackground(background);
			
			this.setContent(msg);
		}
		
		public MButton createButton(){
			MButton b=new MButton();
			//b.setWidth(400);
			//b.setHeight(80);
			//b.setBasePoint(0,0);
			b.text="Edplan";
			b.setAlpha(100);
			b.color=Color.argb(255,(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			return b;
		}

		/*
		@Override
		public boolean onTouchEvent(MotionEvent event){
			
			
			
			return msg.onTouch(event);
		}
		
		
		Camera camera=new Camera();
		Matrix matrix=new Matrix();
		float deg=0;
		
		@Override
		public void mDraw(Canvas c){
			
			long t=System.currentTimeMillis();
			
			c.drawColor(Color.WHITE);
			//a+=5;
			
			//listView.draw(c);
			
			//c.save();
			//b.draw(b.clipCanvasToThis(c));
			//c.restore();
			
			msg.draw(c);
			
			//mstage.draw(c);
			
			//ac.draw(c);
			
			camera.save();
			c.save();
			deg+=1;
			camera.translate(540,-900,0);
			camera.rotate(deg,0,0);
			camera.getMatrix(matrix);
			//matrix.preTranslate(540,990);
			//matrix.postTranslate(-540,-990);
			c.setMatrix(matrix);
			
			
			
			Paint px=new Paint();

			px.setStrokeWidth(6);
			px.setARGB(255,30,30,255);
			
			c.drawBitmap(testBitmap,-100,-100,px);
			
			/*
			float deg=0;
			
			while(deg<90){
				deg+=10;
				camera.save();
				camera.rotate(deg,0,0);
				matrix.reset();
				camera.getMatrix(matrix);
				matrix.preTranslate(-540,0);
				matrix.postTranslate(540,0);
				c.setMatrix(matrix);
				c.drawLine(0,0,0,200,px);
				camera.restore();
			}
			
			camera.save();
			
			int x=0;
			while(x<1080){
				x+=40;
				camera.rotate(3,0,0);
				matrix.reset();
				camera.getMatrix(matrix);
				matrix.preTranslate(-540,0);
				matrix.postTranslate(540,0);
				c.setMatrix(matrix);
				//if(x<1000)continue;
				//c.drawLine(x,0,x,1000,px);
				c.drawRect(x,0,x+5,100,px);
			}
			camera.restore();
			x=0;
			while(x<1080){
				x+=40;
				camera.rotate(1,0,0);
				matrix.reset();
				camera.getMatrix(matrix);
				matrix.preTranslate(-540,0);
				matrix.postTranslate(540,0);
				c.setMatrix(matrix);
				//if(x<1000)continue;
				//c.drawLine(x,0,x,1000,px);
				c.drawRect(x,0,x+5,5000,px);
			}
			
			
			
			c.restore();
			camera.restore();
			
			
			
			TextPaint p=new TextPaint();
			p.setARGB(255,0,0,0);
			if((System.currentTimeMillis()-t)>18){
				p.setARGB(255,255,0,0);
			}
			p.setTextSize(50);
			p.setFakeBoldText(true);
			c.drawText((System.currentTimeMillis()-t)+"",500,200,p);
		}
	}
	*/
}




