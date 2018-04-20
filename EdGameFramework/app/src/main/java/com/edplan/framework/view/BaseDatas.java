package com.edplan.framework.view;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Environment;
import android.view.WindowManager;
import java.io.File;

public class BaseDatas
{
	public static Context context;
	
	
	
	public static void initial(Context _context){
		context=_context;
	}
	
	public static void putString(String key,String s){
		getMainSharedPreferences().edit().putString(key,s).apply();
	}

	public static void putInt(String key,int value){
		getMainSharedPreferences().edit().putInt(key,value).apply();
	}

	public static void putFloat(String key,float v){
		getMainSharedPreferences().edit().putFloat(key,v).apply();
	}

	public static float getFloat(String key){
		return getMainSharedPreferences().getFloat(key,-1);
	}

	public static int getInt(String key){
		return getMainSharedPreferences().getInt(key,-1);
	}

	public static String getString(String key){
		return getMainSharedPreferences().getString(key,null);
	}

	public static SharedPreferences getMainSharedPreferences(){
		return context.getSharedPreferences("main",0);
	}



	public static File getMainDir(){
		File d= new File(Environment.getExternalStorageDirectory(),"夏日绘版");
		if(!d.exists())d.mkdirs();
		return d;
	}

	public static File getMainPictureDir(){
		File d=new File(getMainDir(),"Pictures");
		if(!d.exists())d.mkdirs();
		return d;
	}


	public static float pixelToDp(float pxValue){   
        final float scale = context.getResources().getDisplayMetrics().density;   
        return (pxValue / scale);   
	}   

	public static float dpToPixel(float dipValue){   
        final float scale = context.getResources().getDisplayMetrics().density;   
        return (dipValue * scale);   
	}
}
