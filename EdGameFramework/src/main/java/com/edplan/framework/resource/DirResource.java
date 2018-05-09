package com.edplan.framework.resource;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class DirResource extends AResource
{
	private File dir;
	
	public DirResource(File dir){
		this.dir=dir;
	}
	
	public DirResource(String absPath){
		this(new File(absPath));
	}
	
	@Override
	public InputStream openInput(String path) throws IOException {
		// TODO: Implement this method
		File f=new File(dir,path);
		if((!f.exists())||f.isDirectory()){
			return null;
			//throw new RuntimeException("err open input: "+f.exists()+","+f.isDirectory()+","+f.getAbsolutePath());
		}
		return new FileInputStream(f);
	}

	@Override
	public AResource subResource(String path) {
		// TODO: Implement this method
		return new DirResource(new File(dir,path));
	}

	@Override
	public String[] list(String rdir) throws IOException
	{
		// TODO: Implement this method
		if(rdir==null||rdir.length()==0){
			return dir.list();
		}else{
			File f=new File(dir,rdir);
			return f.exists()?f.list():new String[0];
		}
	}

	@Override
	public boolean contain(String file) {
		// TODO: Implement this method
		return (new File(dir,file)).exists();
	}
}
