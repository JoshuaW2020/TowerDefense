package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

// Acts as parent class (moveable game object ie. enemy/bullet) of the different enemy types (all enemies will need to move, nothing else will)
public abstract class MoveableGameObject {

    private MoveableObjectType type;
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;

    private PointF location;
    private RectF hitBox;

    private List<EnemyObserver> observers = new ArrayList();

    public MoveableGameObject(MoveableObjectType type) {
        this.type = type;
    }

    private void create() {
        //Create the specific type of cyborg here
    }

    public void markTarget(PointF target) {
        //mark the target
    }

    public int getWorth() { return 0; }

    public boolean getDead() {return false;}

    public PointF getLocation() {
        return location;
    }

    public RectF getHitBox() {
        return hitBox;
    }

    public boolean bulletCollision(MoveableGameObject bullet) {return false;}

    public int getBulletDamage() { return 0; }

    //Just for beginning of project
    abstract void spawn(PointF location);

    abstract void move(long fps);

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);

    public void attach(EnemyObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (EnemyObserver observer : observers)
            observer.getHitBox();
    }


}
