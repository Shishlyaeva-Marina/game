package com.example.woohoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EndGame extends AppCompatActivity {
    ImageView img;
    Button but;
    SharedPreferences feed_status, sleep_status, drink_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        img = (ImageView) findViewById(R.id.img);
        img.setImageResource(R.drawable.endgame);
        but = (Button) findViewById(R.id.butRetry);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feed_status = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
                sleep_status = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
                drink_status = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
                SharedPreferences.Editor fed = feed_status.edit();
                SharedPreferences.Editor sed = sleep_status.edit();
                SharedPreferences.Editor ded = drink_status.edit();
                fed.putInt("Value", 80);
                fed.apply();
                sed.putInt("Value", 80);
                sed.apply();
                ded.putInt("Value", 80);
                ded.apply();
                Intent intent = new Intent(EndGame.this, Game.class);
                startActivity(intent);
            }
        });
    }
}