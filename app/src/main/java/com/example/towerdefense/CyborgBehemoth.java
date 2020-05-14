package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import static com.example.towerdefense.MoveableObjectType.Behemoth;

public class CyborgBehemoth extends MoveableGameObject {

    private int health;
    private int speed;
    private int resistance;
    private int worth;
    private boolean dead;
    private int heading;
    private PointF target;
    private float size;                   //size of enemy on screen - a specific block/square size
    private Bitmap enemyImage;
    private Bitmap rotatedEnemy;
    private PointF location = new PointF();   // current location on screen
    private RectF hitBox;

    private List<EnemyObserver> observers = new ArrayList();

    //Movement strategy
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;

    CyborgBehemoth(Context context, float blockSize, Point screenSize) {
        super(Behemoth);
        this.size = blockSize * 3; //big target

        create(context, screenSize);
    }

    private void create(Context context, Point screenSize) {
        health = 200;        //Lots of HP
        speed = 50;         //SLOW
        resistance = 0;     //should make part of a strategy based on previous wave's resistance/tower damage

        //Assign bitmap/scale design
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cyborg_behemoth_1);
        this.enemyImage = Bitmap.createScaledBitmap(enemyImage, (int)size, (int)size, false);

        //Assign movement strategy
        movementStrategyFactory = new MovementStrategyFactory(screenSize);
        movementStrategy = movementStrategyFactory.getStrategy(Behemoth);

        //hit box is a square box
        hitBox = new RectF();

        dead = false;
        worth = 10; //Worth medium amount

    }

    @Override
    public void spawn(PointF location) {

        // Spawn at location given
        this.location = location;

        updateHitBox();
    }

    @Override
    public void updateStartLocation(float x) {
        this.location.x = x;
    }

    @Override
    public void markTarget(PointF target) {

        //Calculate the angle to enemy - similar to heading
        int angle = (int) Math.toDegrees(Math.atan2(target.y - location.y, target.x - location.x + 10));

        angle += 90;
        //Log.w("Debug:", "angle:" + angle);
        //Rotate turret to look at enemy
        Matrix matrix = new Matrix();
        matrix.setTranslate(-size/2, -size/2);
        matrix.postRotate(angle);
        matrix.postTranslate(enemyImage.getWidth()/2, enemyImage.getHeight()/2);
        rotatedEnemy = Bitmap.createBitmap(enemyImage, 0, 0, enemyImage.getWidth(), enemyImage.getHeight(), matrix, true);

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

        canvas.drawBitmap(rotatedEnemy, location.x, location.y, paint);

    }

    private void updateHitBox() {
        //Update left, top, right, and bottom
        hitBox.set(location.x, location.y, location.x + size, location.y + size);
    }

    public MoveableObjectType getEnemyType() { return Behemoth; }

    public void attach(EnemyObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (EnemyObserver observer : observers)
            observer.getHitBox();
    }
}
