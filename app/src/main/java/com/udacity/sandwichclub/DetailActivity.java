package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {


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
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
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
        String ingredients = "";
        String alsoKnowsAs = "";
        String place = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();

        alsoKnowsAs = TextUtils.join(", ", sandwich.getAlsoKnownAs());
        if (alsoKnowsAs.isEmpty()) {
            alsoKnowsAs = getString(R.string.also_known_is_unknown);
        }

        ingredients = TextUtils.join(", ", sandwich.getIngredients());
        if (ingredients.isEmpty() || ingredients.length() < 2) {
            ingredients = getString(R.string.unknown_ingredients);
        }

        //check for place
        if (place.isEmpty()) {
            place = getString(R.string.unknown_plase);
        }

        //check for description
        if (description.isEmpty()) {
            description = getString(R.string.no_description);
        }

        //set TextViews
        mAlsoKnowsAs.setText(alsoKnowsAs);
        mPlaceOfOrigin.setText(place);
        mIngredients.setText(ingredients);
        mDescription.setText(description);
    }
}
