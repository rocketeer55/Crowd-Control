package com.csc309.crowdcontrol;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PlayLevel extends SurfaceView implements SurfaceHolder.Callback
{
    //Brad stuff
    private PlayLevelThread thread; //Thread that controls PlayLevel.update()
    private MusicThread audioThread; //Thread that plays music and runs the Sequencer
    public int currentBeat; //Used for testing/debug.
    public String currentSyllable; //Used for testing/debug.
    public float songPos; //Current position of song playing. Used to control EVERY GAMEOBJECT.

    public Context appContext;

    //Graphics Stuff
    private int screenWidth;
    private int screenHeight;
    private int frameCount = 0;
    private ArrayList<GameObject> objects;
    Paint paint = new Paint();

    //Controls stuff
    private GestureDetector mGestureDetector;
    private CustomGestureDetector customGestureDetector;
    private LinkedList<Arrow> arrowList;

    private int score = 0;
    private int multipler = 1;

    private int missedCount = 0;
    private int noteStreak = 0;


    public PlayLevel(Context context) throws FileNotFoundException
    {
        super(context);

        appContext = context;

        getHolder().addCallback(this);
        thread = new PlayLevelThread(getHolder(), this);

        //This line will be updated to allow for different song selections.
        audioThread = new MusicThread(R.raw.thethrillwithprep,
                new BeatMap(R.raw.thethrillbeatmap, context),this, context);
        setFocusable(true);

        //Some shit Brad don't wanna F|_|CK with
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        objects = new ArrayList<>();
        arrowList = new LinkedList<>();

        objects.add(new ArrowColliderBar(getContext(), screenWidth, screenHeight));
        objects.add(new DJControllerBar(getContext(), screenWidth, screenHeight));
        //objects.add(new UpdateScore(getContext(), screenWidth, screenHeight));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mGestureDetector.onTouchEvent(event)) {
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    //Starts the MusicThread and PlayLevelThread
    @Override
    public void surfaceCreated(SurfaceHolder holder) 
    {
        thread.setRunning(true);
        thread.start();

        audioThread.setRunning(true);
        audioThread.start();

        // Create an object of our Custom Gesture Detector Class
        customGestureDetector = new CustomGestureDetector();

        customGestureDetector.screenWidth = screenWidth;
        customGestureDetector.screenHeight = screenHeight;
        // Create a GestureDetector
        mGestureDetector = new GestureDetector(appContext, customGestureDetector);
    }

    //Kills the MusicThread and PlayLevelThread. Stops music.
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                audioThread.setRunning(false);
                thread.join();
                audioThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }


    // update score here
    // have score extend game object
    // Updates the positional information of every GameObject
    public void update()
    {

        // use this delta var to calculate score
        float delta;

        // score hits and points
        int okay = 100;
        int good = 250;
        int excellent = 500;

        if(noteStreak >= 10)
            multipler = 8;
        else if(noteStreak >= 7)
            multipler = 4;
        else if(noteStreak >= 4)
            multipler = 2;
        else
            multipler = 1;


            //Delete if issue, should launch the gameover option
        if(missedCount >= 10) {
            Context context = getContext();
            Intent intent = new Intent(context, GameOverActivity.class);
            intent.putExtra("Score", score);
            context.startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            ((Activity) context).finish();
            missedCount = 0;

        }
        for (GameObject o : objects) {
            o.update(songPos);
        }

        for (Arrow arr : arrowList)
        {
            arr.update(songPos);
            if(arr.wasMissed == true) {
                missedCount +=1;
            }
        }

        Arrow temp = arrowList.peek();

        if(temp != null)
        {
            if (temp.shouldDequeue() == true && temp.wasDequeued == false)
            {
                removeArrow();
            }
        }

        if (customGestureDetector.left) {
            // there was a left swipe last frame - DO SOMETHING
            System.out.println("left");
            Arrow arr = arrowList.peek();
            if(arr != null)
            {
                if(songPos > (arr.songPosTarget - 100)
                        && songPos < (arr.songPosTarget + 100 ) &&
                        arr.mode == Arrow.DIRECTION.LEFT)
                {
                    // calculate delta
                    delta = Math.abs(songPos - arr.songPosTarget);
                    if (0.333 >= delta && delta >= 0)
                        score += excellent * multipler;
                    else if (0.666 >= delta && delta > 0.333)
                        score += good * multipler;
                    else
                        score += okay * multipler;
                    missedCount = 0;
                    noteStreak ++;
                    removeArrow();
                }
                else {
                    missedCount += 1;
                    noteStreak = 0;

                }
            }
            // set it back to false after handling the swipe
            customGestureDetector.left = false;
        }
        if (customGestureDetector.right) {
            // there was a right swipe last frame - DO SOMETHING
            System.out.println("right");
            Arrow arr = arrowList.peek();
            if(arr != null)
            {
                if(songPos > (arr.songPosTarget - 100)
                        && songPos < (arr.songPosTarget + 100 ) &&
                        arr.mode == Arrow.DIRECTION.RIGHT)
                {
                    // calculate delta
                    delta = Math.abs(songPos - arr.songPosTarget);
                    if (33 >= delta && delta >= 0)
                        score += excellent * multipler;
                    else if (66 >= delta && delta > 33)
                        score += good * multipler;
                    else
                        score += okay * multipler;
                    missedCount = 0;
                    noteStreak ++;
                    removeArrow();
                }
                else {
                    missedCount += 1;
                    noteStreak = 0;

                }
            }
            // set it back to false after handling the swipe
            customGestureDetector.right = false;
        }
        if (customGestureDetector.up) {
            // there was an up swipe last frame - DO SOMETHING
            System.out.println("up");
            Arrow arr = arrowList.peek();
            if(arr != null)
            {
                if(songPos > (arr.songPosTarget - 100)
                        && songPos < (arr.songPosTarget + 100 ) &&
                        arr.mode == Arrow.DIRECTION.UP)
                {
                    // calculate delta
                    delta = Math.abs(songPos - arr.songPosTarget);
                    if (33 >= delta && delta >= 0)
                        score += excellent * multipler;
                    else if (66 >= delta && delta > 33)
                        score += good * multipler;
                    else
                        score += okay * multipler;
                    missedCount = 0;
                    noteStreak ++;
                    removeArrow();
                }
                else {
                    missedCount += 1;
                    noteStreak = 0;
                }
            }
            // set it back to false after handling the swipe
            customGestureDetector.up = false;
        }
        if (customGestureDetector.down) {
            // there was a down swipe last frame - DO SOMETHING
            System.out.println("down");
            Arrow arr = arrowList.peek();
            if(arr != null)
            {
                if(songPos > (arr.songPosTarget - 100)
                        && songPos < (arr.songPosTarget + 100 ) &&
                        arr.mode == Arrow.DIRECTION.DOWN)
                {
                    // calculate delta
                    delta = Math.abs(songPos - arr.songPosTarget);
                    if (33 >= delta && delta >= 0)
                        score += excellent;
                    else if (66 >= delta && delta > 33)
                        score += good;
                    else
                        score += okay;
                    missedCount = 0;
                    noteStreak ++;
                    removeArrow();
                }
                else {
                    missedCount += 1;
                    noteStreak = 0;
                }
            }
            // set it back to false after handling the swipe
            customGestureDetector.down = false;
        }
    }

    //Draws every GameObject
    @Override
    public void draw(Canvas canvas) 
    {
        super.draw(canvas);
        if (canvas != null) {
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            for (GameObject o : objects) {
                if (!o.shouldDelete()) {o.draw(canvas);};
            }

            for (Arrow arr : arrowList)
            {
                arr.draw(canvas);
            }

            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, screenWidth - 1, 100, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(48f);
            canvas.drawText("Score: " + score, 20,80,paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(48f);
            canvas.drawText("Multipler: " + multipler + "x", screenWidth/2,80,paint);
        }
    }

    //Called by Sequencer to add arrows to the screen
    public void spawnArrow(Arrow.DIRECTION dir, float songPosStart, float songPosTarget)
    {
        Arrow arr = new Arrow(getContext(), dir, songPosStart, songPosTarget,
                screenWidth, screenHeight);
        arrowList.offer(arr);
    }

    public void removeArrow()
    {
        Arrow arr = arrowList.poll();
        System.out.println("DEQUEUED");
        arr.wasDequeued = true;
    }
}
