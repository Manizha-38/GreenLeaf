package com.example.greenleaf.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.greenleaf.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE name = (:name)")
    Product getProductByName(String name);


    @Insert
    void insertAll(Product... products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);


    @Query("DELETE FROM product")
    void deleteAll();
}