package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

import static com.example.towerdefense.MoveableObjectType.Soldier;

public class CyborgSoldier extends MoveableGameObject {

    private int health;
    private int speed;
    private int resistance;
    private int worth;
    private boolean dead;
    private int heading;
    private PointF target;
    private float size;                   //size of enemy on screen - a specific block/square size
    private Bitmap enemyImage;
    private PointF location = new PointF();   // current location on screen
    private RectF hitBox;

    private List<EnemyObserver> observers = new ArrayList();

    //Movement strategy
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;

    CyborgSoldier(Context context, float blockSize, Point screenSize) {
        super(Soldier);
        this.size = blockSize;

        create(context, screenSize);
    }

    private void create(Context context, Point screenSize) {
        health = 30;        //Medium hp
        speed = 50;         //Medium speed
        resistance = 0;     //should make part of a strategy based on previous wave's resistance/tower damage

        //Assign bitmap/scale design
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangle_enemy);
        this.enemyImage = Bitmap.createScaledBitmap(enemyImage, (int)size, (int)size, false);

        //Assign movement strategy
        movementStrategyFactory = new MovementStrategyFactory(screenSize);
        movementStrategy = movementStrategyFactory.getStrategy(Soldier);

        //hit box is a square box
        hitBox = new RectF();

        dead = false;
        worth = 5; //Worth medium amount

    }

    @Override
    public void spawn(PointF location) {

        // Spawn at location given
        this.location = location;

        //Log.w("drone", "x: " + location.x + " y: " + location.y);

        //heading = 90;

        updateHitBox();
    }

    @Override
    public void updateStartLocation(float x) {
        this.location.x = x;
    }

    @Override
    public void markTarget(PointF target) {
        this.target = target;

        getHeading();
    }

    private void getHeading() {
        heading = movementStrategy.getHeading(location, target);
    }

    public int getWorth() { return worth; }

    public boolean getDead() { return dead; }

    public boolean bulletCollision(MoveableGameObject bullet) {

        if (RectF.intersects(hitBox, bullet.getHitBox())) {
            health -= bullet.getBulletDamage();

            if (health <= 0)
                dead = true;

            return true;
        }

        return false;
    }

    @Override
    public void move(long fps) {

        if (location != null) {
            location = movementStrategy.move(location, heading, speed, fps);

            //Update hitbox of enemy
            updateHitBox();

            //Notify observers of movement
        }

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

    public MoveableObjectType getEnemyType() { return Soldier; }

    public void attach(EnemyObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (EnemyObserver observer : observers)
            observer.getHitBox();
    }

}