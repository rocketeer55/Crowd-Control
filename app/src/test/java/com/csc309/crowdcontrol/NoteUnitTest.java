package com.csc309.crowdcontrol;
import org.junit.Test;
import static org.junit.Assert.*;

public class NoteUnitTest {
    public NoteUnitTest() {

    }
    @Test
    public void MeasureTest() {
        Note note = new Note(5, Arrow.DIRECTION.DOWN);
        assertEquals(5, note.Measure());
    }

    @Test
    public void DirectionTest() {
        Note note = new Note(5, Arrow.DIRECTION.DOWN);
        assertEquals(Arrow.DIRECTION.DOWN, note.arrowDirection);
    }
}
