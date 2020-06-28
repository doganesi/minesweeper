package com.wr.minesweeper;

public class Tile
{
    public enum State{FLAGGED, OPEN, CLOSED}

    private int tileLocationX;
    private int tileLocationY;
    private boolean hasMine;
    private State tileState = State.CLOSED;
    private Board board;

    public Tile(Board board, int tileLocationX, int tileLocationY, boolean hasMine)
    {
        this.board = board;
        this.tileLocationX = tileLocationX;
        this.tileLocationY = tileLocationY;
        this.hasMine = hasMine;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
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
