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

public class MainGameActivity extends AppCompatActivity {

    ImageButton choice1, choice2;
    TextView situation, choice1t, choice2t, hp;

    Animation anim_emer, anim_ext, anim_fade_out;

    //Save save = new Save();

    Integer i = 0;
    int cur_time = 1;
    Integer cur_id = 1;

    Handler handler = new Handler();

    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

       /* save.cur_id = 0;
        save.HP = 3;
        int j = db.userDao().getCurSaveId().get(0).cur_id;
        db.userDao().update(save);*/

        anim_emer = AnimationUtils.loadAnimation(this, R.anim.txt_anim_emerg);
        anim_ext = AnimationUtils.loadAnimation(this, R.anim.txt_anim_extin);
        anim_fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        situation = findViewById(R.id.situation);
        choice1t = findViewById(R.id.choice1t);
        choice2t = findViewById(R.id.choice2t);
        hp = findViewById(R.id.hp);

        db = Room.databaseBuilder(this, MyDatabase.class, "my1db")
                .createFromAsset("databases/base228.db")
                .allowMainThreadQueries()
                .build();

        situation.setText(db.userDao().getSituationByIdPresent().get(i).situation);
        choice1t.setText(db.userDao().getSituationByIdPresent().get(i).choice1);
        choice2t.setText(db.userDao().getSituationByIdPresent().get(i).choice2);

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice1t.getText() == "Продолжить") {
                    cur_id++;
                    i++;
                }
                Changes(choice1);
                if (cur_time == 1 && cur_id == 8) {
                    cur_time = 3;
                    i = 0;
                } else if ((cur_time == 1 || cur_time == 2) && cur_id == 11) {
                    cur_time = 3;
                    i = 0;
                }
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2t.getText() == "Продолжить") {
                    if (cur_time == 1 && cur_id == 6) {
                        cur_id += 2;
                        i += 2;
                        cur_time = 2;
                    } else {
                        cur_id++;
                        i++;
                    }
                }

                Changes(choice2);
                if (cur_time == 1 && cur_id == 8) {
                    cur_time = 4;
                    i = 0;
                } else if ((cur_time == 1 || cur_time == 2) && cur_id == 11) {
                    cur_time = 4;
                    i = 0;
                }
            }
        });
    }

    public void Changes(View v) {
        if (choice1t.getText() != "Продолжить" && choice2t.getText() != "Продолжить") {
            switch (v.getId()) {
                case (R.id.choice1):
                    situation.startAnimation(anim_ext);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (cur_time == 1 || cur_time == 2)
                                situation.setText(db.userDao().getSituationByIdPresent().get(i).implications1);
                            else if (cur_time == 3)
                                situation.setText(db.userDao().getSituationByIdPast().get(i).implications1);
                            else if (cur_time == 4)
                                situation.setText(db.userDao().getSituationByIdFuture().get(i).implications1);
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
                    situation.startAnimation(anim_ext);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (cur_time == 1 || cur_time == 2)
                                situation.setText(db.userDao().getSituationByIdPresent().get(i).implications2);
                            else if (cur_time == 3)
                                situation.setText(db.userDao().getSituationByIdPast().get(i).implications2);
                            else if (cur_time == 4)
                                situation.setText(db.userDao().getSituationByIdFuture().get(i).implications2);
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
        } else {
            switch (v.getId()) {
                case (R.id.choice1):
                    situation.startAnimation(anim_ext);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            TimeTransition(situation);
                            situation.startAnimation(anim_emer);

                            TimeTransition(choice1t);
                            choice1t.startAnimation(anim_emer);

                            choice2t.setVisibility(View.VISIBLE);
                            TimeTransition(choice2t);
                            choice2t.startAnimation(anim_emer);

                            choice2.setVisibility(View.VISIBLE);
                            choice2.startAnimation(anim_emer);

                            choice1.setClickable(true);
                        }
                    }, 850);

                    choice1t.startAnimation(anim_ext);

                    choice1.setClickable(false);
                    choice2.setClickable(true);
                    break;

                case (R.id.choice2):
                    situation.startAnimation(anim_ext);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            TimeTransition(situation);
                            situation.startAnimation(anim_emer);

                            TimeTransition(choice2t);
                            choice2t.startAnimation(anim_emer);

                            choice1t.setVisibility(View.VISIBLE);
                            TimeTransition(choice1t);
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
                    break;
            }
        }
    }

    void TimeTransition(TextView v) {
        if (cur_time == 1 || cur_time == 2) {
            switch (v.getId()) {
                case (R.id.situation):
                    v.setText(db.userDao().getSituationByIdPresent().get(i).situation);
                    break;
                case (R.id.choice1t):
                    v.setText(db.userDao().getSituationByIdPresent().get(i).choice1);
                    break;
                case (R.id.choice2t):
                    v.setText(db.userDao().getSituationByIdPresent().get(i).choice2);
                    break;
            }
        } else if (cur_time == 3) {
            switch (v.getId()) {
                case (R.id.situation):
                    v.setText(db.userDao().getSituationByIdPast().get(i).situation);
                    break;
                case (R.id.choice1t):
                    v.setText(db.userDao().getSituationByIdPast().get(i).choice1);
                    break;
                case (R.id.choice2t):
                    v.setText(db.userDao().getSituationByIdPast().get(i).choice2);
                    break;
            }
        } else if (cur_time == 4) {
            switch (v.getId()) {
                case (R.id.situation):
                    v.setText(db.userDao().getSituationByIdFuture().get(i).situation);
                    break;
                case (R.id.choice1t):
                    v.setText(db.userDao().getSituationByIdFuture().get(i).choice1);
                    break;
                case (R.id.choice2t):
                    v.setText(db.userDao().getSituationByIdFuture().get(i).choice2);
                    break;
            }
        }
    }
}