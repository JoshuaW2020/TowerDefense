package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import static com.example.towerdefense.MoveableObjectType.Laser;

public class LaserBullet extends MoveableGameObject {

    private float size;
    private PointF location;
    private int heading = -1;
    private int speed;
    private int damage;
    private Point screenSize;
    private PointF target;
    private RectF hitBox;
    private Bitmap bitmap;

    //Movement strategy
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;


    LaserBullet(Context context, float blockSize, Point screenSize) {

        super(Laser);
        this.size = blockSize / 2;  //Half the regular block size
        this.screenSize = screenSize;

        create(context);
    }

    private void create(Context context) {

        //Massive damage
        damage = 40;
        speed = 1000;

        //Assign bitmap/scale design
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_bullet);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)size, (int)size, false);

        //Assign movement strategy
        movementStrategyFactory = new MovementStrategyFactory(screenSize);
        movementStrategy = movementStrategyFactory.getStrategy(Laser);

        hitBox = new RectF();

    }

    public void spawn(PointF location) {
        this.location = location;

        updateHitBox();
    }

    public void markTarget(PointF target) {
        this.target = target;

        getHeading();
    }

    private void getHeading() {
        heading = movementStrategy.getHeading(location, target);
    }

    //For moving the moveable object
    public void move(long fps) {

        //Log.w("bullet: ", "heading: " + heading);

        location = movementStrategy.move(location, heading, speed, fps);

        updateHitBox();

    }

    private void updateHitBox() {

        //Update left, top, right, and bottom
        hitBox.set(location.x, location.y, location.x + size, location.y + size);
    }

    public int getBulletDamage() {
        return damage;
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

        canvas.drawBitmap(bitmap, location.x, location.y, paint);

    }
}
