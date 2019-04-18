package com.sklagat46.mcrop.data.local.dao;


import com.sklagat46.mcrop.data.model.Vegetable;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface VegetableDao extends BaseDao<Vegetable> {
    @Query("SELECT * FROM  Vegetable")
    List<Vegetable> getAll();

}
