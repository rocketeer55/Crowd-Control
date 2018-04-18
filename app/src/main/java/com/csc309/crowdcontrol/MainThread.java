package com.csc309.crowdcontrol;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.csc309.crowdcontrol.MainMenu;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private com.csc309.crowdcontrol.MainMenu mainMenu;
    private boolean running;
    private int targetFPS = 30;
    private double averageFPS;

    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, MainMenu mainMenu) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.mainMenu = mainMenu;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.mainMenu.draw(canvas);
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

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS) {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000.0);
                frameCount = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

}