package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eot1.entities.Save;

import java.util.Objects;

public class MainGameActivity extends AppCompatActivity {

    ImageButton choice1, choice2;
    TextView situation, choice1t, choice2t, hp;

    Animation anim_emer, anim_ext;

    Save save = new Save();

    Integer id = 1, flag = 0;

    Handler handler = new Handler();

    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        anim_emer = AnimationUtils.loadAnimation(this, R.anim.txt_anim_emerg);
        anim_ext = AnimationUtils.loadAnimation(this, R.anim.txt_anim_extin);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        situation = findViewById(R.id.situation);
        choice1t = findViewById(R.id.choice1t);
        choice2t = findViewById(R.id.choice2t);
        hp = findViewById(R.id.hp);

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        save.cur_id = 3;
        save.id = 1;
        save.HP = 5;

        // db.userDao().update(save);

        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
        choice1t.setText(db.userDao().getSituationById().get(id - 1).choice1);
        choice2t.setText(db.userDao().getSituationById().get(id - 1).choice2);

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0)
                    ChangesStart(choice1);
                else
                    ChangesEnd(choice1);
                //        hp.setText(db.userDao().getCurSaveId().get(0).HP.toString());
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0)
                    ChangesStart(choice2);
                else {
                    ChangesEnd(choice2);
                }
                //             hp.setText(id.toString());
            }
        });

    }

    public void ChangesStart(View v) {
        switch (v.getId()) {
            case (R.id.choice1):
                this.flag = 1;
                situation.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).implications1);
                        situation.startAnimation(anim_emer);

                        choice1t.setText("Продолжить");
                        choice1t.startAnimation(anim_emer);

                        choice1.setClickable(true);
                    }
                }, 850);

                choice2.setVisibility(View.INVISIBLE);
                choice2.setClickable(false);
                choice2.startAnimation(anim_ext);

                choice2t.startAnimation(anim_ext);
                choice2t.setVisibility(View.INVISIBLE);

                choice1t.startAnimation(anim_ext);
                choice1.setClickable(false);
                break;

            case (R.id.choice2):
                this.flag = 1;
                situation.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).implications2);
                        situation.startAnimation(anim_emer);

                        choice2t.setText("Продолжить");
                        choice2t.startAnimation(anim_emer);

                        choice2.setClickable(true);
                    }
                }, 850);

                choice1.setVisibility(View.INVISIBLE);
                choice1.setClickable(false);
                choice1.startAnimation(anim_ext);

                choice1t.startAnimation(anim_ext);
                choice1t.setVisibility(View.INVISIBLE);

                choice2t.startAnimation(anim_ext);

                choice2.setClickable(false);
                break;

        }
    }

    public void ChangesEnd(View v) {
        switch (v.getId()) {
            case (R.id.choice1):
                this.flag = 0;
                situation.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
                        situation.startAnimation(anim_emer);

                        choice1t.setText(db.userDao().getSituationById().get(id - 1).choice1);
                        choice1t.startAnimation(anim_emer);

                        choice2t.setVisibility(View.VISIBLE);
                        choice2t.setText(db.userDao().getSituationById().get(id - 1).choice2);
                        choice2t.startAnimation(anim_emer);

                        choice2.setVisibility(View.VISIBLE);
                        choice2.startAnimation(anim_emer);

                        choice1.setClickable(true);
                    }
                }, 850);

                choice1t.startAnimation(anim_ext);

                choice1.setClickable(false);
                choice2.setClickable(true);

                if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "ChangeTime"))
                    id = 11;
                else
                    id++;
                break;

            case (R.id.choice2):
                this.flag = 0;
                situation.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
                        situation.startAnimation(anim_emer);

                        choice2t.setText(db.userDao().getSituationById().get(id - 1).choice2);
                        choice2t.startAnimation(anim_emer);

                        choice1t.setVisibility(View.VISIBLE);
                        choice1t.setText(db.userDao().getSituationById().get(id - 1).choice1);
                        choice1t.startAnimation(anim_emer);

                        choice1.setVisibility(View.VISIBLE);
                        choice1.startAnimation(anim_emer);

                        choice2.setClickable(true);
                    }
                }, 850);
                situation.startAnimation(anim_ext);

                choice2t.startAnimation(anim_ext);

                choice1.setClickable(true);
                choice2.setClickable(false);

                if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "NotChangeTime"))
                    id += 2;
                else if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "ChangeTime"))
                    id = 19;
                else
                    id++;
                break;
        }
    }
}