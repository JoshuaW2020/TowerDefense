package com.example.towerdefense;

import android.graphics.RectF;

public abstract class EnemyObserver {

    private MoveableGameObject enemyObject;
    public abstract RectF getHitBox();

}
