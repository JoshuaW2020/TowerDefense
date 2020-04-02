package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

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

    }

    public void addDrone() {
        //Add a drone to the gameworld list
        drones.add(movingObjectsFactory.build(MoveableObjectType.Drone));
    }

    public void addTower() {

    }

    public void moveEnemies() {

    }

    public void towersShoot() {

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

        //Now draw the towers
        for (FixedGameObject tower : towers) {
            ;
        }
    }

}


