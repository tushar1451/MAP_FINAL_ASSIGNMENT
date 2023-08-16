package com.example.final_project;

import com.example.final_project.Model.Drink;

import org.json.*;
import java.util.ArrayList;

public class JsonService {

    public ArrayList<Drink> getMealsFromJSON(String json)  {
        ArrayList<Drink> arrayList = new ArrayList<>(0);
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("drinks");

            for (int i = 0 ; i< arr.length(); i++) {
                String id = arr.getJSONObject(i).getString("idDrink");
                String drinkName = arr.getJSONObject(i).getString("strDrink");
                String category = arr.getJSONObject(i).getString("strCategory");
                String area = arr.getJSONObject(i).getString("strAlcoholic");
                String image = arr.getJSONObject(i).getString("strDrinkThumb");
                String instruction = arr.getJSONObject(i).getString("strInstructions");

                Drink drink = new Drink(drinkName, category, area, image, instruction);
                arrayList.add(drink);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}