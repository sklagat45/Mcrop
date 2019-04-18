package com.sklagat46.mcrop.data.local;


import com.sklagat46.mcrop.data.local.dao.FruitsDao;
import com.sklagat46.mcrop.data.local.dao.VegetableDao;
import com.sklagat46.mcrop.data.model.Fruits;
import com.sklagat46.mcrop.data.model.Vegetable;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = {
        Fruits.class,
        Vegetable.class
}, version = 1, exportSchema = false)

@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {
    public abstract FruitsDao fruitsDao();
    public abstract VegetableDao vegetableDao();
}
