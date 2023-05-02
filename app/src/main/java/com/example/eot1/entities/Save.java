package com.example.eot1.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Save")
public class Save {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "cur_id")
    public int cur_id;

    @NonNull
    @ColumnInfo(name = "HP")
    public Integer HP;
}
