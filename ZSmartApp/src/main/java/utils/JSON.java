package utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.*;


public class JSON {

	static Logger logger = new CommonLogger(JSON.class).getLogger();
	
	
	public static JSONObject Create(int code, String msg) {

		JSONObject responseObj = new JSONObject();
		try {
			responseObj.put("statuscode", code);
			responseObj.put("message", msg);
		} catch (JSONException e) {
			logger.error("Json Exception on creating new errorObject");
		}
		
		return responseObj;
	}
		
		public static List<Object> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<Object> arrayList = new ArrayList<>();

        // Iterate through the JSONArray and add each element to the ArrayList
        for (int i = 0; i < jsonArray.length(); i++) {
            arrayList.add(jsonArray.get(i));
        }

        return arrayList;
		}
}
