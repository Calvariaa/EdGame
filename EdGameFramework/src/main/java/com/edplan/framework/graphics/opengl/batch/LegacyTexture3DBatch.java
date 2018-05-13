package com.edplan.framework.graphics.opengl.batch;
import com.edplan.framework.graphics.opengl.batch.interfaces.ITexture3DBatch;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import java.nio.FloatBuffer;

public class LegacyTexture3DBatch implements ITexture3DBatch<TextureVertex3D>
{

	@Override
	public TextureVertex3D createNext()
	{
		// TODO: Implement this method
		return null;
	}


	@Override
	public void add(TextureVertex3D t)
	{
		// TODO: Implement this method
	}

	@Override
	public void add(TextureVertex3D[] ts)
	{
		// TODO: Implement this method
		for(TextureVertex3D t:ts)
			add(t);
	}

	@Override
	public int getVertexCount()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void clear()
	{
		// TODO: Implement this method
	}

	@Override
	public FloatBuffer makePositionBuffer()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public FloatBuffer makeColorBuffer()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public FloatBuffer makeTexturePositionBuffer()
	{
		// TODO: Implement this method
		return null;
	}

}
