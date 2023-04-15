package com.example.eot1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStr = findViewById(R.id.button5);
        Intent intent = new Intent(this, MainGameActivity.class);

        btnStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText();
                startActivity(intent);
            }
        });
    }

    private  void showText(){
        Toast.makeText(this, "Good bro", Toast.LENGTH_LONG).show();
    }
}