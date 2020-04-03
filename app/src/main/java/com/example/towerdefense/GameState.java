package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;

public class GameState {

    //The basic stats variables
    private int wave = 0;
    private int level = 0;
    // Each enemy that passes the defenses "damages" for 1 hp
    private int baseHP = 20;

    //Money
    private int money = 10;

    //Tower being added
    private static FixedObjectType towerType = null;
    private static Point towerPlacement = null;

    //The actual game state variables
    private static volatile boolean paused = true;
    //private static volatile boolean waveWon = false;
    private static volatile boolean gameOver = true;
    private static volatile boolean threadRunning = false;
    private static volatile boolean addingTower = false;
    private static volatile boolean placedTower = false;


    public void startNewGame() {
        wave = 0;
        level = 0;
        baseHP = 20;
        money = 10;

        resume();
    }

    private void endGame() {
        gameOver = true;
        paused = true;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        gameOver = false;
    }

    public boolean getPaused() {
        return paused;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void loseHP() {
        baseHP--;
        if (baseHP == 0) {
            endGame();
        }
    }

    public FixedObjectType getTowerType() { return towerType; }

    public Point getTowerPlacement() { return towerPlacement; }

    public void addTower(FixedObjectType tower) {
        addingTower = true;
        towerType = tower;
    }

    public void addedTower() {
        addingTower = false;
        towerType = null;
        placedTower = false;
        towerPlacement = null;
    }

    public boolean getAddingTower() { return addingTower; }

    public boolean getPlacedTower() { return placedTower; }

    public void placeTower(Point placement) {
        placedTower = true;
        towerPlacement = placement;
    }

    public int getHP() {
        return baseHP;
    }

    public int getMoney() { return money; }

    public int getWave() {
        return wave;
    }

    public void nextWave() {
        wave++;
    }

    public void resetWaves() {
        wave = 0;
    }

    public int getLevel() {
        return level;
    }

    public void nextLevel() {
        level++;
    }

    public boolean getThreadRunning() {
        return threadRunning;
    }

    public void startThread() {
        threadRunning = true;
    }

    public void stopEverything() {
        paused = true;
        threadRunning = false;
    }



}
