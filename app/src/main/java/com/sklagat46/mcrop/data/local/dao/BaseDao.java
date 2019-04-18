package com.sklagat46.mcrop.data.local.dao;


import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;


public interface BaseDao<T> {

  @Insert(onConflict = REPLACE)
  void insert(T item);

  @Insert(onConflict = REPLACE)
  void insert(T... items);

  @Insert(onConflict = REPLACE)
  void insert(List<T> items);

  @Update(onConflict = REPLACE)
  void update(T item);

  @Delete
  void delete(T item);
}
