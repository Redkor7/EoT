package com.example.eot1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.eot1.entities.Story;
import com.example.eot1.entities.Save;

@Database(entities = {Story.class, Save.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TimeDao userDao();
}