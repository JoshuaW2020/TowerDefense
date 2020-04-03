package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

public abstract class FixedGameObject {

    private float size;
    private Point location;
    private Rect hitBox;
    private int shotSpeed;
    private int reloading;


    abstract void shotCheck();

    private void shoot() {
        //shoot a bullet
    }

    //For getting location of object - can't update location after creation
    abstract Point getLocation();

    //For retrieving hit box of tower
    abstract Rect getHitBox();

    abstract boolean spawn(Point screen, Point placement);

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);


}
