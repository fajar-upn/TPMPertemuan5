package com.example.room.Model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDiriDao {
    @Insert
    Long insertData(DataDiri dataDiri);

    @Query("SELECT * FROM user_db")
    List<DataDiri> getData();

    @Update
    int updateData(DataDiri item);

    @Delete
    void deleteData(DataDiri item);
}
