package com.example.final_project.database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.example.final_project.Model.Drink;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {
    static DrinkDatabase db;
    ExecutorService databaseExecuter = Executors.newFixedThreadPool(4);
    Handler db_handler = new Handler(Looper.getMainLooper());

    public interface DatabaseListener {
        void databaseAllDrinkListener(List<Drink> list);
    }

    public DatabaseListener listener;


    private static void BuildDBInstance (Context context) {
        db = Room.databaseBuilder(context, DrinkDatabase.class,"drink_db").build();
    }
    public static DrinkDatabase getDBInstance(Context context){
        if (db == null){
            BuildDBInstance(context);
        }
        return db;
    }

    public void insertNewDrink(Drink d){
        databaseExecuter.execute(new Runnable() {
            @Override
            public void run() {
                db.getDrinkDAO().insertNewDrink(d);
            }
        });
    }

    public void getAllDrinks(){
        databaseExecuter.execute(new Runnable() {
            @Override
            public void run() {
                List<Drink> list =  db.getDrinkDAO().getAll();
                db_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseAllDrinkListener(list);
                    }
                });
            }
        });
    }
}
