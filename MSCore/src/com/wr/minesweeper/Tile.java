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

    public int getTileLocationX()
    {
        return tileLocationX;
    }

    public void setTileLocationX(int tileLocationX)
    {
        this.tileLocationX = tileLocationX;
    }

    public int getTileLocationY()
    {
        return tileLocationY;
    }

    public void setTileLocationY(int tileLocationY)
    {
        this.tileLocationY = tileLocationY;
    }

    public boolean isHasMine()
    {
        return hasMine;
    }

    public void setHasMine(boolean hasMine)
    {
        this.hasMine = hasMine;
    }

    public State getTileState()
    {
        return tileState;
    }

    public void setTileState(State tileState)
    {
        this.tileState = tileState;
    }
}
