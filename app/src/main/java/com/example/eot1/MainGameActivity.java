package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainGameActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    int i = 1;

    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        db = Room.databaseBuilder(this, MyDatabase.class, "my-db")
                .createFromAsset("databases/base228.db")
                .allowMainThreadQueries()
                .build();

        textView.setText(db.userDao().getSituationByIdPresent().get(i).situation);
    }


}