package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

public class Level {
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

    Level(Context context, Point screenSize) {
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

        //Create the Moveable object factory to create all the moveable objects
        movingObjectsFactory = new MoveableObjectFactory(context, blockSize, screenSize);

    }

    public ArrayList<MoveableGameObject> getWaveDrones(GameState gameState) {
        //create and return a list of drones for the apprpriate wave/level
        drones.clear();
        //Create drones = 5 * level wave
        for (int i = 0; i < 5 * gameState.getWave(); i++) {

            drones.add(movingObjectsFactory.build(MoveableObjectType.Drone));

            drones.get(i).spawn(new PointF(-20, 500));
        }

        return drones;
    }
}
