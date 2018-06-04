package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DJControllerBar extends GameObject {
    private int screenWidth;
    private int screenHeight;
    private Paint paint;
    private Bitmap turntable;

    public DJControllerBar(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();

        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.turntable);
        turntable = Bitmap.createScaledBitmap(bmp, screenWidth, screenHeight - (3 * screenHeight / 4), false);
    }

    public boolean shouldDelete() {
        return false;
    }

    public boolean shouldDequeue() {return false;}

    public void update(float songPos) {
        // DJ controller bar doesn't need to update
    }

    public void draw(Canvas canvas) {
        if (canvas == null) {return;}

        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 9 * screenHeight / 12, screenWidth - 1, screenHeight - 1, paint);

        canvas.drawBitmap(turntable, 0, 3 * screenHeight / 4, null);
    }
}
