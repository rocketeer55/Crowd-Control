package com.csc309.crowdcontrol;

import android.view.GestureDetector;

import android.view.MotionEvent;

public class CustomGestureDetector implements GestureDetector.OnGestureListener {

    public boolean left = false;
    public boolean right = false;
    public boolean up = false;
    public boolean down = false;

    public int screenWidth;
    public int screenHeight;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // Not implemented
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // Not implemented
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getY() > 9 * screenHeight / 12) {
            // Swipe was in the controller - area
            return (checkLeftRight(e1, e2) || checkUpDown(e1, e2));
        }

        return false;
    }

    private boolean checkLeftRight(MotionEvent e1, MotionEvent e2) {
        if (e1.getX() < screenWidth / 2.f) {
            // Left half of the screen - check left and right swipes
            if (e1.getX() - e2.getX() > 50) {
                left = true;
                return true;
            }
            if (e1.getX() - e2.getX() < -50) {
                right = true;
                return true;
            }
        }

        return false;
    }

    private boolean checkUpDown(MotionEvent e1, MotionEvent e2) {
        if (e1.getX() > screenWidth / 2.f) {
            // right half of the screen - check up and down swipes
            if (e1.getY() - e2.getY() > 50) {
                up = true;
                return true;
            }
            if (e1.getY() - e2.getY() < -50) {
                down = true;
                return true;
            }
        }

        return false;
    }
}
