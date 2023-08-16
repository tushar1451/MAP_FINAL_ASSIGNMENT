package com.example.final_project.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.final_project.Model.Drink;

import java.util.List;

@Dao
public interface DrinkDAO {
    @Insert
    void insertNewDrink(Drink drink);

    @Query("SELECT * FROM Drink")
    List<Drink> getAll();
}