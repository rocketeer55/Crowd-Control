package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;



import static com.csc309.crowdcontrol.PlayLevelThread.canvas;

public class UpdateScore extends GameObject{

    private int screenWidth, screenHeight;
    private Paint paint;

    public int score = 0;

    private float delta = 0;

    // multipliers
    int multipler = 1;


    // score hits and points
    int okay = 100;
    int good = 250;
    int excellent = 500;

    public UpdateScore(Context context, int screenWidth, int screenHeight){

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
    }

    public void draw(Canvas canvas){
        if (canvas == null) {return;}

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, screenWidth - 1, 100, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(48f);
        canvas.drawText("Score: " + score, 20,80,paint);


        }

    public void update(float delta){

        if (0.333 >= delta && delta >= 0)
            score += excellent * multipler;
        else if (0.666 >= delta && delta > 0.333)
            score += good * multipler;
        else
            score += okay * multipler;
    }

    public boolean shouldDelete(){
        return false;

    }

    public boolean shouldDequeue(){
        return false;
    }




}
