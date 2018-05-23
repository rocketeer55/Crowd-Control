package com.csc309.crowdcontrol;

public class Sequencer
{
    int quarterNotesPerBar;
    public float bpm;
    public float quarterNote;
    public float eighthNote;
    public float sixteenthNote;
    public float lengthOfMeasure;
    //public float songPosition;
    //public float deltaSongPosition;
    //public float lastHit;
    //public float actualLastHit;
    //float nextBeatTime = 0.0f;
    //float nextBarTime = 0.0f;
    public float offset = 0.2f *1000;
    //public float addOffset;
    //public static float offsetstatic;
    //public static boolean hasOffsetAdjusted;
    public int beatNumber = 0;
    public int barNumber = 0;
    public float lastBeat;

    public int currentBeat;
    public int currentBar;

    public String currentSyllable;
    public float lastBeatLength;

    public BeatMap beatMap;
    public float triggerTime;
    public Note currentNote;

    PlayLevel playLevel;

    public Sequencer(BeatMap beatMap, PlayLevel playLevel)
    {
        this.playLevel = playLevel;
        this.beatMap = beatMap;
        quarterNotesPerBar = beatMap.beatsPerMeasure;
        bpm = beatMap.bpm;
    }

    public void Start()
    {
        lastBeat = 0;
        currentBeat = 0;
        currentBar = 1;
        currentSyllable = "PREP TIME";

        quarterNote = (60/bpm) * 1000;
        eighthNote = quarterNote/2;
        sixteenthNote = quarterNote/4;

        lengthOfMeasure = (quarterNotesPerBar * quarterNote);

        //offset += lengthOfMeasure;

        triggerTime = offset;
        lastBeatLength = offset;

        currentNote = beatMap.dequeue();
    }

    public String dirToString(Arrow.DIRECTION arrowDir)
    {
        String returnVal;

        switch(arrowDir)
        {
            case UP: returnVal = "UP";
                break;
            case DOWN: returnVal = "DOWN";
                break;
            case LEFT: returnVal = "LEFT";
                break;
            case RIGHT: returnVal = "RIGHT";
                break;
            default: returnVal = "UP";
                break;
        }

        return returnVal;
    }

    public float lengthToFloat(BeatMap.NOTE_LENGTH noteLength)
    {
        float returnVal;

        switch(noteLength)
        {
            case WHOLE: returnVal = (4 * quarterNote);
                break;
            case HALF: returnVal = (2 * quarterNote);
                break;
            case QUARTER: returnVal = quarterNote;
                break;
            case EIGHTH: returnVal = eighthNote;
                break;
            case SIXTEENTH: returnVal = sixteenthNote;
                break;
            case DOTTED_WHOLE: returnVal = 6 * quarterNote;
                break;
            case DOTTED_HALF: returnVal = 3 * quarterNote;
                break;
            case DOTTED_QUARTER: returnVal = quarterNote + eighthNote;
                break;
            case DOTTED_EIGHTH: returnVal = eighthNote + sixteenthNote;
                break;
            case DOTTED_SIXTEENTH: returnVal = sixteenthNote + (sixteenthNote /2);
                break;
            case QUARTER_TIE_SIXTEENTH: returnVal = quarterNote + sixteenthNote;
                break;
            case QUARTER_TIE_DOTTED_EIGHTH: returnVal = quarterNote + eighthNote + sixteenthNote;
                    break;
            case HALF_TIE_SIXTEENTH: returnVal = (2*quarterNote) + sixteenthNote;
                break;
            case QUARTER_TRIPLET: returnVal = ((quarterNote * 2) / 3);
                break;
            case EIGHTH_TRIPLET: returnVal = (quarterNote / 3);
                break;
            case SIXTEENTH_TRIPLET: returnVal = (eighthNote /3);
                break;
            default: returnVal = quarterNote;
                break;
        }

        return returnVal;
    }

    public void Update(float songPosition)
    {
        if(songPosition > triggerTime && currentNote != null)
        {
            currentBar = currentNote.measure;
            currentSyllable = dirToString(currentNote.arrowDirection);
            lastBeat += lastBeatLength;
            lastBeatLength = lengthToFloat(currentNote.noteLength);

            //System.out.println("SEQUENCER: SPAWNING ARROW");
            playLevel.spawnArrow(currentNote.arrowDirection, songPosition, (songPosition + lengthOfMeasure));

            currentNote = beatMap.dequeue();

            triggerTime = lastBeatLength + lastBeat;
        }
    }

    public void HardCodedUpdate(float songPosition)
    {
/*        if(songPosition > lastBeat + quarterNote)
        {
            System.out.println(currentBeat);
            currentBeat++;
            if(currentBeat > quarterNotesPerBar)
            {
                currentBeat = 1;
            }

            lastBeat += quarterNote;
        }*/

        if(currentBar == 1 && currentBeat == 0 && songPosition > offset)
        {
            //System.out.println("Song Position for Bar: " + currentBar + " Beat: " +
            //        currentBeat + ": " + songPosition);

            currentBeat = 1;
            currentSyllable = "One";
            lastBeat += offset;
            lastBeatLength = quarterNote;
        }

        if(currentBar == 1)
        {
            if(songPosition > lastBeat + lastBeatLength)
            {
                if(currentBeat == 1)
                {
                    //System.out.println("Song Position for Bar: " + currentBar + " Beat: " +
                    //        currentBeat + ": " + songPosition);
                    currentBeat = 2;
                    currentSyllable = "Two";
                    lastBeat += lastBeatLength;
                    lastBeatLength = sixteenthNote;

                }

                else if(currentBeat == 2)
                {
                    //System.out.println("Song Position for Bar: " + currentBar + " Beat: " +
                   //         currentBeat + ": " + songPosition);
                    currentBeat = 3;
                    currentSyllable = "e";
                    lastBeat += lastBeatLength;
                    lastBeatLength = (2*sixteenthNote);

                }

                else if(currentBeat == 3)
                {
                    currentBeat = 0;
                    currentSyllable = "Three";
                    lastBeat += lastBeatLength;
                    lastBeatLength = (quarterNote * 2) + sixteenthNote;

                    currentBar = 2;
                }
            }
        }

        if(currentBar == 2)
        {
            if(songPosition > lastBeat + lastBeatLength)
            {
                if(currentBeat == 0)
                {
                    currentBeat = 1;
                    currentSyllable = "One";

                    lastBeat += lastBeatLength;
                    lastBeatLength = quarterNote;
                }

                else if(currentBeat == 1)
                {
                    currentBeat = 2;
                    currentSyllable = "Two";

                    lastBeat += lastBeatLength;
                    lastBeatLength = sixteenthNote;
                }

                else if(currentBeat == 2)
                {
                    currentBeat = 3;
                    currentSyllable = "e";
                    lastBeat += lastBeatLength;
                    lastBeatLength = (2*sixteenthNote);
                }

                else if(currentBeat == 3)
                {

                    currentBeat = 4;
                    currentSyllable = "Three";
                    lastBeat += lastBeatLength;
                    lastBeatLength = (quarterNote) + sixteenthNote;
                }

                else if(currentBeat == 4)
                {

                    currentBeat = 5;
                    currentSyllable = "Four";
                    lastBeat += lastBeatLength;
                    lastBeatLength = eighthNote;
                }

                else if(currentBeat == 5)
                {
                    currentBeat = 0;
                    currentSyllable = "and";
                    lastBeat += lastBeatLength;
                    lastBeatLength = eighthNote;

                    currentBar = 3;
                }
            }
        }

        if(currentBar == 3)
        {

            if(songPosition > lastBeat + lastBeatLength)
            {
                currentBeat = 0;
                currentSyllable = "BAR 3";

                lastBeat += quarterNote;
            }

        }
    }
}
