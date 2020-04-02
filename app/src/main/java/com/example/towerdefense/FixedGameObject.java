package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public abstract class FixedGameObject {

    private int size;
    private PointF location;




    //For getting location of object - can't update location after creation
    abstract PointF getLocation();

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);


}
