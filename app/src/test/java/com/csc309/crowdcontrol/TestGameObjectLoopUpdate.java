package com.csc309.crowdcontrol;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestGameObjectLoopUpdate {

    //Game object array only holds 2 necessary objects that are used during the update method.
    //Ensures that whenever dealing with game objects they are all changed.
    @Test
    public void TestObjectLoopNotEntered() throws Exception
    {
        ArrayList<GameObject> objects = new ArrayList<>();
        float songPos = 0;
        boolean enteredLoop = false;
        for (GameObject o : objects) {
            enteredLoop = true;
            o.update(songPos);
        }
        assertFalse(enteredLoop);
    }

    @Test
    public void TestObjectLoopEnteredOnce() throws Exception
    {
        ArrayList<GameObject> objects = new ArrayList<>();
        float songPos = 0;
        int LoopEnterCount = 0;
        objects.add(new ArrowColliderBar());
        for (GameObject o : objects) {
            LoopEnterCount++;
            o.update(songPos);
        }
        assertEquals(1, LoopEnterCount);
    }

    @Test
    public void TestObjectLoopEnteredTwice() throws Exception
    {
        ArrayList<GameObject> objects = new ArrayList<>();
        float songPos = 0;
        int LoopEnterCount = 0;
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        for (GameObject o : objects) {
            LoopEnterCount++;
            o.update(songPos);
        }
        assertEquals(2, LoopEnterCount);
    }

    @Test
    public void TestObjectLoopEnteredALot() throws Exception
    {
        ArrayList<GameObject> objects = new ArrayList<>();
        float songPos = 0;
        int LoopEnterCount = 0;
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        objects.add(new ArrowColliderBar());
        objects.add(new DJControllerBar());
        for (GameObject o : objects) {
            LoopEnterCount++;
            o.update(songPos);
        }
        assertEquals(14, LoopEnterCount);
    }
}
