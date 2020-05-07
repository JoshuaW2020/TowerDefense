package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import static com.example.towerdefense.MoveableObjectType.Drone;
import static com.example.towerdefense.MoveableObjectType.Soldier;
import static com.example.towerdefense.MoveableObjectType.Behemoth;

public class Level {
    //Context for object creation
    Context context;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 100;        //Make 100 blocks wide - arbitrary
    private float NumBlocksHigh;
    Point screenSize;
    float blockSize;

    private MoveableObjectFactory movingObjectsFactory;

    private ArrayList<MoveableGameObject> enemies;

    //The map, ie. path for the enemies to take
    private Map map;

    Level(Context context, Point screenSize) {
        this.context = context;

        // Work out how many pixels each block is
        float blockSize = screenSize.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        NumBlocksHigh = screenSize.y / blockSize;
        this.screenSize = screenSize;
        this.blockSize = blockSize;

        //Initialize the Array lists of objects
        enemies = new ArrayList<>();

        //Create the Moveable object factory to create all the moveable objects
        movingObjectsFactory = new MoveableObjectFactory(context, blockSize, screenSize);

        map = new Map(context, screenSize, blockSize);

    }

    public ArrayList<MoveableGameObject> getWaveEnemies(GameState gameState) {
        //create and return a list of drones for the apprpriate wave/level
        enemies.clear();

        //update start location float
        float x;

        //Create drones = 4 * level * wave
        for (int i = 0; i < 4 * gameState.getWave() * gameState.getLevel(); i++) {
            enemies.add(movingObjectsFactory.build(Drone));

            if (enemies.get(i).getEnemyType() == Drone) {
                enemies.get(i).spawn(map.getSpawnPoint());

                x = map.getSpawnPoint().x;
                enemies.get(i).updateStartLocation(x - 20);

                enemies.get(i).markTarget(map.getObjectivePoint(0));
            }

            Log.w("Drone", "created drone: " + i);
        }

        //Create Soldiers = 1 * (level - 1) * wave
        int enemiesSize = enemies.size();
        for (int i = enemiesSize; i < (enemiesSize + 1 * gameState.getWave() * (gameState.getLevel() - 1)); i++) {
            enemies.add(movingObjectsFactory.build(MoveableObjectType.Soldier));

            if (enemies.get(i).getEnemyType() == Soldier) {
                enemies.get(i).spawn(map.getSpawnPoint());

                x = map.getSpawnPoint().x;
                enemies.get(i).updateStartLocation(x - 20);

                enemies.get(i).markTarget(map.getObjectivePoint(0));
            }

            Log.w("Drone", "created soldier: " + i);
        }

        return enemies;
    }

    public ArrayList<Rect> getPath() { return map.getPath(); }

    public int getNumOfObjectives() { return map.getNumOfObjectives(); }

    public Rect getObjective(int i) { return map.getObjective(i); }

    public PointF getObjectivePoint(int i) { return map.getObjectivePoint(i); }

}
