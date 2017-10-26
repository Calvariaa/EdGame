package com.edplan.simpleGame.view.advance.widget;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.ArrayMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import com.edplan.simpleGame.GameStatic;
import com.edplan.simpleGame.view.BaseDatas;

public class OsuBaseTriangleManager implements OsuTriangleManager
{
	public int color=0xFFFFFFFF;
	
	public float width;
	
	public float height;
	
	public Random random;
	
	public Map<OsuTriangle,Property> triangles;
	
	public OsuBaseTriangleManager(){
		triangles=new ArrayMap<OsuTriangle,Property>();
		random=new Random();
	}

	public void setColor(int color){
		this.color=color;
	}

	public int getColor(){
		return color;
	}

	@Override
	public void setWidth(float width){
		this.width=width;
	}

	@Override
	public float getWidth(){
		return width;
	}

	@Override
	public void setHeight(float height){
		this.height=height;
	}

	@Override
	public float getHeight(){
		return height;
	}
	
	private Property makeProperty(OsuTriangle t){
		Property p=new Property();
		p.speed=(random.nextFloat()*BaseDatas.dpToPixel(1.3f)+BaseDatas.dpToPixel(9)/t.getRadius()+BaseDatas.dpToPixel(1.2f))/16;
		p.targetAlpha=t.getPaint().getAlpha();
		p.alpha=0;
		p.size=t.getRadius();
		return p;
	}
	
	@Override
	public void add(){
		OsuTriangle o=new OsuTriangle();
		o.setRadius(random.nextFloat()*BaseDatas.dpToPixel(75)+BaseDatas.dpToPixel(18));
		o.getCenterPoint().set(getWidth()*random.nextFloat(),getHeight()*(1+random.nextFloat())/2);
		Paint p=new Paint();
		p.setColor(getColor());
		p.setAlpha((int)(10+100*random.nextFloat()));
		o.setPaint(p);
		add(o);
	}
	
	@Override
	public void add(OsuTriangle t){
		// TODO: Implement this method
		triangles.put(t,makeProperty(t));
	}

	@Override
	public Set<OsuTriangle> getTriangles(){
		// TODO: Implement this method
		return triangles.keySet();
	}
	
	long latestTime=0;
	int deltaTime;

	@Override
	public void measure(Canvas c){
		// TODO: Implement this method
		
		/*
		if(latestTime==0){
			latestTime=System.currentTimeMillis();
			deltaTime=1;
		}else{
			deltaTime=(int)(System.currentTimeMillis()-latestTime);
			latestTime=System.currentTimeMillis();
		}*/
		
		deltaTime=GameStatic.getDrawDeltaTime();
		
		OsuTriangle t;
		Property p;
		for(Map.Entry<OsuTriangle,Property> e:triangles.entrySet()){
			t=e.getKey();
			p=e.getValue();
			if(p.alpha<p.targetAlpha){
				p.alpha+=0.2f*deltaTime;
				if(p.alpha>p.targetAlpha)p.alpha=p.targetAlpha;
				t.getCenterPoint().move(0f,-p.speed*deltaTime*p.alpha/p.targetAlpha);
			}else{
				t.getCenterPoint().move(0f,-p.speed*deltaTime);
			}
			
			//p.alpha=(int)(255*Math.min(getHeight()-t.getCenterPoint().x,t.getCenterPoint().x)/getHeight()/2);
			if(e.getKey().getBottom()<0){
				reset(e.getKey(),e.getValue());
			}
			t.getPaint().setARGB(255,(int)p.alpha,(int)p.alpha,(int)p.alpha);
			//t.getPaint().setAlpha((int)p.alpha);
		}
	}
	
	private void reset(OsuTriangle t,Property p){
		t.getCenterPoint().set(getWidth()*random.nextFloat(),getHeight()+t.getRadius());
		//+0*getHeight()*(1+random.nextFloat())/2);
		p.targetAlpha=(int)(70+((int)(random.nextFloat()*5))*27);
		p.alpha=0;
		p.size=t.getRadius();
	}
	
	private class Property{
		
		public float speed=0;
		
		public int targetAlpha=255;
		
		public float alpha=0;
		
		public float size=0;
		
	}
}
