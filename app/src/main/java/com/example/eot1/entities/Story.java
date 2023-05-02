package com.example.eot1.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Story")
public class Story {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "situation")
    public String situation;

    @ColumnInfo(name = "choice1")
    public String choice1;

    @ColumnInfo(name = "choice2")

    public String choice2;

    @ColumnInfo(name = "implications1")
    public String implications1;

    @ColumnInfo(name = "implications2")
    public String implications2;

    @ColumnInfo(name = "time")
    public String time;
}
