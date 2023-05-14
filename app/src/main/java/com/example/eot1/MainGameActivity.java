package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eot1.entities.Save;

import java.util.Objects;

public class MainGameActivity extends AppCompatActivity {

    Button choice1, choice2;
    TextView situation, hp;
    ImageView textBack;
    Animation anim_emer, anim_ext;
    Save save = new Save();
    Integer id, flag = 0, health, flag2 = 0;
    Handler handler = new Handler();
    MyDatabase db;
    Intent intent;
    ConstraintLayout layout;
    MediaPlayer btnClick, backSong;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        anim_emer = AnimationUtils.loadAnimation(this, R.anim.txt_anim_emerg);
        anim_ext = AnimationUtils.loadAnimation(this, R.anim.txt_anim_extin);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        situation = findViewById(R.id.situation);
        hp = findViewById(R.id.hp);
        layout = findViewById(R.id.mainGameL);
        textBack = findViewById(R.id.textback);

        intent = new Intent(this, EndOrDeath.class);
        btnClick = MediaPlayer.create(this, R.raw.soundbtn);
        backSong = MediaPlayer.create(this, R.raw.backsong);
        backSong.start();
        backSong.setLooping(true);

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        setStart();

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.start();
                if (flag == 0) {
                    ChangeHP(choice1);
                    ChangesStart(choice1);
                    EndOrDeath(choice1);
                } else {
                    ChangesEnd(choice1);
                    save.cur_id = id;
                    db.userDao().update(save);
                }
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.start();
                if (flag == 0) {
                    ChangeHP(choice2);
                    ChangesStart(choice2);
                    EndOrDeath(choice2);
                } else {
                    ChangesEnd(choice2);
                    save.cur_id = id;
                    db.userDao().update(save);
                }
            }
        });
    }

    public void ChangesStart(View v) {
        switch (v.getId()) {
            case (R.id.choice1):
                this.flag = 1;
                situation.startAnimation(anim_ext);
                textBack.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).implications1);
                        situation.startAnimation(anim_emer);
                        textBack.startAnimation(anim_emer);

                        choice1.setText("Продолжить");
                        choice1.startAnimation(anim_emer);
                        choice1.setClickable(true);

                        if (flag2 == 0)
                            choice1.setBackgroundResource(R.drawable.btnpres2);
                        else if (flag2 == 1)
                            choice1.setBackgroundResource(R.drawable.btnpast2);
                        else if (flag2 == 2)
                            choice1.setBackgroundResource(R.drawable.btnfut2);
                    }
                }, 840);
                choice2.setVisibility(View.INVISIBLE);
                choice2.setClickable(false);
                choice2.startAnimation(anim_ext);

                choice1.startAnimation(anim_ext);
                choice1.setClickable(false);
                break;

            case (R.id.choice2):
                this.flag = 1;
                situation.startAnimation(anim_ext);
                textBack.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).implications2);
                        situation.startAnimation(anim_emer);
                        textBack.startAnimation(anim_emer);

                        choice2.setText("Продолжить");
                        choice2.startAnimation(anim_emer);
                        choice2.setClickable(true);

                        if (flag2 == 0)
                            choice2.setBackgroundResource(R.drawable.btnpres2);
                        else if (flag2 == 1)
                            choice2.setBackgroundResource(R.drawable.btnpast2);
                        else if (flag2 == 2)
                            choice2.setBackgroundResource(R.drawable.btnfut2);
                    }
                }, 840);
                choice1.setVisibility(View.INVISIBLE);
                choice1.setClickable(false);
                choice1.startAnimation(anim_ext);

                choice2.startAnimation(anim_ext);
                choice2.setClickable(false);
                break;
        }
    }

    public void ChangesEnd(View v) {
        if (id >= 10)
            flag2 = 1;
        else if (id >= 18)
            flag2 = 2;
        switch (v.getId()) {
            case (R.id.choice1):
                this.flag = 0;
                situation.startAnimation(anim_ext);
                textBack.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
                        situation.startAnimation(anim_emer);
                        textBack.startAnimation(anim_emer);

                        choice1.setText(db.userDao().getSituationById().get(id - 1).choice1);
                        choice1.startAnimation(anim_emer);

                        choice2.setVisibility(View.VISIBLE);
                        choice2.setText(db.userDao().getSituationById().get(id - 1).choice2);
                        choice2.startAnimation(anim_emer);

                        choice1.setClickable(true);
                        if (id == 3)
                            layout.setBackgroundResource(R.drawable.backstreet);
                        if (flag2 == 0) {
                            choice1.setBackgroundResource(R.drawable.btnpres1);
                            choice2.setBackgroundResource(R.drawable.btnpres1);
                        } else if (flag2 == 1) {
                            choice1.setBackgroundResource(R.drawable.btnpast1);
                            choice2.setBackgroundResource(R.drawable.btnpast1);
                            layout.setBackgroundResource(R.drawable.backpast);
                        } else if (flag2 == 2) {
                            choice1.setBackgroundResource(R.drawable.btnfut1);
                            choice2.setBackgroundResource(R.drawable.btnfut1);
                        }
                    }
                }, 840);

                choice1.startAnimation(anim_ext);
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
                textBack.startAnimation(anim_ext);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
                        situation.startAnimation(anim_emer);
                        textBack.startAnimation(anim_emer);

                        choice2.setText(db.userDao().getSituationById().get(id - 1).choice2);
                        choice2.startAnimation(anim_emer);

                        choice1.setVisibility(View.VISIBLE);
                        choice1.setText(db.userDao().getSituationById().get(id - 1).choice1);
                        choice1.startAnimation(anim_emer);

                        choice2.setClickable(true);
                        if (id == 3)
                            layout.setBackgroundResource(R.drawable.backstreet);
                        if (flag2 == 0) {
                            choice1.setBackgroundResource(R.drawable.btnpres1);
                            choice2.setBackgroundResource(R.drawable.btnpres1);
                        } else if (flag2 == 1) {
                            choice1.setBackgroundResource(R.drawable.btnpast1);
                            choice2.setBackgroundResource(R.drawable.btnpast1);
                        } else if (flag2 == 2) {
                            choice1.setBackgroundResource(R.drawable.btnfut1);
                            choice2.setBackgroundResource(R.drawable.btnfut1);
                            layout.setBackgroundResource(R.drawable.backfutsvg);
                        }
                    }
                }, 840);

                situation.startAnimation(anim_ext);
                textBack.startAnimation(anim_ext);

                choice2.startAnimation(anim_ext);

                choice1.setClickable(true);
                choice2.setClickable(false);

                if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "NotChangeTime"))
                    id += 2;
                else if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "ChangeTime")) {
                    id = 19;
                } else
                    id++;
                break;
        }
    }

    public void ChangeHP(View v) {
        switch (v.getId()) {
            case (R.id.choice1):
                if (id == 3 || id == 9 || id == 14 || id == 20) {
                    health += 1;
                    save.HP = health;
                    db.userDao().update(save);
                    hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");
                } else if (id == 12 || id == 22) {
                    health -= 1;
                    save.HP = health;
                    db.userDao().update(save);
                    hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");
                }
                break;
            case (R.id.choice2):
                if (id == 3 || id == 8 || id == 15 || id == 20 || id == 23) {
                    health -= 1;
                    save.HP = health;
                    db.userDao().update(save);
                    hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");
                }
                break;
        }
    }

    public void EndOrDeath(View v) {
        if (db.userDao().getCurSaveId().get(0).HP == 0) {
            if (db.userDao().getCurSaveId().get(0).cur_id >= 11 && db.userDao().getCurSaveId().get(0).cur_id < 19) {
                save.cur_id = 11;
                save.HP = 2;
                db.userDao().update(save);
            } else if (db.userDao().getCurSaveId().get(0).cur_id >= 19 && db.userDao().getCurSaveId().get(0).cur_id < 27) {
                save.cur_id = 19;
                save.HP = 2;
                db.userDao().update(save);
            }
            intent.putExtra("end", "Вы погибли");
            startActivity(intent);
        } else {
            save.cur_id = 1;
            save.HP = 3;
            db.userDao().update(save);
            if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "Ending")) {
                if (v.getId() == R.id.choice1)
                    intent.putExtra("end", db.userDao().getSituationById().get(id - 1).implications1);
                else if (v.getId() == R.id.choice2)
                    intent.putExtra("end", db.userDao().getSituationById().get(id - 1).implications2);

                startActivity(intent);
            }
            if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "MaybeEnding") && (v.getId() == R.id.choice2)) {
                intent.putExtra("end", db.userDao().getSituationById().get(id - 1).implications2);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            backSong.pause();
            finishAffinity();
        }
        back_pressed = System.currentTimeMillis();
    }

    public void setStart() {
        id = db.userDao().getCurSaveId().get(0).cur_id;
        save.cur_id = db.userDao().getCurSaveId().get(0).cur_id;
        save.id = 1;
        health = db.userDao().getCurSaveId().get(0).HP;
        save.HP = health;
        hp.setText(save.HP.toString() + "x");

        if (db.userDao().getCurSaveId().get(0).cur_id >=3 && db.userDao().getCurSaveId().get(0).cur_id < 11)
            layout.setBackgroundResource(R.drawable.backstreet);
        else if (db.userDao().getCurSaveId().get(0).cur_id >= 11 && db.userDao().getCurSaveId().get(0).cur_id < 19) {
            layout.setBackgroundResource(R.drawable.backpast);
            choice1.setBackgroundResource(R.drawable.btnpast1);
            choice2.setBackgroundResource(R.drawable.btnpast1);
        } else if (db.userDao().getCurSaveId().get(0).cur_id >= 19 && db.userDao().getCurSaveId().get(0).cur_id < 27) {
            layout.setBackgroundResource(R.drawable.backfutsvg);
            choice1.setBackgroundResource(R.drawable.btnfut1);
            choice2.setBackgroundResource(R.drawable.btnfut1);
        }
        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
        choice1.setText(db.userDao().getSituationById().get(id - 1).choice1);
        choice2.setText(db.userDao().getSituationById().get(id - 1).choice2);
    }
}