package com.csc309.crowdcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.MediaStore;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

    public PlayLevel(Context context) throws FileNotFoundException
    {
        super(context);

        appContext = context;

        getHolder().addCallback(this);
        thread = new PlayLevelThread(getHolder(), this);

        //This line will be updated to allow for different song selections.
        audioThread = new MusicThread(R.raw.shelterwithprep, new BeatMap(R.raw.shelterbeatmap, context),this, context);
        setFocusable(true);

        //Some shit Brad don't wanna F|_|CK with
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        objects = new ArrayList<>();

        objects.add(new ArrowColliderBar(getContext(), screenWidth, screenHeight));
        objects.add(new DJControllerBar(getContext(), screenWidth, screenHeight));
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

    //Updates the positional information of every GameObject
    public void update() 
    {
        for (GameObject o : objects) {
<<<<<<< HEAD
=======

>>>>>>> master
            o.update(songPos);
        }

        if (customGestureDetector.left) {
            // there was a left swipe last frame - DO SOMETHING
            System.out.println("left");
            // set it back to false after handling the swipe
            customGestureDetector.left = false;
        }
        if (customGestureDetector.right) {
            // there was a right swipe last frame - DO SOMETHING
            System.out.println("right");
            // set it back to false after handling the swipe
            customGestureDetector.right = false;
        }
        if (customGestureDetector.up) {
            // there was an up swipe last frame - DO SOMETHING
            System.out.println("up");
            // set it back to false after handling the swipe
            customGestureDetector.up = false;
        }
        if (customGestureDetector.down) {
            // there was a down swipe last frame - DO SOMETHING
            System.out.println("down");
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
        }
    }

    //Called by Sequencer to add arrows to the screen
    public void spawnArrow(Arrow.DIRECTION dir, float songPosStart, float songPosTarget)
    {
        objects.add(new Arrow(getContext(), dir, songPosStart, songPosTarget,
                screenWidth, screenHeight));
    }
<<<<<<< HEAD

};
=======
}
>>>>>>> master
