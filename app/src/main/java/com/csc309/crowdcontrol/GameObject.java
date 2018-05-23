package com.csc309.crowdcontrol;

import android.graphics.Canvas;

abstract class GameObject {

    public String type;

    public abstract void draw(Canvas canvas);

    public abstract void update(float songPos);

    public abstract boolean shouldDelete();
}
