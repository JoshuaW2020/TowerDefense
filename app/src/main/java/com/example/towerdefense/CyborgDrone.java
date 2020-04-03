package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.towerdefense.MoveableObjectType.Drone;

public class CyborgDrone extends MoveableGameObject {

    private int health;
    private int speed;
    private int resistance;
    private boolean dead;
    private int heading;
    private float size;                   //size of enemy on screen - a specific block/square size
    private Bitmap enemyImage;
    private PointF location = new PointF();   // current location on screen
    private MoveableObjectType objectType;
    private RectF hitBox;

    private List<EnemyObserver> observers = new ArrayList();

    //Movement strategy
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;

    CyborgDrone(Context context, float blockSize, Point screenSize) {
        super(Drone);
        this.size = blockSize;

        create(context, screenSize);
    }

    private void create(Context context, Point screenSize) {
        health = 10;
        speed = 150;         //will make speed a part of the movement strategy later
        resistance = 0;     //should also make part of a strategy based on previous wave's resistance/tower damage

        //Assign bitmap/scale design
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangle_enemy);
        this.enemyImage = Bitmap.createScaledBitmap(enemyImage, (int)size, (int)size, false);

        //Type of object
        objectType = Drone;

        //Assign movement strategy
        movementStrategyFactory = new MovementStrategyFactory(screenSize);
        movementStrategy = movementStrategyFactory.getStrategy(objectType);

        //hit box is a square box
        hitBox = new RectF();

        dead = false;

    }

    public void spawn(Point screen) {

        // Spawn on left side of screen outside of view for now
        location = new PointF(-20, 500);

        heading = 90;

        updateHitBox();
    }

    public boolean bulletCollision(RectF bulletHitBox, int bulletDamage) {

        if(RectF.intersects(hitBox, bulletHitBox)) {
            health -= bulletDamage;

            if (health <= 0)
                dead = true;

            return true;
        }

        return false;
    }

    @Override
    public void move(long fps) {

        location = movementStrategy.move(location, heading, speed, fps);

        //Update hitbox of enemy
        updateHitBox();

        //Notify observers of movement


    }

    @Override
    public PointF getLocation() {
        return location;
    }

    @Override
    public RectF getHitBox() {
        return hitBox;
    }

    public void draw(Canvas canvas, Paint paint) {

        canvas.drawBitmap(enemyImage, location.x, location.y, paint);

    }

    private void updateHitBox() {

        //Update left, top, right, and bottom
        hitBox.set(location.x, location.y, location.x + size, location.y + size);


    }

    public void attach(EnemyObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (EnemyObserver observer : observers)
            observer.getHitBox();
    }

}
