package com.edplan.nso.ruleset.std.playing;
import android.util.Log;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.ruleset.amodel.playing.PlayField;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitObject;
import com.edplan.nso.ruleset.std.playing.drawable.interfaces.IHasApproachCircle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdFollowpoint;

public class StdPlayField extends PlayField
{
	private FieldDrawables drawableHitObjects=new FieldDrawables();
	
	private FieldDrawables connectionDrawables=new FieldDrawables();
	
	private PreciseTimeline timeline;
	
	//private approachCircleLayer;
	
	//private connectionLayer;
	
	
	
	public StdPlayField(MContext context,PreciseTimeline timeline){
		super(context);
		this.timeline=timeline;
	}

	public List<DrawableStdHitObject> getDrawableHitObjects() {
		return drawableHitObjects.getObjects();
	}

	@Override
	public void applyBeatmap(PlayingBeatmap res) {
		// TODO: Implement this method
		Log.v("beatmap-data",res.getDifficulty().makeString());
		if(res instanceof StdPlayingBeatmap){
			StdPlayingBeatmap beatmap=(StdPlayingBeatmap)res;
			drawableHitObjects.setObjects(beatmap.getDrawableHitObjects());
			connectionDrawables.setObjects(beatmap.getDrawableConnections());
			Log.v("test-datas","Connections: "+connectionDrawables.getObjects().size());
		}else{
			throw new IllegalArgumentException("you can only apply a StdPlayingBeatmap to StdPlayField");
		}
	}
	
	
	
	protected void drawContentLayer(GLCanvas2D canvas){
		DrawableStdHitObject obj;
		for(int i=drawableHitObjects.getObjectsInField().size()-1;i>=0;i--){
			obj=drawableHitObjects.getObjectsInField().get(i);
			obj.draw(canvas);
			if(obj instanceof IHasApproachCircle){
				((IHasApproachCircle)obj).getApproachCircle().draw(canvas);
			}
		}
	}
	
	private void drawConnectionLayer(GLCanvas2D canvas){
		DrawableStdHitObject obj;
		for(int i=connectionDrawables.getObjectsInField().size()-1;i>=0;i--){
			obj=connectionDrawables.getObjectsInField().get(i);
			obj.draw(canvas);
		}
	}
	
	private void drawApproachCircleLayer(GLCanvas2D canvas){
		DrawableStdHitObject obj;
		for(int i=drawableHitObjects.getObjectsInField().size()-1;i>=0;i--){
			obj=drawableHitObjects.getObjectsInField().get(i);
			if(obj instanceof IHasApproachCircle){
				((IHasApproachCircle)obj).getApproachCircle().draw(canvas);
			}
		}
	}
	
	private void drawTestLayer(GLCanvas2D canvas){
		List<DrawableStdHitObject> l=drawableHitObjects.getObjectsInField();
		GLPaint paint=new GLPaint();
		paint.setColorMixRate(1);
		paint.setStrokeWidth(2f);
		paint.setMixColor(Color4.rgba(1,0,0,1));
		StdHitObject v1,v2;
		for(int i=1;i<l.size();i++){
			v1=l.get(i-1).getHitObject();
			v2=l.get(i).getHitObject();
			if(!v2.isNewCombo())canvas.drawLines(new float[]{v1.getStartX(),v1.getStartY(),v2.getStartX(),v2.getStartY()},paint);
		}
		
		l=connectionDrawables.getObjectsInField();
		
		//StdHitObject v1,v2;
		paint.setStrokeWidth(1f);
		paint.setMixColor(Color4.rgba(0,1,0,1));
		for(int i=0;i<l.size();i++){
			v1=((DrawableStdFollowpoint)l.get(i)).getObj1().getHitObject();
			v2=((DrawableStdFollowpoint)l.get(i)).getObj2().getHitObject();
			if(!v2.isNewCombo())canvas.drawLines(new float[]{v1.getStartX(),v1.getStartY(),v2.getStartX(),v2.getStartY()},paint);
		}
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		int curTime=timeline.frameTime();
		//添加新的物件
		drawableHitObjects.prepareToDraw(curTime);
		connectionDrawables.prepareToDraw(curTime);
		
		drawConnectionLayer(canvas);
		//drawTestLayer(canvas);
		drawContentLayer(canvas);
		//drawApproachCircleLayer(canvas);
	}
	
	public class FieldDrawables{
		private List<DrawableStdHitObject> drawableHitObjects;

		private List<DrawableStdHitObject> hitObjectsInField=new ArrayList<DrawableStdHitObject>();

		private int savedIndex=0;
		
		public FieldDrawables(){
			
		}

		public void setObjectsInField(List<DrawableStdHitObject> hitObjectsInField) {
			this.hitObjectsInField=hitObjectsInField;
		}

		public List<DrawableStdHitObject> getObjectsInField() {
			return hitObjectsInField;
		}

		public void setObjects(List<DrawableStdHitObject> drawableHitObjects) {
			this.drawableHitObjects=drawableHitObjects;
		}

		public List<DrawableStdHitObject> getObjects() {
			return drawableHitObjects;
		}
		
		public void prepareToDraw(int curTime){
			pullNewAdded(curTime);
			deleteFinished();
		}
		
		private void showHitObject(DrawableStdHitObject obj){
			hitObjectsInField.add(obj);
			obj.onShow();
		}

		private void removeHitObject(DrawableStdHitObject obj){
			obj.onFinish();
		}

		private void pullNewAdded(int curTime){
			DrawableStdHitObject obj;
			for(;savedIndex<drawableHitObjects.size();savedIndex++){
				obj=drawableHitObjects.get(savedIndex);
				if(obj.getShowTime()<=curTime){
					showHitObject(obj);
				}else{
					break;
				}
			}
		}

		protected void deleteFinished(){
			Iterator<DrawableStdHitObject> iter=hitObjectsInField.iterator();
			DrawableStdHitObject obj;
			while(iter.hasNext()){
				obj=iter.next();
				if(obj.isFinished()){
					removeHitObject(obj);
					iter.remove();
				}
			}
		}
		
	}
}
