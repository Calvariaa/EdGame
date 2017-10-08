package com.edplan.mygame.acts;
import com.edplan.mygame.GameSurfaceView;
import com.edplan.simpleGame.view.MStaticViewGroup;
import com.edplan.simpleGame.view.advance.widget.OsuBaseTriangleManager;
import com.edplan.simpleGame.view.advance.widget.OsuTriangleField;
import com.edplan.simpleGame.view.advance.widget.OsuTriangleManager;
import com.edplan.simpleGame.view.MButton;
import com.edplan.simpleGame.view.MFlatButton;
import com.edplan.simpleGame.view.MTextView;
import android.graphics.Color;

public class MainGamePage extends MStaticViewGroup
{
	public GameSurfaceView suface;
	
	public MainGamePage(GameSurfaceView res){
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
	
	OsuTriangleManager otm;
	OsuTriangleField otf;
	
	MStaticViewGroup titleField;
	MFlatButton titleBackground;
		MTextView titleTextView;
	
	MStaticViewGroup mainButtonField;
	MButton fieldBackground;
		MButton startPlayButton;
		MButton backpackButton;
		MButton storeButton;
		MButton settingButton;
	
	MStaticViewGroup topDataBar;
	MFlatButton barBackground;
		MTextView medalCount;
		MTextView crystalCount;
	
	
	public void initialGame(){
		otm=new OsuBaseTriangleManager();
		otf=new OsuTriangleField(otm);
		otf.setHeight(getHeight());
		otf.setWidth(getWidth());
		for(int i=0;i<75;i++){
				otm.add();
		}
		
		mainButtonField=new MStaticViewGroup();
		fieldBackground=new MButton();
			startPlayButton=new MButton();
			backpackButton=new MButton();
			storeButton=new MButton();
			settingButton=new MButton();
		
			float w1=getWidth()*0.5f;
			float h1=getHeight()*0.06f;
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
		
		add(otf);
		add(mainButtonField);
	}

	
	
	public void setSuface(GameSurfaceView suface){
		this.suface=suface;
	}

	public GameSurfaceView getSuface(){
		return suface;
	}
}
