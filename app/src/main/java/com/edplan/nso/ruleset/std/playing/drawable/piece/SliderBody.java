package com.edplan.nso.ruleset.std.playing.drawable.piece;
import android.graphics.Bitmap;
import android.graphics.Color;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.line.DrawLinePath;
import com.edplan.framework.graphics.line.LinePath;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.drawable.BufferedDrawable;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.nso.ruleset.std.objects.StdSlider;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdSlider;

public class SliderBody extends BasePiece
{
	private BufferedDrawable sliderBuffered;
	
	private float progress1=0;
	
	private float progress2=0;
	
	private StdSlider slider;
	
	private LinePath sliderPath;
	
	public SliderBody(MContext c,PreciseTimeline t,DrawableStdSlider sld){
		super(c,t);
		this.slider=(StdSlider)sld.getHitObject();
		sliderPath=sld.getPath();
	}
	
	public Vec2 getCurrentHeadPoint(){
		return sliderPath.getMeasurer().atLength((float)(slider.getPixelLength()*getProgress1()));
	}

	public Vec2 getCurrentEndPoint(){
		return sliderPath.getMeasurer().atLength((float)(slider.getPixelLength()*getProgress2()));
	}
	
	public Vec2 getPointAt(float length){
		return sliderPath.getMeasurer().atLength(length);
	}

	@Override
	public void setAlpha(float a) {
		// TODO: Implement this method
		super.setAlpha(a);
		if(sliderBuffered!=null)sliderBuffered.setAlpha(a);
	}

	public LinePath getSliderPath() {
		return sliderPath;
	}

	@Override
	public void setSkin(OsuSkin skin) {
		// TODO: Implement this method
		super.setSkin(skin);
		sliderBuffered=new BufferedSliderDrawable(getContext());
		sliderBuffered.setArea(sliderPath.getDrawArea());
	}

	public void setProgress1(float progress1) {
		if(this.progress1!=progress1){
			this.progress1=progress1;
			sliderBuffered.postUpdate();
		}
	}

	public float getProgress1() {
		return progress1;
	}

	public void setProgress2(float progress2) {
		if(this.progress2!=progress2){
			this.progress2=progress2;
			sliderBuffered.postUpdate();
		}
	}

	public float getProgress2() {
		return progress2;
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		sliderBuffered.draw(canvas);
	}
	
	public class BufferedSliderDrawable extends BufferedDrawable{
		
		private Texture3DBatch<TextureVertex3D> batch=new Texture3DBatch<TextureVertex3D>();
		
		private GLTexture sliderPathTexture;
		
		public BufferedSliderDrawable(MContext c){
			super(c);
			updateTexture();
			setAlpha(0);
		}
		
		public void updateTexture(){
			float aa_portion = 0.02f;
            float border_portion = 0.128f;
            float gradient_portion = 1 - border_portion;

            float opacity_at_centre = 0.3f;
            float opacity_at_edge = 0.8f;

			Bitmap bmp=Bitmap.createBitmap(512,1,Bitmap.Config.ARGB_8888);
			for(int x=0;x<bmp.getWidth();x++){
				float v=1-x/(float)(bmp.getWidth()-1);

				if(v<=border_portion){
					bmp.setPixel(x,0,Color.argb((int)(Math.min(v/aa_portion,1)*255),255,255,255));
				}else{
					v-=border_portion;
					bmp.setPixel(x,0,Color.argb(
									 (int)((opacity_at_edge-(opacity_at_edge-opacity_at_centre)*v/gradient_portion)*255),
									 255,255,255));
				}
			}
			
			if(sliderPathTexture!=null)sliderPathTexture.delete();
			sliderPathTexture=GLTexture.create(bmp);
		}

		public void setSliderPathTexture(GLTexture sliderPathTexture) {
			this.sliderPathTexture=sliderPathTexture;
		}

		public GLTexture getSliderPathTexture() {
			return sliderPathTexture;
		}

		@Override
		protected void drawContent(GLCanvas2D canvas) {
			// TODO: Implement this method
			canvas.drawColor(Color4.Alpha);
			canvas.clearDepthBuffer();
			DrawLinePath<Texture3DBatch> d=new DrawLinePath<Texture3DBatch>(
								getSliderPath()
								 .cutPath(
								 	(float)(slider.getPixelLength()*getProgress1()),
									(float)(slider.getPixelLength()*getProgress2())
									)
								);
			batch.clear();
			d.addToBatch(batch);
			GLWrapped.depthTest.save();
			GLWrapped.blend.save();
			GLWrapped.depthTest.set(true);
			GLWrapped.blend.set(false);
			canvas.drawTexture3DBatch(batch,sliderPathTexture,1,Color4.White);
			GLWrapped.blend.restore();
			GLWrapped.depthTest.restore();
		}
	}
}
