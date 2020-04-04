package com.sklagat46.mcrop.data.local.dao;


import com.sklagat46.mcrop.data.model.Fruits;
import com.sklagat46.mcrop.data.model.Vegetable;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FruitsDao extends BaseDao<Fruits> {
    @Query("SELECT * FROM  Fruits")
    LiveData<List<Fruits>> getAll();

}
