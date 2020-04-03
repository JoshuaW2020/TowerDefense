package com.example.towerdefense;

public class GameState {

    //The basic stats variables
    private int wave = 0;
    private int level = 0;
    // Each enemy that passes the defenses "damages" for 1 hp
    private int baseHP = 20;

    //The actual game state variables
    private static volatile boolean paused = true;
    //private static volatile boolean waveWon = false;
    private static volatile boolean gameOver = true;
    private static volatile boolean threadRunning = false;


    public void startNewGame() {
        wave = 0;
        level = 0;
        baseHP = 20;

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

    public int getHP() {
        return baseHP;
    }

    public int getWave() {
        return wave;
    }

    public void nextWave() {
        wave++;
    }

    public void resetWave() {
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
