package com.csc309.crowdcontrol;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PlayGameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private com.csc309.crowdcontrol.PlayGame playGame;
    private boolean running;
    private int targetFPS = 30;
    private double averageFPS;

    public static Canvas canvas;

    public PlayGameThread(SurfaceHolder surfaceHolder, PlayGame playGame) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.playGame = playGame;
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
                    this.playGame.update();
                    this.playGame.draw(canvas);
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
