package com.edplan.osulab.ui;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.drawable.ColorDrawable;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import java.io.IOException;

public class MainBackground extends RelativeContainer
{
	private AbstractTexture testTexture;
	
	public MainBackground(MContext c){
		super(c);
		ColorDrawable cd=new ColorDrawable(c);
		float t=0.1f,b=0.1f;
		cd.setColor(Color4.gray(t),Color4.gray(t),
					Color4.gray(b),Color4.gray(b));
		setBackground(cd);
		
		try{
			testTexture=getContext().getAssetResource().loadTexture("osu/ui/menu-background-1.jpg");
		}catch(IOException e){
			e.printStackTrace();
			
		}

	}
	
}
