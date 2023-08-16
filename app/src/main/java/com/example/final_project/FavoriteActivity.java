package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.Model.Drink;
import com.example.final_project.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener {
    ArrayList<Drink> listFromDB = new ArrayList<>();
    RecyclerView recyclerView;
    DrinksAdapter adapter;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.list_of_drinks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbManager = ((myApp) getApplication()).getDatabaseManager();
        dbManager.listener = this;

        dbManager.getAllDrinks();
        setTitle("My favorites");
    }


    @Override
    public void databaseAllDrinkListener(List<Drink> list) {
        listFromDB = new ArrayList<>(list);

        adapter = new DrinksAdapter(this, listFromDB, new DrinksAdapter.drinkClickListener() {
            @Override
            public void drinkClicked(Drink selectedDrink) {
                // TODO: show instruction

                Intent intent = new Intent(getApplicationContext(), InstructionActivity.class
                );

                Bundle bundle = new Bundle();
                bundle.putString("instruction", selectedDrink.getInstruction());
                intent.putExtra("bundle", bundle);

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
