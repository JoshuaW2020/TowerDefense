package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

import java.util.List;

//Will be holding the game world objects
public class GameWorld {

    //Context for object creation
    Context context;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 100;        //Make 100 blocks wide - arbitrary
    private int NumBlocksHigh;
    Point screenSize;
    int blockSize;

    private MoveableObjectFactory movingObjectsFactory;

    private List<MoveableGameObject> drones;

    private List<Tower> towers;

    GameWorld(Context context, Point screenSize) {
        this.context = context;

        // Work out how many pixels each block is
        int blockSize = screenSize.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        NumBlocksHigh = screenSize.y / blockSize;
        this.screenSize = screenSize;
        this.blockSize = blockSize;

        //Create the Moveable object factory to create all the moveable objects
        movingObjectsFactory = new MoveableObjectFactory(context, blockSize);

    }

    public void addDrone () {
        //Add a drone to the gameworld list
        int size = drones.size();
        drones.add(movingObjectsFactory.build(MoveableObjectType.Drone));


    }
}


