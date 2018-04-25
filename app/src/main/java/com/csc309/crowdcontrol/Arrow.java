package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Arrow implements GameObject {
    private int x, y, velocity, screenWidth, screenHeight;
    int mode;
    private Bitmap image;
    private boolean shouldDelete;

    public Arrow(Context current, int mode, int velocity, int screenWidth, int screenHeight) {
        y = 0;
        this.velocity = velocity;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mode = mode;

        Bitmap bmp = BitmapFactory.decodeResource(current.getResources(), R.drawable.uparrowbluenormal);
        bmp = Bitmap.createScaledBitmap(bmp, screenWidth/6, screenWidth/6, false);


        if (mode == 0) {
            // Left Arrow
            image = rotateBitmap(bmp, 270);
            x = screenWidth/15; // left gap
        }
        else if (mode == 1) {
            // Up Arrow
            image = bmp;
            x = 2 * screenWidth/15 + image.getWidth(); // two gaps + 1 width
        }
        else if (mode == 2) {
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

    public void update() {
        y += velocity;

        if (y > screenHeight) {shouldDelete = true;}
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawBitmap(image, x, y, null);
        }
    }
}
