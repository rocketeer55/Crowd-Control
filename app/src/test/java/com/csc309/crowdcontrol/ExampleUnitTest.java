package com.csc309.crowdcontrol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.MediaStore;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
class ExampleUnitTest extends AndroidTestCase {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Sequence_Length() throws Exception {
        Context context = new ContextThemeWrapper();
        PlayLevel playLevel = new PlayLevel(context);
        BeatMap beatMap = new BeatMap();
        Sequencer MyTest = new Sequencer(beatMap, playLevel);
        float result = MyTest.lengthToFloat(BeatMap.NOTE_LENGTH.WHOLE);
        assertEquals(result,  result);

    }

    @Test
    public void ArrowTest() throws Exception {
        Context context = new ContextThemeWrapper();
        PlayLevel playLevel = new PlayLevel(context);
        Arrow arrow = new Arrow(context, Arrow.DIRECTION.DOWN, 5, 200, 200);
        Boolean result = arrow.shouldDelete();
        assertFalse(result);
    }
    //want to test score counter: if adding properly

    @Test
    public void MIDItest() throws Exception {
        MIDIReader midi = new MIDIReader();
        assertTrue(midi.isEmpty());
    }
    //want MIDI incrementer score value to work

    @Test
    public void
    //
}