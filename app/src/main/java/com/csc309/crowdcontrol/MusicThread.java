package com.csc309.crowdcontrol;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicThread extends Thread
{
    private com.csc309.crowdcontrol.PlayLevel playLevel;
    private boolean running;
    private int targetFPS = 60;
    private double averageFPS;

    private MediaPlayer musicPlayer;
    public Context mainContext;
    private Sequencer sequencer;

    public MusicThread(int songID, BeatMap beatMap, PlayLevel playLevel, Context context) {
        super();
        createMusicPlayer(context, songID);
        this.playLevel = playLevel;
        sequencer = new Sequencer(beatMap, playLevel);
    }

    public void createMusicPlayer(Context context, int songID)
    {
        this.musicPlayer = MediaPlayer.create(context, songID);
    }

    @Override
    public void run() {

        musicPlayer.start();
        //sequencer = new Sequencer(4, 100);
        sequencer.Start();

        while (running)
        {
            playLevel.songPos = musicPlayer.getCurrentPosition();
            sequencer.Update(musicPlayer.getCurrentPosition());
            playLevel.currentBeat = sequencer.currentBeat;
            playLevel.currentSyllable = sequencer.currentSyllable;
        }

        musicPlayer.stop();
        musicPlayer.release();
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

}
