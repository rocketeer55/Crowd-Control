package com.csc309.crowdcontrol;

import java.io.*;
import java.util.*;
import android.app.Activity;
import android.content.res.Resources;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BeatMap
{
    public enum NOTE_LENGTH
    {
        WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH,
        DOTTED_WHOLE, DOTTED_HALF, DOTTED_QUARTER, DOTTED_EIGHTH, DOTTED_SIXTEENTH,
        QUARTER_TIE_SIXTEENTH, QUARTER_TIE_DOTTED_EIGHTH, HALF_TIE_SIXTEENTH,
        QUARTER_TRIPLET, EIGHTH_TRIPLET, SIXTEENTH_TRIPLET,
        NOTE_OFF
    }

    private static class Node
    {
        public Note note;
        public Node next;
        public int val;

        public Node(Note note)
        {
            this.note = note;
        }

        public Node(int val)
        {
            this.val = val;
        }

        public Node next()
        {
            return next;
        }
    }

    public float offset;
    public int beatsPerMeasure;
    public float bpm;

    public Node head;
    public Node tail;

    public BeatMap(int resID, Context context) throws FileNotFoundException
    {
        Resources resources = context.getResources();
        InputStream readStream = resources.openRawResource(resID);

        Scanner scanner = new Scanner(readStream);

        createMap(scanner);
    }

    public BeatMap()
    {

    }

    NOTE_LENGTH stringToNoteLength(String string)
    {
        NOTE_LENGTH returnVal;

        switch(string)
        {
            case "WHOLE": returnVal = NOTE_LENGTH.WHOLE;
                break;
            case "HALF": returnVal = NOTE_LENGTH.HALF;
                break;
            case "QUARTER": returnVal = NOTE_LENGTH.QUARTER;
                break;
            case "EIGHTH": returnVal = NOTE_LENGTH.EIGHTH;
                break;
            case "SIXTEENTH": returnVal = NOTE_LENGTH.SIXTEENTH;
                break;
            case "DOTTED_WHOLE": returnVal = NOTE_LENGTH.DOTTED_WHOLE;
                break;
            case "DOTTED_HALF": returnVal = NOTE_LENGTH.DOTTED_HALF;
                break;
            case "DOTTED_QUARTER": returnVal = NOTE_LENGTH.DOTTED_QUARTER;
                break;
            case "DOTTED_EIGHTH": returnVal = NOTE_LENGTH.DOTTED_EIGHTH;
                break;
            case "DOTTED_SIXTEENTH": returnVal = NOTE_LENGTH.DOTTED_SIXTEENTH;
                break;
            case "QUARTER_TIE_SIXTEENTH": returnVal = NOTE_LENGTH.QUARTER_TIE_SIXTEENTH;
                break;
            case "QUARTER_TIE_DOTTED_EIGHTH": returnVal = NOTE_LENGTH.QUARTER_TIE_DOTTED_EIGHTH;
                break;
            case "HALF_TIE_SIXTEENTH": returnVal = NOTE_LENGTH.HALF_TIE_SIXTEENTH;
                break;
            case "QUARTER_TRIPLET": returnVal = NOTE_LENGTH.DOTTED_SIXTEENTH;
                break;
            case "EIGHTH_TRIPLET": returnVal = NOTE_LENGTH.EIGHTH_TRIPLET;
                break;
            case "SIXTEENTH_TRIPLET": returnVal = NOTE_LENGTH.SIXTEENTH_TRIPLET;
                break;
            default: returnVal = NOTE_LENGTH.QUARTER;
                break;
        }

        return returnVal;
    }

    Arrow.DIRECTION stringToArrowDir(String string)
    {
        Arrow.DIRECTION returnVal;

        switch(string)
        {
            case "UP": returnVal = Arrow.DIRECTION.UP;
                break;
            case "DOWN": returnVal = Arrow.DIRECTION.DOWN;
                break;
            case "LEFT": returnVal = Arrow.DIRECTION.LEFT;
                break;
            case "RIGHT": returnVal = Arrow.DIRECTION.RIGHT;
                break;
            default: returnVal = Arrow.DIRECTION.UP;
                break;
        }

        return returnVal;
    }

    public void createMap(Scanner scanner)
    {
        String line;

        scanner.nextLine();

        offset = scanner.nextFloat();
        scanner.nextLine();
        beatsPerMeasure = scanner.nextInt();
        scanner.nextLine();
        bpm = scanner.nextFloat();
        scanner.nextLine();

        //System.out.println("Offset: " + offset +
        // " BeatsPerMeasure: " + beatsPerMeasure + " BPM: " + bpm);

        while(scanner.hasNext())
        {
            line = scanner.nextLine();
            System.out.println(line);
            Scanner lineScanner = new Scanner(line);

            int tempBarNo = lineScanner.nextInt();
            NOTE_LENGTH tempNoteLength = stringToNoteLength(lineScanner.next());
            Arrow.DIRECTION tempArrowDir = stringToArrowDir(lineScanner.next());

            Note tempNote = new Note(tempBarNo, tempNoteLength, tempArrowDir);
            Node tempNode = new Node(tempNote);

            enqueue(tempNode);
        }

        //printQ();
    }

    public void enqueue(Node node)
    {
        if(tail == null)
        {
            tail = node;
            head = node;
        }

        else
        {
            if(head.next == null)
            {
                head.next = tail;
            }

            tail.next = node;
            tail = node;
        }
    }

    public boolean isEmpty()
    {
        return (tail == null);
    }

    public Note dequeue()
    {
        if(head == null)
        {
            return null;
        }

        Node returnNode = head;

        head = head.next;

        if(head == null)
        {
            tail = null;
        }

        return returnNode.note;
    }

    public Node dequeueNode()
    {
        Node returnNode = head;

        head = head.next;

        if(head == null)
        {
            tail = null;
        }

        return returnNode;
    }

    public void printQ()
    {
        Node cur = head;

        while(cur != null)
        {
            System.out.println("Measure: " + cur.note.measure + " Len: " + cur.note.noteLength +
                " Dir: " + cur.note.arrowDirection);
            cur = cur.next();
        }
    }

    public void qTest()
    {
        for(int i = 0; i < 10; i++)
        {
            Node newNode = new Node(i);
            enqueue(newNode);
            System.out.println("Enqueued: " + newNode.val +
                    ". Head = " + head.val + " Tail = " + tail.val);
        }

        while(!isEmpty())
        {
            Node removedNode = dequeueNode();
            if(!isEmpty())
            {
                System.out.println("Dequeued: " + removedNode.val + ". Head = " + head.val +
                        " Tail = " + tail.val + " isEmpty = " + isEmpty());
            }
        }
    }
}
