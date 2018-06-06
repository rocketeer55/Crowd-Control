package com.csc309.crowdcontrol;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicThread extends Thread
{
    private com.csc309.crowdcontrol.PlayLevel playLevel;
    private boolean running;

    private MediaPlayer musicPlayer; //Plays the music
    private Sequencer sequencer; //Reference to the parent instance of PlayLevel

    public MusicThread() {

    }

    public MusicThread(int songID, BeatMap beatMap, PlayLevel playLevel, Context context) {
        super();
        createMusicPlayer(context, songID);
        this.playLevel = playLevel;
        sequencer = new Sequencer(beatMap, playLevel);
    }

    //Creates a MediaPlayer to play music
    public void createMusicPlayer(Context context, int songID)
    {
        this.musicPlayer = MediaPlayer.create(context, songID);
    }

    @Override
    public void run() {

        //Might need to sleep() here for a second to give prep time.
        musicPlayer.start();
        sequencer.start();

        while (running)
        {
            playLevel.songPos = musicPlayer.getCurrentPosition();
            sequencer.update(musicPlayer.getCurrentPosition());

            //Used for testing/debug
            playLevel.currentBeat = sequencer.currentBeat;
            playLevel.currentSyllable = sequencer.currentSyllable;
        }

        //Stops playing music when thread is killed. This is very important.
        musicPlayer.stop();
        musicPlayer.release();
    }

    public boolean checkRunning() {
        return running;
    }
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

}
