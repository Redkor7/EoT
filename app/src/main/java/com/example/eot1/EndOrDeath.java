package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EndOrDeath extends AppCompatActivity {

    Button start, menu;
    Intent intent, intent2;
    MyDatabase db;
    TextView endOrDeatht;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_or_death);

        intent = new Intent(this, MainActivity.class);
        intent2 = new Intent(this, MainGameActivity.class);

        start = findViewById(R.id.b_start2);
        menu = findViewById(R.id.b_menu);
        endOrDeatht = findViewById(R.id.endordeatht);

        endOrDeatht.setText(getIntent().getStringExtra("end"));

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        if (db.userDao().getCurSaveId().get(0).cur_id >= 11 && db.userDao().getCurSaveId().get(0).cur_id <= 27)
            start.setText("Продолжить с контрольной точки");
        else
            start.setText("Начать с начала");

        menu.setText("Выйти в меню");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else
            Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}