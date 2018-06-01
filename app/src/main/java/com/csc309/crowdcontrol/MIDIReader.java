package com.csc309.crowdcontrol;

import java.net.BindException;
import java.util.*;
import java.io.*;
import android.content.res.Resources;
import android.content.Context;
import android.icu.util.Measure;

public class MIDIReader
{
    private static float measureVal = 0.0f;
    private static int measure = 1;
    private static int elapsedTime;

    public static MIDINode head;
    public static MIDINode tail;

    private static class MIDINode
    {
        public MIDINode next;
        public String statusByte;
        public String key;
        public String vel;
        public BeatMap.NOTE_LENGTH noteLength;
        public int timeAdded;

        public MIDINode(String statusByte, String key, String vel, int timeAdded)
        {
            this.statusByte = statusByte;
            this.key = key;
            this.vel = vel;
            this.timeAdded = timeAdded;
        }

        public MIDINode next()
        {
            return next;
        }
    }

    private static void incrementMeasure(float val)
    {
        if(val > 100)
        {
            float temp = val;
            while(temp > 100.0)
            {
                measureVal += 100.0;
                measure++;
                temp -= 100;
            }
        }

        measureVal += val;

        if(measureVal >= 97.0f)
        {
            measure++;
            float temp = (measureVal - 100.0f);
            measureVal = 0.0f + temp;
        }
    }


    public static BeatMap.NOTE_LENGTH hexToNoteLength(String hexVal)
    {
        BeatMap.NOTE_LENGTH returnVal;
        switch(hexVal)
        {
            case "83": returnVal = BeatMap.NOTE_LENGTH.WHOLE;
                        incrementMeasure(100.0f);
                break;
            case "c1": returnVal = BeatMap.NOTE_LENGTH.HALF;
                        incrementMeasure(50.0f);
                break;
            case "60": returnVal = BeatMap.NOTE_LENGTH.QUARTER;
                        incrementMeasure(25.0f);
                break;
            case "30": returnVal = BeatMap.NOTE_LENGTH.EIGHTH;
                        incrementMeasure(12.5f);
                break;
            case "18": returnVal = BeatMap.NOTE_LENGTH.SIXTEENTH;
                        incrementMeasure(6.25f);
                break;
            case "40": returnVal = BeatMap.NOTE_LENGTH.QUARTER_TRIPLET;
                        incrementMeasure((50.0f)/3.0f);
                break;
            case "20": returnVal = BeatMap.NOTE_LENGTH.EIGHTH_TRIPLET;
                        incrementMeasure((25.0f)/3.0f);
                break;
            case "10": returnVal = BeatMap.NOTE_LENGTH.SIXTEENTH_TRIPLET;
                        incrementMeasure((12.5f)/3.0f);
                break;
            case "c4": returnVal = BeatMap.NOTE_LENGTH.DOTTED_WHOLE;
                        incrementMeasure(150.0f);
                break;
            case "a2": returnVal = BeatMap.NOTE_LENGTH.DOTTED_HALF;
                        incrementMeasure(75.0f);
                break;
            case "91": returnVal = BeatMap.NOTE_LENGTH.DOTTED_QUARTER;
                        incrementMeasure(37.5f);
                break;
            case "48": returnVal = BeatMap.NOTE_LENGTH.DOTTED_EIGHTH;
                        incrementMeasure(18.75f);
                break;
            case "24": returnVal = BeatMap.NOTE_LENGTH.DOTTED_SIXTEENTH;
                        incrementMeasure(9.375f);
                break;
            case "78": returnVal = BeatMap.NOTE_LENGTH.QUARTER_TIE_SIXTEENTH;
                        incrementMeasure(31.25f);
                break;
            case "a9": returnVal = BeatMap.NOTE_LENGTH.QUARTER_TIE_DOTTED_EIGHTH;
                        incrementMeasure(43.65f);
                break;
            case "d9": returnVal = BeatMap.NOTE_LENGTH.HALF_TIE_SIXTEENTH;
                        incrementMeasure(56.25f);
                break;
            default: returnVal = BeatMap.NOTE_LENGTH.NOTE_OFF;
                break;
        }

        return returnVal;
    }

    public static Arrow.DIRECTION hexToDir(String hexVal)
    {
        Arrow.DIRECTION returnVal;
        switch(hexVal)
        {
            case "53": returnVal = Arrow.DIRECTION.LEFT;
                break;
            case "4f": returnVal = Arrow.DIRECTION.RIGHT;
                break;
            case "4c": returnVal = Arrow.DIRECTION.UP;
                break;
            case "48": returnVal = Arrow.DIRECTION.DOWN;
                break;
            default: returnVal = Arrow.DIRECTION.UP;
                System.out.println("WRONG NOTE DETECTED. GIVE UP ON YOUR LIFE.");
                break;
        }
        return returnVal;
    }

    public static void readMidi(Context context, int resID)
    {
        Resources resources = context.getResources();
        InputStream readStream = resources.openRawResource(resID);
        Scanner scanner = new Scanner(readStream);

        int lastBar = 1;
        try
        {
            int tempVal = 0;
            String val = "0";

            //Read through Header Chunk
            int i = 0;
            System.out.print("HEADER: ");
            while(i < 4)
            {
                tempVal = readStream.read();
                if(tempVal != -1)
                {
                    val = Integer.toHexString(tempVal);
                    i++;
                    System.out.print(val);
                }
            }
            System.out.println();

            int headerLength = 0;
            i = 0;
            while(i < 4)
            {
                headerLength += readStream.read();
                i++;
            }

            System.out.println("HEADER LENGTH: " + headerLength);

            i = 0;
            while(i < headerLength)
            {
                readStream.read();
                i++;
            }

            //Read through Track Chunk
            System.out.println("TRACK HEADER: ");
            i = 0;
            while(i < 4)
            {
                tempVal = readStream.read();
                if(tempVal != -1)
                {
                    val = Integer.toHexString(tempVal);
                    System.out.print(val);
                }
                i++;
            }

            System.out.println();

            int trackHeaderLength = 0;
            i = 0;
            while(i < 4)
            {
                trackHeaderLength += readStream.read();
                i++;
            }

            System.out.println("TRACK HEADER LENGTH: " + trackHeaderLength);

            i = 0;

            while(i < 4)
            {
                readStream.read();
                i++;
            }

            val = "00";

            //Read through MIDI Track Name, which is a variable length
            System.out.println("BYPASSING: ");

            while(tempVal != 255)
            {
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);
                System.out.print(val);
            }

            System.out.println();

            i = 0;

            //Read through Time Signature Data
            System.out.print("SKIPPING TIME SIGNAGURE DATA: ");
            System.out.print(val);

            while(i < 7)
            {
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);
                System.out.print(val);
                i++;
            }
            System.out.println();

            //Read through duplicate (for some reason) Time Signature Data
            System.out.print("SKIPPING DUPLICATE TIME SIGNAGURE DATA: ");
            System.out.print(val);

            i = 0;
            while(i < 8)
            {
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);
                System.out.print(val);
                i++;
            }
            System.out.println();

            //Read through MIDI events. This is the important information.
            System.out.println("START OF MIDI EVENTS SHOULD BE HERE");

            measureVal = 0.0f;
            measure = 1;

            boolean firstPass = true;
            int delta_t;

            while(tempVal != -1)
            {
                if(firstPass)
                {
                    System.out.println("Got here");
                    firstPass = false;
                    delta_t = 0;
                    elapsedTime = 0;
                }

                else
                {
                    boolean reading = true;
                    delta_t = 0;

                    //System.out.println("Got there");

                    while(reading)
                    {
                        tempVal = readStream.read();
                        delta_t += tempVal;
                        elapsedTime += tempVal;
                        if((tempVal & 0x80) == 0x00)
                        {
                            reading = false;
                        }
                    }
                }
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);

                //System.out.println("VAL: " + val);

                //Track End Indicator
                if(tempVal == 255)
                {
                    break;
                }

                //Note On Message
                else if (tempVal == 0x90)
                {
                    String statusByte = val;

                    tempVal = readStream.read();
                    val = Integer.toHexString(tempVal);
                    String key = val;

                    tempVal = readStream.read();
                    val = Integer.toHexString(tempVal);
                    String vel = val;

                    enqueue(new MIDINode(statusByte, key, vel, elapsedTime));
                }

                //Note Off Message
                else if (tempVal == 0x80)
                {
                    readStream.read();
                    readStream.read();
                    MIDINode tempNode = dequeue();

                    System.out.println(measure + " " +
                            hexToDir(tempNode.key) + " " + hexToNoteLength(
                            Integer.toHexString(elapsedTime - tempNode.timeAdded)));
                   /* System.out.println("NODE DEQUEUED: SB: " + tempNode.statusByte
                            + " KEY: " + hexToDir(tempNode.key) + " VEL: " + tempNode.vel +
                            " DT: " + hexToNoteLength(
                                    Integer.toHexString(elapsedTime - tempNode.timeAdded)));*/
                }
            }
            System.out.println();
        }
        catch (IOException e)
        {

        }
    }

    public static void enqueue(MIDINode node)
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

    public static boolean isEmpty()
    {
        return (tail == null);
    }

    public static MIDINode dequeue()
    {
        if(head == null)
        {
            return null;
        }

        MIDINode returnNode = head;

        head = head.next;

        if(head == null)
        {
            tail = null;
        }

        return returnNode;
    }

    public static MIDINode dequeueNode()
    {
        MIDINode returnNode = head;

        head = head.next;

        if(head == null)
        {
            tail = null;
        }

        return returnNode;
    }
}
