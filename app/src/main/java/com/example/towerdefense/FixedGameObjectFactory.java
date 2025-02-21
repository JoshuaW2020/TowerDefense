package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;

import static com.example.towerdefense.FixedObjectType.Turret;

public class FixedGameObjectFactory {

    private Context context;
    private float blockSize;
    private Point screenSize;

    FixedGameObjectFactory(Context context, float blockSize, Point screenSize) {
        this.context = context;
        this.blockSize = blockSize;
        this.screenSize = screenSize;
    }

    public FixedGameObject build(FixedObjectType type) {
        switch (type) {
            case Turret:
                return new PlasmaTurret(context, blockSize, screenSize);

            case Cannon:
                return new LaserCannon(context, blockSize, screenSize);

            case Rockets:
                return new AntimatterRockets(context,blockSize,screenSize);
        }

        return null;
    }

}
