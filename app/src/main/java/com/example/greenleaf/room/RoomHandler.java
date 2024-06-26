package com.example.greenleaf.room;

import android.content.Context;

import androidx.room.Room;

public class RoomHandler {
    private static RoomHandler mInstance;
    private final Context mCtx;
    private final AppDatabase appDatabase;

    private RoomHandler(Context mCtx, String name) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, name)
                .build();
    }

    public static RoomHandler getInstance(Context mCtx, String name) {
        if (mInstance == null) {
            mInstance = new RoomHandler(mCtx, name);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
