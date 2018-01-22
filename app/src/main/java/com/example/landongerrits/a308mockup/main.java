package com.example.landongerrits.a308mockup;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView crowd;
    private ImageView musicNote;

    // Score
    private int score = 0;

    // Position
    private int crowdY;

    private int musicNoteX;
    private int musicNoteY;

    // Size Constraints
    private int frameHeight;
    private int frameWidth; // not used yet

    private int screenWidth;
    private int screenHeight;

    private int crowdSize;

    // Initialize handler
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Status Check
    private boolean action_flag = false;
    private boolean start_flag = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        crowd = (ImageView) findViewById(R.id.crowd);
        musicNote = (ImageView) findViewById(R.id.musicNote);

        // get screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        // set music note off screen
        musicNote.setX(-80);
        musicNote.setY(-80);


    }

    public void hitCheck(){

        // musicNote
        int noteCenterX = musicNoteX + musicNote.getWidth();
        int noteCenterY = musicNoteY + musicNote.getHeight();

        if(0 <= noteCenterX && noteCenterY <= crowdSize && crowdY <= noteCenterY && noteCenterY <= crowdY + crowdSize) {
            musicNoteX = -10;
            score += 100;

        }

    }

    public void changePos() {

        hitCheck();

        // Spawn music note
        musicNoteX -= 12;
        if(musicNoteX < 0) {
            musicNoteX = screenWidth + 20;
            musicNoteY = (int) Math.floor(Math.random() * (frameHeight - musicNote.getHeight()));
        }
        musicNote.setX(musicNoteX);
        musicNote.setY(musicNoteY);

        // Move crowd
        if(action_flag) {
            // touching
            crowdY -= 20;
        } else {
            // releasing
            crowdY += 20;
        }

        if(crowdY < 0 ) crowdY = 0;
        if(crowdY >= frameHeight - crowdSize) crowdY = frameHeight - crowdSize;

        crowd.setY(crowdY);

        scoreLabel.setText("Score : " + score);
    }

    public boolean onTouchEvent(MotionEvent me) {

        if (start_flag == false) {

            start_flag = true;

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            crowdY = (int)crowd.getY();

            // square icon so height = width
            crowdSize = crowd.getHeight();


            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0,20);


        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN){
                action_flag = true;

            } else if(me.getAction() == MotionEvent.ACTION_UP) {
                action_flag = false;
            }
        }




        return true;
    }
}
