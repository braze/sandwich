package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        String mainName = "";
        ArrayList<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        ArrayList<String> ingredients = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(json);

            JSONObject name = obj.getJSONObject("name");
            mainName = name.getString("mainName");
            Log.v(TAG,mainName);

            JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
                Log.v(TAG,alsoKnownAs.get(i));
            }

            placeOfOrigin = obj.getString("placeOfOrigin");
            Log.v(TAG,placeOfOrigin);
            description = obj.getString("description");
            Log.v(TAG,description);
            image = obj.getString("image");
            Log.v(TAG,image);

            JSONArray ingredientsJsonArray = obj.getJSONArray("ingredients");
            for (int i = 0; i <ingredientsJsonArray.length() ; i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
                Log.v(TAG,ingredients.get(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
