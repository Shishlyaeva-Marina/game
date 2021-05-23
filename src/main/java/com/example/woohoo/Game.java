package com.example.woohoo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;

public class Game extends AppCompatActivity {
    private Button butFeed, butSleep, butDuff;
    private ProgressBar pbFeed, pbSleep, pbDuff;
    private TextView sayHomer;
    private Timer time;
    private ImageView homer;
    private  int t = 1000, warning = 40;
    private Handler handler;
    SharedPreferences feed_status, sleep_status, drink_status;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sayHomer = (TextView) findViewById(R.id.sayHomer);
        homer = (ImageView) findViewById(R.id.homer);
        homer.setImageResource(R.drawable.bad);
        butFeed = (Button) findViewById(R.id.butFeed);
        butSleep = (Button) findViewById(R.id.butSleep);
        butDuff = (Button) findViewById(R.id.butDuff);

        feed_status = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        sleep_status = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        drink_status = getSharedPreferences("SaveData", Context.MODE_PRIVATE);

        pbFeed = (ProgressBar) findViewById(R.id.pbFeed);
        pbFeed.setProgress(feed_status.getInt("Value", 0));

        pbSleep = (ProgressBar) findViewById(R.id.pbSleep);
        pbSleep.setProgress(sleep_status.getInt("Value", 0));

        pbDuff = (ProgressBar)findViewById(R.id.pbDuff);
        pbDuff.setProgress(drink_status.getInt("Value", 0));

        Thread thread = new Thread(img);
        thread.start();

        butFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbFeed.setProgress(pbFeed.getProgress()+30);
            }
        });

        butSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbSleep.setProgress(pbSleep.getProgress()+30);
            }
        });

        butDuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbDuff.setProgress(pbDuff.getProgress()+30);
            }
        });
    }
    Runnable img = new Runnable() {
        boolean imgCheck = true;
        @Override
        public void run() {
            if(imgCheck){
                if(pbFeed.getProgress()<warning && pbSleep.getProgress()>warning && pbDuff.getProgress()>warning) { // хочет есть
                    homer.setImageResource(R.drawable.want_eat);
                    sayHomer.setText("Eat!");
                }
                else if(pbFeed.getProgress()>warning && pbSleep.getProgress()<warning && pbDuff.getProgress()>warning){ // хочет спать
                    homer.setImageResource(R.drawable.grr);
                    sayHomer.setText("Sleeeep!");
                }
                else if(pbFeed.getProgress()>warning && pbSleep.getProgress()>warning && pbDuff.getProgress()<warning){ // хочет Duff
                    homer.setImageResource(R.drawable.want_duff);
                    sayHomer.setText("I want drink!");
                }
                else if(pbFeed.getProgress()<warning && pbSleep.getProgress()<warning && pbDuff.getProgress()<warning &&
                        pbFeed.getProgress()>0 && pbSleep.getProgress()>0 && pbDuff.getProgress()>0 ||
                        pbFeed.getProgress()<warning && pbSleep.getProgress()<warning && pbDuff.getProgress()>warning ||
                        pbFeed.getProgress()<warning && pbSleep.getProgress()>warning && pbDuff.getProgress()<warning ||
                        pbFeed.getProgress()>warning && pbSleep.getProgress()<warning && pbDuff.getProgress()<warning){
                    homer.setImageResource(R.drawable.bad);
                    sayHomer.setText("Wake up!");
                }
                else if(pbFeed.getProgress()==0 && pbSleep.getProgress()==0 && pbDuff.getProgress()==0){
                    Intent intent = new Intent(Game.this, EndGame.class);
                    startActivity(intent);
                    imgCheck = false;
                }
                else{
                    homer.setImageResource(R.drawable.all_good);
                    sayHomer.setText("Woo-Hoo!");
                }
                if(pbFeed.getProgress()>0) pbFeed.setProgress(pbFeed.getProgress()-1);
                if(pbSleep.getProgress()>0)pbSleep.setProgress(pbSleep.getProgress()-1);
                if(pbDuff.getProgress()>0)pbDuff.setProgress(pbDuff.getProgress()-1);
                handler.postDelayed(this, t);
            }

        }
    };
}