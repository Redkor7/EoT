package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eot1.entities.Save;

public class MainActivity extends AppCompatActivity {

    Button start, exit, contin;
    Save save = new Save();
    Intent intent;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MainGameActivity.class);

        save.id = 1;
        save.cur_id = 1;
        save.HP = 3;

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        start = findViewById(R.id.b_start2);
        exit = findViewById(R.id.b_exit);
        contin = findViewById(R.id.b_contin);

        if (db.userDao().getCurSaveId().get(0).cur_id != 1)
            start.setText("Начать сначала");
        else
            start.setText("Начать игру");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.userDao().update(save);
                startActivity(intent);
            }
        });

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}