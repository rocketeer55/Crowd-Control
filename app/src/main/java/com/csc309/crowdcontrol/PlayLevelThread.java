package com.csc309.crowdcontrol;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.csc309.crowdcontrol.PlayLevel;

public class PlayLevelThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private com.csc309.crowdcontrol.PlayLevel playLevel;
    private boolean running;
    private int targetFPS = 60;
    private double averageFPS;

    public static Canvas canvas;

    public PlayLevelThread(SurfaceHolder surfaceHolder, PlayLevel playLevel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.playLevel = playLevel;
    }

    @Override
    public void run() 
    {
        long startTime;
        long lastTime = System.nanoTime();
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        double amountOfTicks = 60.0;
        double ns = 1000000000;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            startTime = System.nanoTime();
            delta += (startTime - lastTime) / ns;
            lastTime = startTime;

            canvas = null;

            while(delta >= (1/60) && running)
            {
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized(surfaceHolder) {
                        this.playLevel.update();
                        this.playLevel.draw(canvas);
                    }
                } catch (Exception e) {} finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                delta -= (1/60);
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

}
