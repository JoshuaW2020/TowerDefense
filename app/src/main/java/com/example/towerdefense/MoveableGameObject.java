package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

// Acts as parent class (moveable game object ie. enemy/bullet) of the different enemy types (all enemies will need to move, nothing else will)
public abstract class MoveableGameObject {

    private MoveableObjectType type;
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;

    public MoveableGameObject(MoveableObjectType type) {
        this.type = type;
    }

    private void create() {
        //Create the specific type of cyborg here
    }

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);
}
