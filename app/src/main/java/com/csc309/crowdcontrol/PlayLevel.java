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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayLevel extends SurfaceView implements SurfaceHolder.Callback
{
    //Brad stuff
    private PlayLevelThread thread;
    private MusicThread audioThread;
    public int currentBeat;
    public String currentSyllable;
    public float songPos;
    public Context appContext;

    //Graphics Stuff
    private int screenWidth;
    private int screenHeight;
    private int frameCount = 0;
    private ArrayList<GameObject> objects;

    Paint paint = new Paint();

    public PlayLevel(Context context) throws FileNotFoundException
    {
        super(context);

        appContext = context;

        getHolder().addCallback(this);
        thread = new PlayLevelThread(getHolder(), this);
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
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
    {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) 
    {
        thread.setRunning(true);
        thread.start();

        audioThread.setRunning(true);
        audioThread.start();
    }

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

    public void update() 
    {
        //System.out.println("UPDATING PLAYLEVEL");
        for (GameObject o : objects) {
            if(o.type == "BradArrow")

            o.update(songPos);

            if (o.shouldDelete()) {
                objects.remove(o);
                System.out.println("Removed!");
            }
        }
    }

    @Override
    public void draw(Canvas canvas) 
    {
        super.draw(canvas);
        if (canvas != null) {
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public void spawnArrow(Arrow.DIRECTION dir, float songPosStart, float songPosTarget)
    {
        //System.out.println("PLAYLEVEL: SPAWNING ARROW");
        objects.add(new Arrow(getContext(), dir, songPosStart, songPosTarget,
                screenWidth, screenHeight));
    }

    public void drawSomesShit(Canvas canvas)
    {
        if (canvas != null)
        {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            paint.setTextAlign(Paint.Align.CENTER);
            int xPos = (canvas.getWidth() / 2);
            int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
            canvas.drawText(Float.toString(songPos), xPos, yPos, paint);
            canvas.drawText(Integer.toString(currentBeat), xPos, yPos + 100, paint);
            canvas.drawText(currentSyllable, xPos, yPos+200, paint);
        }
    }
};
