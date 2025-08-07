package me.nv.managers;

public class ExampleGameManager {
    public boolean gameStarted = false;

    public void startGame() {
        this.gameStarted = true;
    }

    public void endGame() {
        this.gameStarted = false;
    }
}
