package com.csc309.crowdcontrol;

import android.graphics.Canvas;

public interface GameObject {

    void draw(Canvas canvas);

    void update();

    boolean shouldDelete();
}
