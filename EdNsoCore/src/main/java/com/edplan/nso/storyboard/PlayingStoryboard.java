package com.edplan.nso.storyboard;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.nso.storyboard.StoryboardLayer;
import java.util.HashMap;
import java.util.Map;
import com.edplan.framework.resource.AResource;
import com.edplan.framework.graphics.opengl.objs.texture.TexturePool;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import java.io.IOException;
import com.edplan.framework.resource.BufferedListResource;
import com.edplan.framework.graphics.opengl.objs.texture.AutoPackTexturePool;
import com.edplan.nso.storyboard.elements.IStoryboardElements;
import java.io.File;
import com.edplan.framework.graphics.opengl.shader.advance.ColorShader;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.nso.storyboard.elements.StoryboardSprite;
import com.edplan.framework.fallback.GLES10Drawable;
import com.edplan.framework.graphics.opengl.GL10Canvas2D;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.test.Test;
import com.edplan.nso.resource.OsuSkin;

public class PlayingStoryboard extends EdDrawable implements GLES10Drawable
{
	private HashMap<String,PlayingStoryboardLayer> layers=new HashMap<String,PlayingStoryboardLayer>();
	
	private PreciseTimeline timeline;
	
	private BufferedListResource resource;
	
	public TexturePool pool;
	
	public Storyboard storyboard;
	
	public PlayingStoryboard(MContext context,PreciseTimeline timeline,Storyboard storyboard,AResource resource,OsuSkin skin){
		super(context);
		this.storyboard=storyboard;
		this.resource=new BufferedListResource(resource);
		this.resource.setIgnoreCase(true);
		//this.resource.printDetails();
		//new BufferedListResource(resource.subResource("SB/Font")).printDetails();
		//this.resource.printDetails();
		loadPool();
		this.timeline=timeline;
		for(Map.Entry<String,StoryboardLayer> l:storyboard.getLayers().entrySet()){
			layers.put(l.getKey(),new PlayingStoryboardLayer(l.getValue(),this));
		}
	}
	
	protected void loadPool(){
		TexturePool rawPool=new TexturePool(new TexturePool.TextureLoader(){
				@Override
				public AbstractTexture load(String msg) {
					// TODO: Implement this method
					try {
						//System.out.println("on load texture: "+msg);
						return PlayingStoryboard.this.resource.loadTexture(msg);
					} catch (IOException e) {
						e.printStackTrace();
						//return null;
						throw new RuntimeException(e.getMessage());
					}
				}
			});
		for(Map.Entry<String,StoryboardLayer> l:storyboard.getLayers().entrySet()){
			for(IStoryboardElements ele:l.getValue().getElements()){
				for(String res:ele.getTexturePaths()){
					rawPool.getTexture(res);
				}
			}
		}
		if(GLWrapped.GL_VERSION>=2){
			pool=new AutoPackTexturePool(new TexturePool.TextureLoader(){
					@Override
					public AbstractTexture load(String msg) {
						// TODO: Implement this method
						try {
							//System.out.println("on load texture: "+msg);
							return PlayingStoryboard.this.resource.loadTexture(msg);
						} catch (IOException e) {
							e.printStackTrace();
							//return null;
							throw new RuntimeException(e.getMessage());
						}
					}
				},getContext());
			pool.addAll(rawPool.getAll());
			if(!Test.get().getBoolean(Test.IS_RELEASE,true))((AutoPackTexturePool)pool).writeToDir(new File("/storage/emulated/0/MyDisk/bin/pool/1"),"pool",null);
		}else{
			pool=rawPool;
		}
		
	}
	
	public PlayingStoryboardLayer getLayer(String key){
		return layers.get(key);
	}
	
	public int objectsInField(){
		int c=0;
		for(PlayingStoryboardLayer l:layers.values()){
			c+=l.objectInField();
		}
		return c;
	}
	
	public int newApply(){
		int c=0;
		for(PlayingStoryboardLayer l:layers.values()){
			c+=l.getNewApply();
		}
		return c;
	}
	
	public TexturePool getTexturePool(){
		return pool;
	}

	public void setTimeline(PreciseTimeline timeline) {
		this.timeline=timeline;
	}

	public PreciseTimeline getTimeline() {
		return timeline;
	}

	@Override
	public void draw(BaseCanvas canvas) {
		// TODO: Implement this method
		int i=canvas.save();
		//canvas.getData().getShaders().setColorShader(ColorShader.Invalid);
		//canvas.getData().getShaders().setTexture3DShader(Texture3DShader.Invalid);
		for(Map.Entry<String,PlayingStoryboardLayer> e:layers.entrySet()){
			e.getValue().draw(canvas);
		}
		canvas.restoreToCount(i);
	}

	@Override
	public void drawGL10(GL10Canvas2D canvas) {
		// TODO: Implement this method
		int i=canvas.save();
		//canvas.getData().getShaders().setColorShader(ColorShader.Invalid);
		//canvas.getData().getShaders().setTexture3DShader(Texture3DShader.Invalid);
		for(Map.Entry<String,PlayingStoryboardLayer> e:layers.entrySet()){
			e.getValue().drawGL10(canvas);
		}
		canvas.restoreToCount(i);
	}
}
