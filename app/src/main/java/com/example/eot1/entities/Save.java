package com.example.eot1.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Save")
public class Save {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "cur_id")
    public int cur_id;

    @ColumnInfo(name = "HP")
    public int HP;
}
