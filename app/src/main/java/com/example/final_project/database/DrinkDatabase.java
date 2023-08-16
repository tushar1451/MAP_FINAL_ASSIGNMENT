package com.example.final_project.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.final_project.Model.Drink;

@Database(version = 1,entities = {Drink.class})
public abstract class DrinkDatabase extends RoomDatabase{

    abstract public DrinkDAO getDrinkDAO();

}