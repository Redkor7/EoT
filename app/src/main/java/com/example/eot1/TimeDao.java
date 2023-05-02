package com.example.eot1;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eot1.entities.Story;
import com.example.eot1.entities.Save;

import java.util.List;

@Dao
public interface TimeDao {
    @Query("SELECT * FROM Story")
    List<Story> getSituationById();

    @Query("SELECT * FROM Save")
    List<Save> getCurSaveId();

    @Update
    void update(Save save);
}
