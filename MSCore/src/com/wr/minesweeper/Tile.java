package com.wr.minesweeper;

public class Tile
{
    enum State{FLAGGED, REVEALED, CLOSED}

    private int tileLocationX;
    private int tileLocationY;
    private boolean hasMine;
    private State tileState = State.CLOSED;

    public Tile(int tileLocationX, int tileLocationY, boolean hasMine)
    {
        this.tileLocationX = tileLocationX;
        this.tileLocationY = tileLocationY;
        this.hasMine = hasMine;
    }
}
