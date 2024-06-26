package com.example.greenleaf.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private final String date;
    @ColumnInfo(name = "sum")
    private final String sum;

    public Order( String date, String sum) {
        this.date = date;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getSum() {
        return sum;
    }
}
