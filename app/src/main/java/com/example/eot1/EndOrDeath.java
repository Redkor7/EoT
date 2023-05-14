package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import android.content.Intent;
import android.media.MediaPlayer;
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
    ConstraintLayout layout;
    MediaPlayer btnClick;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_or_death);

        intent = new Intent(this, MainActivity.class);
        intent2 = new Intent(this, MainGameActivity.class);

        layout = findViewById(R.id.endordeath);
        btnClick = MediaPlayer.create(this, R.raw.soundbtn);

        start = findViewById(R.id.b_start2);
        menu = findViewById(R.id.b_menu);
        endOrDeatht = findViewById(R.id.endordeatht);

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        endOrDeatht.setText(getIntent().getStringExtra("end"));

        if (db.userDao().getCurSaveId().get(0).HP == 2){
            layout.setBackgroundResource(R.drawable.death);
        }

        if (db.userDao().getCurSaveId().get(0).cur_id >= 11 && db.userDao().getCurSaveId().get(0).cur_id < 27)
            start.setText("Продолжить с контрольной точки");
        else
            start.setText("Начать с начала");

        menu.setText("Выйти в меню");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.start();
                startActivity(intent2);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.start();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            finishAffinity();
        back_pressed = System.currentTimeMillis();
    }
}