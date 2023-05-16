package com.example.eot1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    ImageView textBack, hpChangesImage;
    Animation anim_emer, anim_ext;
    Save save = new Save();
    Integer id, flag = 0, health;
    Handler handler = new Handler();
    MyDatabase db;
    Intent intent, intent2;
    ConstraintLayout layout;
    MediaPlayer backSong;

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        anim_emer = AnimationUtils.loadAnimation(this, R.anim.txt_anim_emerg);
        anim_ext = AnimationUtils.loadAnimation(this, R.anim.txt_anim_extin);

        backSong = MediaPlayer.create(this, R.raw.backsong);
        backSong.start();
        backSong.setLooping(true);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        situation = findViewById(R.id.situation);
        hp = findViewById(R.id.hp);
        layout = findViewById(R.id.mainGameLa);
        textBack = findViewById(R.id.textback);
        hpChangesImage = findViewById(R.id.hpchanges);


        intent = new Intent(this, EndOrDeath.class);
        intent2 = new Intent(this, MainActivity.class);
        //btnClick = MediaPlayer.create(this, R.raw.btnsound);

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        setStart();

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    ChangesStart(choice1);
                    ChangeHP(choice1);
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
                if (flag == 0) {
                    ChangesStart(choice2);
                    ChangeHP(choice2);
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

                        if (id < 11)
                            choice1.setBackgroundResource(R.drawable.btnpres2);
                        else if (id >= 11 && id < 19)
                            choice1.setBackgroundResource(R.drawable.btnpast2);
                        else if (id >= 19)
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
                        //ChangeHP(choice2);
                        situation.setText(db.userDao().getSituationById().get(id - 1).implications2);
                        situation.startAnimation(anim_emer);
                        textBack.startAnimation(anim_emer);

                        choice2.setText("Продолжить");
                        choice2.startAnimation(anim_emer);
                        choice2.setClickable(true);

                        if (id < 11)
                            choice2.setBackgroundResource(R.drawable.btnpres2);
                        else if (id >= 11 && id < 19)
                            choice2.setBackgroundResource(R.drawable.btnpast2);
                        else if (id >= 19)
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
                        if (id == 3) {
                            layout.setBackgroundResource(R.drawable.backstreet);
                        }
                        if (id < 11) {
                            choice1.setBackgroundResource(R.drawable.btnpres1);
                            choice2.setBackgroundResource(R.drawable.btnpres1);
                        } else if (id >= 11 && id < 19) {
                            choice1.setBackgroundResource(R.drawable.btnpast1);
                            choice2.setBackgroundResource(R.drawable.btnpast1);
                            layout.setBackgroundResource(R.drawable.backpast);
                        } else if (id >= 19) {
                            choice1.setBackgroundResource(R.drawable.btnfut1);
                            choice2.setBackgroundResource(R.drawable.btnfut1);
                        }
                    }
                }, 840);

                choice1.startAnimation(anim_ext);
                choice1.setClickable(false);
                choice2.setClickable(true);

                if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "ChangeTime")) {
                    id = 11;
                } else
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
                        if (id < 11) {
                            choice1.setBackgroundResource(R.drawable.btnpres1);
                            choice2.setBackgroundResource(R.drawable.btnpres1);
                        } else if (id >= 11 && id < 19) {
                            choice1.setBackgroundResource(R.drawable.btnpast1);
                            choice2.setBackgroundResource(R.drawable.btnpast1);
                        } else if (id >= 19) {
                            choice1.setBackgroundResource(R.drawable.btnfut1);
                            choice2.setBackgroundResource(R.drawable.btnfut1);
                            layout.setBackgroundResource(R.drawable.backfutsvg);
                        }
                    }
                }, 840);

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
                    hpChangesImage.setBackgroundResource(R.drawable.uphp);
                    hpChangesImage.startAnimation(anim_ext);
                } else if (id == 12 || id == 22) {
                    health -= 1;
                    save.HP = health;
                    db.userDao().update(save);
                    hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");
                    hpChangesImage.setBackgroundResource(R.drawable.damagehp);
                    hpChangesImage.startAnimation(anim_ext);
                }
                break;
            case (R.id.choice2):
                if (id == 3 || id == 8 || id == 15 || id == 20 || id == 23) {
                    health -= 1;
                    save.HP = health;
                    db.userDao().update(save);
                    hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");
                    hpChangesImage.setBackgroundResource(R.drawable.damagehp);
                    hpChangesImage.startAnimation(anim_ext);
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
            backSong.pause();
            intent.putExtra("end", "Вы погибли");
            startActivity(intent);
            overridePendingTransition(R.anim.da, R.anim.d);
        } else {
            save.cur_id = 1;
            save.HP = 3;
            db.userDao().update(save);
            if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "Ending")) {
                if (v.getId() == R.id.choice1)
                    intent.putExtra("end", db.userDao().getSituationById().get(id - 1).implications1);
                else if (v.getId() == R.id.choice2)
                    intent.putExtra("end", db.userDao().getSituationById().get(id - 1).implications2);

                backSong.pause();
                startActivity(intent);
                overridePendingTransition(R.anim.da, R.anim.d);
            }
            if (Objects.equals(db.userDao().getSituationById().get(id - 1).time, "MaybeEnding") && (v.getId() == R.id.choice2)) {
                intent.putExtra("end", db.userDao().getSituationById().get(id - 1).implications2);
                backSong.pause();
                startActivity(intent);
                overridePendingTransition(R.anim.da, R.anim.d);
            }
        }
    }

    public void showAlertWithTwoButton(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Выбор есть всегда!")
                .setMessage("Что вы хотите сдетать?")
                .setPositiveButton("Выйти в главное меню", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                startActivity(intent2);
                overridePendingTransition(R.anim.da, R.anim.d);
                backSong.pause();
            }
        })
                .setNegativeButton("Выйти из игры", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                backSong.pause();
                finishAffinity();
            }
        })
                .setCancelable(true);

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 3000 > System.currentTimeMillis()) {
            showAlertWithTwoButton();
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

        hpChangesImage.setVisibility(View.INVISIBLE);

        if (db.userDao().getCurSaveId().get(0).cur_id >= 3 && db.userDao().getCurSaveId().get(0).cur_id < 11)
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