package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.eot1.entities.Save;

public class MainActivity extends AppCompatActivity {

    Button start, exit, contin;
    Save save = new Save();
    Intent intent;
    MyDatabase db;
    Animation anim;
    MediaPlayer btnClick;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MainGameActivity.class);
        anim = AnimationUtils.loadAnimation(this, R.anim.scale);

        save.id = 1;
        save.cur_id = 1;
        save.HP = 3;
        btnClick = MediaPlayer.create(this, R.raw.soundbtn);

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
                btnClick.start();
                start.startAnimation(anim);
                db.userDao().update(save);
                startActivity(intent);
                overridePendingTransition(R.anim.da, R.anim.d);
            }
        });

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contin.startAnimation(anim);
                btnClick.start();
                if (db.userDao().getCurSaveId().get(0).cur_id == 1)
                    Toast.makeText(getBaseContext(), "Сначала начните историю", Toast.LENGTH_LONG).show();
                else {
                    startActivity(intent);
                    overridePendingTransition(R.anim.da, R.anim.d);
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.start();
                exit.startAnimation(anim);
                finishAffinity();
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