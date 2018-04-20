package com.edplan.test.osbplayer;

import android.app.*;
import android.os.*;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.View;
import java.io.File;
import android.widget.Toast;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		final EditText osbDirView=(EditText)findViewById(R.id.mainEditText_OsbDir);
		final EditText osuFileView=(EditText)findViewById(R.id.mainEditText_OsuPath);
		Button startButton=(Button)findViewById(R.id.mainButton_Start);
		final CheckBox enableStoryboardBox=(CheckBox)findViewById(R.id.mainCheckBox_EnableStoryboard);
		final CheckBox enableGameplayBox=(CheckBox)findViewById(R.id.mainCheckBox_EnableGameplay);
		enableStoryboardBox.setChecked(true);
		enableGameplayBox.setChecked(true);
		
		startButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					// TODO: Implement this method
					String osbPath=osbDirView.getText().toString();
					String osuPath=osuFileView.getText().toString();
					boolean enableStoryboard=enableStoryboardBox.isChecked();
					boolean enableGameplay=enableGameplayBox.isChecked();
					
					File osb=new File(osbPath);
					
					if(!(enableStoryboard||enableGameplay)){
						toast("至少开启一个模式");
						return;
					}
					
					if(!osb.exists()){
						toast("osb文件或文件夹不存在");
						return;
					}
					
					if(osb.isDirectory()){
						boolean h;
						
						if(enableStoryboard){
							h=false;
							for(File s:osb.listFiles()){
								if(s.isFile()&&s.getName().endsWith(".osb")){
									osbPath=s.getAbsolutePath();
									h=true;
									break;
								}
							}
							if(!h){
								toast("文件夹下无.osb文件");
								return;
							}
							osbDirView.setText(osbPath);
						}
						
						if(enableGameplay){
							h=false;
							for(File s:osb.listFiles()){
								if(s.isFile()&&s.getName().endsWith(".osu")){
									osuPath=s.getAbsolutePath();
									h=true;
									break;
								}
							}
							if(!h){
								toast("文件夹下无.osu文件");
								return;
							}
							osuFileView.setText(osuPath);
						}
					}
					
					final String json="{enableStoryboard:"+enableStoryboard+",enableGameplay:"+enableGameplay+",osbPath:\""+osbPath+"\",osuPath:\""+osuPath+"\"}";
					toast("准备开始");
					p1.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO: Implement this method
								startGLActivity(json);
							}
						}, 1000);
				}
			});
    }
	
	public void toast(final String text){
		runOnUiThread(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	public void startGLActivity(String json){
		Intent intent=new Intent(this,GLActivity.class);
		Bundle b=new Bundle();
		b.putString(StaticData.InitialJSON,json);
		intent.putExtras(b);
		startActivity(intent);
	}
	
}
