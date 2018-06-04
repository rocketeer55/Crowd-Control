package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class DJControllerBar extends GameObject {
    private int screenWidth;
    private int screenHeight;
    private Paint paint;
    private Bitmap left;
    private Bitmap right;

    public DJControllerBar(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();

        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.vinyl);
        left = Bitmap.createScaledBitmap(bmp, screenWidth / 4, screenWidth / 4, false);
        right = Bitmap.createScaledBitmap(bmp, screenWidth / 4, screenWidth / 4, false);
    }

    public boolean shouldDelete() {
        return false;
    }

    public void update(float songPos) {
        // DJ controller bar doesn't need to update
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
