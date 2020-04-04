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
    public void addTower(FixedObjectType towerType, Point location) {

        //Create the tower
        towers.add(fixedObjectFactory.build(towerType));

        //Spawn tower where placed
        towers.get(towers.size() - 1).spawn(screenSize, location);

    }

    public void addDrones(GameState gameState, Level level) {
        drones.clear();

        drones = level.getWaveDrones(gameState);

    }

    public void moveEnemies(long fps) {
        //Move all enemies

        //Move drones
        for (int i = 0; i < drones.size(); i++) {

            if (i == 0)
                drones.get(i).move(fps);
            else {
                //If the earlier enemy is no longer intersecting the next enemy then allow next enemy to move
                if (!RectF.intersects(drones.get(i-1).getHitBox(), drones.get(i).getHitBox())) {
                    drones.get(i).move(fps);
                }
            }
        }


    }

    public void checkEnemies(GameState gameState) {
        //check all enemies to see bullet collisions/deaths and if they made it to the base

        for (int i = 0; i < drones.size(); i++) {
            for (FixedGameObject tower : towers) {
                if (tower.getBullet() != null) {
                    if (drones.get(i).bulletCollision(tower.getBullet())) {
                        tower.deleteBullet();
                    }
                }
            }

            if (drones.get(i).getDead()) {
                gameState.addMoney(drones.get(i).getWorth());
                drones.remove(i);
            }
        }

        for (int i = 0; i < drones.size(); i++) {
            if (drones.get(i).getHitBox().left >= screenSize.x) {
                gameState.loseHP();

                // and delete the enemy that made it
                drones.remove(i);
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

        for (FixedGameObject tower : towers) {
            tower.moveBullets(fps, drones.get(0).getHitBox());
        }

    }

    public void drawObjects(Canvas canvas, Paint paint) {

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


