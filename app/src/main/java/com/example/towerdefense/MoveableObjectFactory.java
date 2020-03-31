package com.example.towerdefense;

import android.content.Context;

public class MoveableObjectFactory {

    private Context context;
    private int blockSize;

    MoveableObjectFactory(Context context, int blockSize) {
        this.context = context;
        this.blockSize = blockSize;
    }

    public MoveableGameObject build(MoveableObjectType type) {
        MoveableGameObject object= null;
        switch (type) {
            case Drone:
                object = new CyborgDrone(context, blockSize);
                break;

            case Soldier:
                ;
                break;

            case Behemoth:
                ;
                break;

            case Bullet:
                ;
                break;

            default:
                //Exception: no compatible type found
                break;
        }
        return object;
    }
}
