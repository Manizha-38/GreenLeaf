package com.example.greenleaf.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.greenleaf.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order`")
    List<Order> getAll();

    @Insert
    void insertAll(Order... orders);

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);


    @Query("DELETE FROM `order`")
    void deleteAll();
}
