package com.example.eot1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.eot1.entities.Future;
import com.example.eot1.entities.Past;
import com.example.eot1.entities.Present;
import com.example.eot1.entities.Save;

@Database(entities = {Present.class, Past.class, Future.class, Save.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TimeDao userDao();
}