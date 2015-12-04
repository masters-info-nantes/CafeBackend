package fr.alma.mw1516.services.backend.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

	protected JSONObject obj;
	
	public JsonParser(){
		super();
	}
	
	public String fetchJsonData(Object ObjectJson , String neededAtt) throws JSONException{
		
		JSONObject jsonObject = (JSONObject) ObjectJson;
		String name = (String) jsonObject.get(neededAtt);		
		return name;
	}
	
}
