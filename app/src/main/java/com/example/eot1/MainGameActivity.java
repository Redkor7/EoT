package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eot1.entities.Save;

import java.util.Objects;

public class MainGameActivity extends AppCompatActivity {

    ImageButton choice1, choice2;
    TextView situation, choice1t, choice2t, hp;
    String endOrDeatht;
    Animation anim_emer, anim_ext;
    Save save = new Save();
    Integer id, flag = 0;
    Handler handler = new Handler();
    MyDatabase db;
    Intent intent;
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
        choice1t = findViewById(R.id.choice1t);
        choice2t = findViewById(R.id.choice2t);
        hp = findViewById(R.id.hp);

        intent = new Intent(this, EndOrDeath.class);

        db = Room.databaseBuilder(this, MyDatabase.class, "my4db")
                .createFromAsset("databases/base8.db")
                .allowMainThreadQueries()
                .build();

        id = db.userDao().getCurSaveId().get(0).cur_id;
        save.cur_id = db.userDao().getCurSaveId().get(0).cur_id;
        save.id = 1;
        save.HP = db.userDao().getCurSaveId().get(0).HP;
        hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");

        situation.setText(db.userDao().getSituationById().get(id - 1).situation);
        choice1t.setText(db.userDao().getSituationById().get(id - 1).choice1);
        choice2t.setText(db.userDao().getSituationById().get(id - 1).choice2);

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");

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

                hp.setText(db.userDao().getCurSaveId().get(0).HP.toString() + "x");

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

    public void ChangeHP(View v) {
        switch (v.getId()) {
            case (R.id.choice1):
                if (id == 3 || id == 9 || id == 14 || id == 20) {
                    save.HP += 1;
                    db.userDao().update(save);
                } else if (id == 12 || id == 22) {
                    save.HP -= 1;
                    db.userDao().update(save);
                }
                break;
            case (R.id.choice2):
                if (id == 3 || id == 8 || id == 15 || id == 20 || id == 23) {
                    save.HP -= 1;
                    db.userDao().update(save);
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
            } else {
                save.cur_id = 1;
                save.HP = 3;
                db.userDao().update(save);
            }
            intent.putExtra("end", "Вы погибли");
            startActivity(intent);
        }
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

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}