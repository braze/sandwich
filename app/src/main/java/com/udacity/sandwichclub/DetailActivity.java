package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = "DetailActivity";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnowsAs;
    private TextView mPlaceOfOrigin;
    private TextView mIngredients;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAlsoKnowsAs = findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);


        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            Log.v(TAG, "intent = null");
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            Log.v(TAG, "EXTRA_POSITION not found in intent");

            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.v(TAG, "Sandwich data unavailable");

            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //prepare field "Also known as"
        String alsoKnowsAs = "";
        StringBuilder sb = new StringBuilder();
        for (String s : sandwich.getAlsoKnownAs()) {
            sb.append(s).append(" ");
        }
        alsoKnowsAs = sb.toString().trim();
        if (alsoKnowsAs.isEmpty()) {
            alsoKnowsAs = "Other names are unknown";
        }

        //prepare field ingredients
        String ingredients = "";
        //clear StringBuilder
        sb.setLength(0);

        for (String item : sandwich.getIngredients()) {
            sb.append(item).append(", ");
        }
        ingredients = sb.toString().trim();

        if (ingredients.isEmpty() || ingredients.length() < 2) {
            ingredients = "Unknown ingredients";
        } else {
            //get rid of comma in the end
            ingredients = ingredients.substring(0, ingredients.length() - 1);
        }

        //set TextViews
        mAlsoKnowsAs.setText(alsoKnowsAs);
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        mIngredients.setText(ingredients);
        mDescription.setText(sandwich.getDescription());
    }
}
