package com.example.greenleaf.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.greenleaf.entity.FavoriteProduct;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.entity.Product;

@Database(entities = {Product.class, Order.class, FavoriteProduct.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();

    public abstract FavoriteProductDao favoriteProductDao();
}
