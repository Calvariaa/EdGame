package com.edplan.mygame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import com.edplan.simpleGame.animation.AnimaAdapter;
import com.edplan.simpleGame.animation.AnimaCallback;
import com.edplan.simpleGame.animation.MAnimation;
import com.edplan.simpleGame.animation.interpolator.MaterialInterpolator;
import com.edplan.simpleGame.view.BaseDatas;
import com.edplan.simpleGame.view.BaseWidget;
import com.edplan.simpleGame.view.MButton;
import com.edplan.simpleGame.view.MListView;
import com.edplan.simpleGame.view.MStaticViewGroup;
import com.edplan.simpleGame.view.MTextView;
import com.edplan.simpleGame.view.Stage;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		BaseDatas.initial(this);
		
		GameSurfaceView gsv=new MGameSurfaceView(this);
        setContentView(gsv);
    }
	
	public class MGameSurfaceView extends GameSurfaceView{
		
		float a=0;
		
		MStaticViewGroup msg=new MStaticViewGroup();
		
		MButton b=new MButton();
		MListView listView=new MListView();
		Stage mstage=new Stage();
		ActorTest ac=new ActorTest();
		MTextView tv=new MTextView();
		
		public MGameSurfaceView(Context c){
			super(c);
			mstage.addActor(ac);
			mstage.translate(100,-100,0).rotate(70,0,0).applyCamera();
			mstage.getMatrix().preTranslate(-700,0);
			mstage.getMatrix().postTranslate(700,0);
			ac.setBasePoint(0,200);
			
			b.text="✪ω✪";
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
			
			tv.setText("Edplan !..............*****\nkkkk\nk");
			tv.setWidth(300);
			tv.setHeight(500);
			tv.setBasePoint(400,500);
			
			b.setOnClickListener(new BaseWidget.MOnClickListener(){
				MAnimation anima;
				@Override
				public void onClick(BaseWidget view){
					// TODO: Implement this method
					if(anima!=null)return;
					Log.v("anim","creat");
					anima=new MAnimation();
					anima.setAdapter(new AnimaAdapter(){
							float nowP=b.basePoint.y;
							@Override
							public void setProgress(float p){
								// TODO: Implement this method
								b.setBasePoint(b.basePoint.x,p*400+nowP);
								b.setAlpha((int)(250*(1-p)+5));
								Log.v("anim","progress "+p+"|"+(p*600+nowP));
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
								Log.v("anim","end in "+(System.currentTimeMillis()-startTime)+"ms");
							}

							@Override
							public void onStart(){
								// TODO: Implement this method
								Log.v("anim","start");
								startTime=System.currentTimeMillis();
							}

							@Override
							public void onProgress(float p){
								// TODO: Implement this method
								
							}

							@Override
							public void onFinish(){
								// TODO: Implement this method
								Log.v("anim","finish");
							}

							@Override
							public void onStop(float p){
								// TODO: Implement this method
							}
						});
					anima.start();
				}
			});
			
			
			msg.add(listView);
			msg.add(tv);
			msg.add(b);
			
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
		}*/


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
			
			mstage.draw(c);
			
			//ac.draw(c);
			
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
	
}




