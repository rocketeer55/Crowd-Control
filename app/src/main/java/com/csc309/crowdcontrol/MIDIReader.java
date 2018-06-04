package com.csc309.crowdcontrol;

import java.util.*;
import java.io.*;
import android.content.res.Resources;
import android.content.Context;

public class MIDIReader
{
    private static float measureVal = 0.0f;
    private static int measure = 1;
    private static int elapsedTime;

    public static MIDINode head;
    public static MIDINode tail;

    private MIDIReader() {
        // Do nothing}
    }

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
                measure++;
                temp -= 100;
            }
            measureVal += temp;

            if(measureVal >= 97.0f)
            {
                measure++;
                float temp2 = (measureVal - 100.0f);
                measureVal = 0.0f + temp2;
            }
        }

        else
        {
            measureVal += val;

            if(measureVal >= 97.0f)
            {
                measure++;
                float temp = (measureVal - 100.0f);
                measureVal = 0.0f + temp;
            }
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
            case "f4": returnVal = BeatMap.NOTE_LENGTH.DOTTED_WHOLE_TIE_EIGHTH;
                        incrementMeasure(162.5f);
                break;
            case "b3": returnVal = BeatMap.NOTE_LENGTH.WHOLE_TIE_EIGHTH;
                        incrementMeasure(112.5f);
                break;
            case "d2": returnVal = BeatMap.NOTE_LENGTH.DOTTED_HALF_TIE_EIGHTH;
                        incrementMeasure(87.5f);
                break;
            case "f1": returnVal = BeatMap.NOTE_LENGTH.HALF_TIE_EIGHTH;
                        incrementMeasure(62.5f);
                break;
            default: returnVal = BeatMap.NOTE_LENGTH.UNKNOWN_LENGTH;
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
                break;
        }
        return returnVal;
    }

    public static void readMidi(Context context, int resID)
    {
        Resources resources = context.getResources();
        InputStream readStream = resources.openRawResource(resID);

        try
        {
            int tempVal = 0;
            String val = "0";

            //Read through Header Chunk
            int i = 0;
            while(i < 4)
            {
                tempVal = readStream.read();
                if(tempVal != -1)
                {
                    val = Integer.toHexString(tempVal);
                    i++;
                }
            }

            int headerLength = 0;
            i = 0;
            while(i < 4)
            {
                headerLength += readStream.read();
                i++;
            }


            i = 0;
            while(i < headerLength)
            {
                readStream.read();
                i++;
            }

            //Read through Track Chunk
            i = 0;
            while(i < 4)
            {
                tempVal = readStream.read();
                if(tempVal != -1)
                {
                    val = Integer.toHexString(tempVal);
                }
                i++;
            }

            int trackHeaderLength = 0;
            i = 0;
            while(i < 4)
            {
                trackHeaderLength += readStream.read();
                i++;
            }


            i = 0;

            while(i < 4)
            {
                readStream.read();
                i++;
            }

            val = "00";

            //Read through MIDI Track Name, which is a variable length

            while(tempVal != 255)
            {
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);
            }


            i = 0;

            //Read through Time Signature Data

            while(i < 7)
            {
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);
                i++;
            }

            //Read through duplicate (for some reason) Time Signature Data

            i = 0;
            while(i < 8)
            {
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);
                i++;
            }

            //Read through MIDI events. This is the important information.

            measureVal = 0.0f;
            measure = 1;

            boolean firstPass = true;

            while(tempVal != -1)
            {
                if(firstPass)
                {
                    firstPass = false;
                    elapsedTime = 0;
                }

                else
                {
                    boolean reading = true;

                    while(reading)
                    {
                        tempVal = readStream.read();
                        elapsedTime += tempVal;
                        if((tempVal & 0x80) == 0x00)
                        {
                            reading = false;
                        }
                    }
                }
                tempVal = readStream.read();
                val = Integer.toHexString(tempVal);

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
                    dequeue();
                }
            }
        }
        catch (IOException e)
        {
            // Who knows what to do here
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
