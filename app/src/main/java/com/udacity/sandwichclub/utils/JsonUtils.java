package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

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

            JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            placeOfOrigin = obj.getString("placeOfOrigin");
            description = obj.getString("description");
            image = obj.getString("image");

            JSONArray ingredientsJsonArray = obj.getJSONArray("ingredients");
            for (int i = 0; i <ingredientsJsonArray.length() ; i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
