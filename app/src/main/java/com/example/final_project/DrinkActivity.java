package com.example.final_project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.Model.Drink;
import com.example.final_project.database.DatabaseManager;
import com.example.final_project.database.DrinkDatabase;

public class DrinkActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {
    TextView drinkNameText;
    TextView categoryText;
    TextView areaText;
    ImageView imageView;
    Button addToFavorite;

    NetworkingService networkingManager;
    DatabaseManager dbManager;
    DrinkDatabase db;

    Drink drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        networkingManager = ((myApp) getApplication()).getNetworkingService();
        networkingManager.listener = this;

        db = DatabaseManager.getDBInstance(this);
        dbManager = ((myApp) getApplication()).getDatabaseManager();

        if (getIntent().hasExtra("bundle")) {
            Bundle bundle = getIntent().getBundleExtra("bundle");
            drink = bundle.getParcelable("drink");
        }

        drinkNameText = findViewById(R.id.drinkNameText);
        categoryText = findViewById(R.id.categoryText);
        areaText = findViewById(R.id.areaText);
        imageView = findViewById(R.id.imageView);
        addToFavorite = findViewById(R.id.addToFavorite);

        drinkNameText.setText("Drink Name: " + drink.getMealName());
        categoryText.setText("Category: " + drink.getCategory());
        areaText.setText("Type: " + drink.getArea());

        networkingManager.getImageData(drink.getImage());

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.insertNewDrink(drink);

                // TODO: go back to previous page
            }
        });
    }

    @Override
    public void dataListener(String jsonString) {
    }

    @Override
    public void imageListener(Bitmap image) {
        imageView.setImageBitmap(image);
    }
}
