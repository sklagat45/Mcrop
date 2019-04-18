package com.sklagat46.mcrop.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.sklagat46.mcrop.data.local.Database;
import com.sklagat46.mcrop.data.model.Fruits;
import com.sklagat46.mcrop.data.model.Vegetable;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class McropRepository {

    private String DB_NAME = "mcrop_db";

    private Database database;
    public McropRepository(Context context) {
        database = Room.databaseBuilder(context, Database.class, DB_NAME).build();
    }

    public List<Vegetable> getAllVegetables() {
        return database.vegetableDao().getAll();
    }
    public LiveData<List<Fruits>> getAllFruits() {
        return database.fruitsDao().getAll();
    }

    public void addVegetable(Vegetable vegetable) {
        database.vegetableDao().insert(vegetable);
    }

    public void addFruits(Fruits fruits) {
        database.fruitsDao().insert(fruits);
    }





}