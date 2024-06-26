package com.example.greenleaf.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.greenleaf.entity.FavoriteProduct;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.entity.Product;

import java.util.List;

@Dao
public interface FavoriteProductDao {
    @Query("SELECT * FROM `favoriteproduct`")
    List<Product> getAll();

    @Query("SELECT * FROM `favoriteproduct` WHERE name = (:name)")
    FavoriteProduct getProductByName(String name);

    @Insert
    void insertAll(FavoriteProduct... product);

    @Insert
    void insert(FavoriteProduct product);

    @Delete
    void delete(FavoriteProduct product);


    @Query("DELETE FROM `favoriteproduct`")
    void deleteAll();
}