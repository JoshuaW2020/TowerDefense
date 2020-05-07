package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//Will be holding the game world objects
public class GameWorld {

    //Context for object creation
    Context context;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 100;        //Make 100 blocks wide - arbitrary
    private float NumBlocksHigh;
    Point screenSize;
    float blockSize;

    private MoveableObjectFactory movingObjectsFactory;
    private FixedGameObjectFactory fixedObjectFactory;

    private ArrayList<MoveableGameObject> drones;
    private ArrayList<MoveableGameObject> soldiers;
    private ArrayList<MoveableGameObject> behemoths;

    //enemy path
    private ArrayList<Rect> paths;

    private ArrayList<FixedGameObject> towers;

    GameWorld(Context context, Point screenSize) {
        this.context = context;

        // Work out how many pixels each block is
        float blockSize = screenSize.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        NumBlocksHigh = screenSize.y / blockSize;
        this.screenSize = screenSize;
        this.blockSize = blockSize;

        //Initialize the Array lists of objects
        drones = new ArrayList();
        soldiers = new ArrayList();
        behemoths = new ArrayList();

        towers = new ArrayList();


        //Create the Moveable object factory to create all the moveable objects
        movingObjectsFactory = new MoveableObjectFactory(context, blockSize, screenSize);

        fixedObjectFactory = new FixedGameObjectFactory(context, blockSize, screenSize);

    }

    //Created and place tower into game world
    public void addTower(FixedObjectType towerType, Point location, GameState gameState) {

        //Create the tower
        towers.add(fixedObjectFactory.build(towerType));

        //Spawn tower where placed
        towers.get(towers.size() - 1).spawn(screenSize, location);

        //If tower intersects with path, delete tower
        for (Rect path : paths) {
            if (towers.get(towers.size() - 1).getHitBox().intersect(path)) {

                //Refund for tower
                gameState.refundTower(towers.get(towers.size() - 1).getTowerType());
                //Now remove tower
                towers.remove(towers.size() - 1);
            }
        }
    }

    public void addDrones(GameState gameState, Level level) {
        drones.clear();

        drones = level.getWaveDrones(gameState);

        paths = level.getPath();

    }

    public void moveEnemies(long fps) {
        //Move all enemies

        //Move drones
        for (int i = 0; i < drones.size(); i++) {

            if (i == 0)
                drones.get(i).move(fps);
            else if (!RectF.intersects(drones.get(i - 1).getHitBox(), drones.get(i).getHitBox())) {
                //If the earlier enemy is no longer intersecting the next enemy then allow next enemy to move
                drones.get(i).move(fps);

            }
        }


    }

    public void checkEnemies(GameState gameState, Level level) {
        //check all enemies to see bullet collisions/deaths and if they made it to the objective/base

        for (int i = 0; i < drones.size(); i++) {
            for (FixedGameObject tower : towers) {
                if (tower.getBullet() != null) {
                    if (drones.get(i).bulletCollision(tower.getBullet())) {
                        tower.deleteBullet();
                    }
                }
            }

            //checks if enemy is dead
            if (drones.get(i).getDead()) {
                gameState.addMoney(drones.get(i).getWorth());
                drones.remove(i);
            }
        }

        for (int i = 0; i < drones.size(); i++) {

            //check if enemy made it to first objective
            if (level.getObjective(0).contains( (int) drones.get(i).getHitBox().centerX(), (int) drones.get(i).getHitBox().centerY())) {
                //check how many objectives there are - if > 1 -> set next target
                if (level.getNumOfObjectives() > 1) {
                    drones.get(i).markTarget(level.getObjectivePoint(i));
                }
                //Checks if enemy made it to base ie.right side of map
                else if (drones.get(i).getHitBox().left >= screenSize.x) {
                    gameState.loseHP();

                    // and delete the enemy that made it
                    drones.remove(i);
                }
            }

        }

    }

    public int getEnemies() {
        return drones.size() + soldiers.size() + behemoths.size();
    }

    public void towersShoot() {

        if (drones.size() >= 1) {
            for (FixedGameObject tower : towers) {
                tower.shotCheck(drones.get(0).getHitBox());
            }
        }
    }

    public void moveBullets(long fps) {

        if (!drones.isEmpty()) {
            for (FixedGameObject tower : towers) {
                tower.moveBullets(fps, drones.get(0).getHitBox());
            }
        }

    }

    public void drawObjects(Canvas canvas, Paint paint) {

        paint.setColor(Color.argb(100, 0, 0, 0));

        //Draw the path
        if (paths != null) {
            for (Rect path : paths) {
                canvas.drawRect(path, paint);
            }
        }

        paint.setColor(Color.argb(255, 160, 160, 160));

        //Draw the different enemies
        for (MoveableGameObject drone : drones) {
            drone.draw(canvas, paint);
        }
        for (MoveableGameObject soldier : soldiers) {
            soldier.draw(canvas, paint);
        }
        for (MoveableGameObject behemoth : behemoths) {
            behemoth.draw(canvas, paint);
        }

        //Now draw the towers / bullets
        for (FixedGameObject tower : towers) {
            tower.draw(canvas, paint);
        }

    }

}


