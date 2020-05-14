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

import static com.example.towerdefense.MoveableObjectType.Rocket;

public class RocketBullet extends MoveableGameObject {

    private float size;
    private PointF location;
    private int heading = -1;
    private int speed;
    private int damage;
    private Point screenSize;
    private PointF target;
    private RectF hitBox;
    private Bitmap bitmap;
    private Bitmap rotatedBitmap;

    //Movement strategy
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;


    RocketBullet(Context context, float blockSize, Point screenSize) {

        super(Rocket);
        this.size = blockSize;  //Half the regular block size
        this.screenSize = screenSize;

        create(context);
    }

    private void create(Context context) {

        //Medium damage
        damage = 16;
        speed = 300;

        //Assign bitmap/scale design
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_bullet);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)size, (int)size, false);

        //Assign movement strategy
        movementStrategyFactory = new MovementStrategyFactory(screenSize);
        movementStrategy = movementStrategyFactory.getStrategy(Rocket);

        hitBox = new RectF();

    }

    public void spawn(PointF location) {
        this.location = location;

        updateHitBox();
    }

    public void markTarget(PointF target) {

        //Calculate the angle to enemy - similar to heading
        int angle = (int) Math.toDegrees(Math.atan2(target.y - location.y, target.x - location.x + 10));

        angle += 90;
        //Log.w("Debug:", "angle:" + angle);
        //Rotate turret to look at enemy
        Matrix matrix = new Matrix();
        matrix.setTranslate(-size/2, -size/2);
        matrix.postRotate(angle);
        matrix.postTranslate(bitmap.getWidth()/2, bitmap.getHeight()/2);
        rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

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

        canvas.drawBitmap(rotatedBitmap, location.x, location.y, paint);

    }

}
