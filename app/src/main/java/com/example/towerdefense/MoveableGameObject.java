package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

// Acts as parent class (moveable game object ie. enemy/bullet) of the different enemy types (all enemies will need to move, nothing else will)
public abstract class MoveableGameObject {

    private int size;
    private Point location;
    private int heading;
    private int speed;
    private MoveableObjectType type;

    public MoveableGameObject(MoveableObjectType type) {
        this.type = type;
    }

    private void create() {
        //Create the specific type of cyborg here
    }

    //For getting location of object, can move after creation
    public Point getLocation() { return location; }

    //For moving the objects
    abstract void move();

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);
}
