package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Arrow extends GameObject {

    public enum DIRECTION
    {
        LEFT, RIGHT, UP, DOWN
    }

    private int x, y, velocity, screenWidth, screenHeight;
    DIRECTION mode;
    private Bitmap image;
    private boolean shouldDelete;

    //USED TO TRACK SCREEN POSITION
    private float songPosStart;
    private float songPosTarget;
    private boolean bradArrow = false;
    public float currentSongPos;

    int ARROW_STARTING_Y; //Where arrows are spawned
    private float ARROW_TARGET_Y; //Where arrows should end up

    public Arrow(Context current, DIRECTION mode, int velocity, int screenWidth, int screenHeight) {
        y = 0;
        this.velocity = velocity;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mode = mode;

        Bitmap bmp = BitmapFactory.decodeResource(current.getResources(), R.drawable.uparrowbluenormal);
        bmp = Bitmap.createScaledBitmap(bmp, screenWidth/6, screenWidth/6, false);


        if (mode == DIRECTION.LEFT) {
            // Left Arrow
            image = rotateBitmap(bmp, 270);
            x = screenWidth/15; // left gap
        }
        else if (mode == DIRECTION.UP) {
            // Up Arrow
            image = bmp;
            x = 2 * screenWidth/15 + image.getWidth(); // two gaps + 1 width
        }
        else if (mode == DIRECTION.DOWN) {
            // Down Arrow
            image = rotateBitmap(bmp, 180);
            x = 3 * screenWidth/15 + 2 * image.getWidth(); // three gaps + 2 widths
        }
        else {
            // Right Arrow
            image = rotateBitmap(bmp, 90);
            x = 4 * screenWidth/15 + 3 * image.getWidth(); // four gaps + 3 widths
        }
    }



    //BRAD IS RESPONSIBLE FOR THIS AND WHATEVER CONSEQUENCES ARISE FROM IT
    public Arrow(Context current, DIRECTION mode, float songPosStart, float songPosTarget,
                 int screenWidth, int screenHeight)
    {
        ARROW_STARTING_Y = -1000;
        y = ARROW_STARTING_Y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mode = mode;

        //Sencer... take a look at this super wack math. Maybe there's a better way to do this
        this.ARROW_TARGET_Y = 7 * screenHeight / 12 + screenHeight / 24;

        this.songPosStart = songPosStart;
        this.songPosTarget = songPosTarget;

        bradArrow = true;

        Bitmap bmp = BitmapFactory.decodeResource(current.getResources(), R.drawable.uparrowbluenormal);
        bmp = Bitmap.createScaledBitmap(bmp, screenWidth/6, screenWidth/6, false);


        if (mode == DIRECTION.LEFT) {
            // Left Arrow
            image = rotateBitmap(bmp, 270);
            x = screenWidth/15; // left gap
        }
        else if (mode == DIRECTION.UP) {
            // Up Arrow
            image = bmp;
            x = 3 * screenWidth/15 + 2 * image.getWidth(); // three gaps + 2 widths
            //x = 2 * screenWidth/15 + image.getWidth(); // two gaps + 1 width
        }
        else if (mode == DIRECTION.DOWN) {
            // Down Arrow
            image = rotateBitmap(bmp, 180);
            x = 4 * screenWidth/15 + 3 * image.getWidth(); // four gaps + 3 widths
            //x = 3 * screenWidth/15 + 2 * image.getWidth(); // three gaps + 2 widths
        }
        else {
            // Right Arrow
            image = rotateBitmap(bmp, 90);
            x = 2 * screenWidth/15 + image.getWidth(); // two gaps + 1 width
            //x = 4 * screenWidth/15 + 3 * image.getWidth(); // four gaps + 3 widths
        }
    }

    private Bitmap rotateBitmap(Bitmap src, float degree) {
        // Create new matrix
        Matrix matrix = new Matrix();
        // Setup rotation degree
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    public boolean shouldDelete() {
        return shouldDelete;
    }

    public void update(float songPosition) {

        this.currentSongPos = songPosition;


        float temp = (songPosTarget - currentSongPos)/(songPosTarget - songPosStart);
        y = ((int)((1-temp) * (ARROW_TARGET_Y - (ARROW_STARTING_Y)))) + (ARROW_STARTING_Y);

        if (y > screenHeight || currentSongPos > songPosTarget) {shouldDelete = true;}
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawBitmap(image, x, y, null);
        }
    }
}
