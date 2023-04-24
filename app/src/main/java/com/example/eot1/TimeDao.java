package com.example.eot1;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eot1.entities.Future;
import com.example.eot1.entities.Past;
import com.example.eot1.entities.Present;
import com.example.eot1.entities.Save;

import java.util.List;

@Dao
public interface TimeDao {
    @Query("SELECT * FROM Present")
    List<Present> getSituationByIdPresent();

    @Query("SELECT * FROM Past")
    List<Past> getSituationByIdPast();

    @Query("SELECT * FROM Future")
    List<Future> getSituationByIdFuture();

    @Update
    void update(Save save);
}
