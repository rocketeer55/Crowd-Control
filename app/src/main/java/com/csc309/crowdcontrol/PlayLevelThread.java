package com.csc309.crowdcontrol;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PlayLevelThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private com.csc309.crowdcontrol.PlayLevel playLevel;
    private boolean running;

    public Canvas canvas;

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

        double ns = 1000000000;
        double delta = 0;

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
                } catch (Exception e) {
                    // This saves our code btw
                } finally {
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
