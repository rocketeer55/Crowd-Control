package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

public class PlayGame extends SurfaceView implements SurfaceHolder.Callback {
    private PlayGameThread thread;
    private int screenWidth;
    private int screenHeight;
    private int frameCount = 0;
    private ArrayList<GameObject> objects;

    Paint paint = new Paint();

    public PlayGame(Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new PlayGameThread(getHolder(), this);
        setFocusable(true);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        objects = new ArrayList<>();

        objects.add(new ArrowColliderBar(getContext(), screenWidth, screenHeight));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        if (frameCount == 50) {
            objects.add(new Arrow(getContext(), Arrow.DIRECTION.LEFT, 5, screenWidth, screenHeight));
        }
        if (frameCount == 100) {
            objects.add(new Arrow(getContext(), Arrow.DIRECTION.DOWN, 10, screenWidth, screenHeight));
            objects.add(new Arrow(getContext(), Arrow.DIRECTION.UP, 10, screenWidth, screenHeight));
            objects.add(new Arrow(getContext(), Arrow.DIRECTION.RIGHT, 10, screenWidth, screenHeight));
        }

        for (GameObject o : objects) {
            o.update(0.0f);

            if (o.shouldDelete()) {
                objects.remove(o);
                System.out.println("Removed!");
            }
        }

        frameCount++;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }

    }
}
