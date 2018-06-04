package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class DJControllerBar extends GameObject {
    private int screenWidth, screenHeight;
    private Paint paint;
    Bitmap left, right;

    public DJControllerBar(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();

        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.vinyl);
        left = Bitmap.createScaledBitmap(bmp, screenWidth / 4, screenWidth / 4, false);
        right = Bitmap.createScaledBitmap(bmp, screenWidth / 4, screenWidth / 4, false);
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
        return false;
    }

    public boolean shouldDequeue() {return false;}

    public void update(float songPos) {
    }

    public void draw(Canvas canvas) {
        if (canvas == null) {return;}

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 9 * screenHeight / 12, screenWidth - 1, screenHeight - 1, paint);

        canvas.drawBitmap(left, screenWidth / 8, 9 * screenHeight / 12 + screenHeight / 96, null);
        canvas.drawBitmap(right, 5 * screenWidth / 8, 9 * screenHeight / 12 + screenHeight / 96, null);
    }
}
