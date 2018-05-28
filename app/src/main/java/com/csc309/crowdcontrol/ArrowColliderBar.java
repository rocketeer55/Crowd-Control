package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ArrowColliderBar extends GameObject {
    private int screenWidth, screenHeight;
    private Paint paint;
    private Context context;
    Bitmap left, up, down, right;

    public ArrowColliderBar(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();

        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.uparrowendnotebracket);
        bmp = Bitmap.createScaledBitmap(bmp, screenWidth / 6, screenWidth / 6, false);

        left = rotateBitmap(bmp, 270);
        up = bmp;
        down = rotateBitmap(bmp, 180);
        right = rotateBitmap(bmp, 90);
    }

    private Bitmap rotateBitmap(Bitmap src, float degree) {
        // Create new matrix
        Matrix matrix = new Matrix();
        // Setup rotation degree
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    public boolean shouldDelete() {return false;}

    public void update(float songPos) {

    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            paint.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 7 * screenHeight / 12, screenWidth - 1, 9 * screenHeight / 12, paint);

            canvas.drawBitmap(left, screenWidth/15, 7 * screenHeight / 12 + screenHeight / 24, null);
            canvas.drawBitmap(up, 2 * screenWidth/15 + left.getWidth(), 7 * screenHeight / 12 + screenHeight / 24, null);
            canvas.drawBitmap(down, 3 * screenWidth/15 + left.getWidth() + up.getWidth(), 7 * screenHeight / 12 + screenHeight / 24, null);
            canvas.drawBitmap(right, 4 * screenWidth/15 + left.getWidth() + up.getWidth() + down.getWidth(), 7 * screenHeight / 12 + screenHeight / 24, null);
        }
    }
}
