package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.final_project.Model.Drink;
import com.example.final_project.database.DatabaseManager;
import com.example.final_project.database.DrinkDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {

    ArrayList<Drink> drinks = new ArrayList<>();
    RecyclerView recyclerView;
    DrinksAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;
    DrinkDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DatabaseManager.getDBInstance(this);

        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();

        networkingManager.listener = this;

        recyclerView = findViewById(R.id.drinksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        adapter = new DrinksAdapter(this, drinks);
//        recyclerView.setAdapter(adapter);

        networkingManager.searchForRandomMeal();
        setTitle("Our Drinks");
    }

    @Override
    public void dataListener(String jsonString) {
        drinks = jsonService.getMealsFromJSON(jsonString);
        adapter = new DrinksAdapter(this, drinks, new DrinksAdapter.drinkClickListener() {
            @Override
            public void drinkClicked(Drink selectedDrink) {
                Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("drink", selectedDrink);
                intent.putExtra("bundle", bundle);

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.favorites) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void imageListener(Bitmap image) {

    }

//    @Override
//    public void drinkClicked(Drink selectedMeal) {
//
//    }

}