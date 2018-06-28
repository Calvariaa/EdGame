package com.edplan.framework.config.property;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfigString extends ConfigProperty
{
	public static final String TYPE="string";

	static{
		ConfigProperty.registerLoader(TYPE,new ConfigProperty.Loader(){
				@Override
				public ConfigProperty load(JSONObject obj){
					// TODO: Implement this method
					ConfigBoolean b=new ConfigBoolean();
					b.injectJson(obj);
					return b;
				}
			});
	}

	private String data;

	public String get(){
		return data;
	}

	public void set(String b){
		this.data=b;
	}

	@Override
	public String propertyType(){
		// TODO: Implement this method
		return TYPE;
	}

	@Override
	public JSONObject asJson(){
		// TODO: Implement this method
		JSONObject obj=new JSONObject();
		try{
			obj.put("data",data);
		}catch(JSONException e){}
		return obj;
	}

	@Override
	public void injectJson(JSONObject data){
		// TODO: Implement this method
		set(data.optString("data"));
	}
}
