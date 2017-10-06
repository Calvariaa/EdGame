package com.edplan.mygame;

import android.app.*;
import android.os.*;
import android.graphics.Canvas;
import com.edplan.simpleGame.view.MButton;
import android.graphics.Color;
import android.view.MotionEvent;
import com.edplan.simpleGame.view.MListView;
import android.content.Context;
import com.edplan.simpleGame.view.BaseDatas;
import android.text.TextPaint;
import com.edplan.simpleGame.view.MStaticViewGroup;
import com.edplan.simpleGame.view.Stage;
import com.edplan.simpleGame.view.MTextView;

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
			ac.setBasePoint(0,100);
			listView.setBasePoint(10,10);
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
			
			msg.add(b);
			msg.add(listView);
			msg.add(tv);
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


		@Override
		public boolean onTouchEvent(MotionEvent event){
			return listView.onTouch(event)||b.onTouch(event);
		}


		@Override
		public void mDraw(Canvas c){
			
			long t=System.currentTimeMillis();
			
			c.drawColor(Color.WHITE);
			//a+=5;
			
			//listView.draw(c);
			b.text="✪ω✪";
			b.setWidth(400);
			b.setHeight(100);
			b.setCenter(c.getWidth()/2,c.getHeight()/2+a);
			
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




