package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);


}
