package com.yongyida.robot.qrcode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.TextUtils;
import android.util.Log;

import com.yongyida.robot.qrcode.bean.User;

public class JsonUtils {

	public static List<User> getUser(String json){
		List<User> list = new ArrayList<User>();
		try {
			//JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(json);
			Log.i("JSON", "jsonResult" + joResult);
			if(joResult.has("Users")){
				JSONArray array = new JSONArray(joResult.optString("Users"));
				if(array != null){
					for(int i = 0; i < array.length(); i++){
						JSONObject jsonObject = array.getJSONObject(i);
						Long id = jsonObject.getLong("id");
						String name = jsonObject.optString("name");
						String nick = jsonObject.optString("nickname");
						Log.i("JSON", "nick" + nick);
						Long controller = jsonObject.getLong("controller");
						User user = new User();
						if(id != 0){
						user.setId(id);
						}
						if(!TextUtils.isEmpty(name)){
							user.setName(name);
						}
						if(!TextUtils.isEmpty(nick)){
							user.setNickName(nick);
						}
						user.setController(controller);
						list.add(user);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
}
