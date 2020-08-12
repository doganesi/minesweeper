package com.wr.minesweeper;

import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable
{
    public enum State {FLAGGED, OPEN, CLOSED}
    public static final Color[] COLORS = new Color[] {Color.WHITE, Color.BLUE, new Color(24, 100, 40), Color.RED, Color.MAGENTA, new Color(128, 6, 6), Color.CYAN, Color.BLACK, Color.GRAY};

    private int tileLocationX;
    private int tileLocationY;
    private int numMinesAround;
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

    boolean toggleFlag()
    {
        if(tileState == State.FLAGGED)
        {
            tileState = State.CLOSED;
            return true;
        }
        else if(tileState == State.CLOSED)
        {
            tileState = State.FLAGGED;
            return true;
        }
        return false;
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

    public int getNumMinesAround()
    {
        return numMinesAround;
    }

    public char getNumMinesAroundChar()
    {
        if (numMinesAround == 0)
        {
            return ' ';
        }
        String numberMinesAroundStr = String.valueOf(getNumMinesAround());
        return numberMinesAroundStr.charAt(0);
    }

    public Color getNumMinesAroundColor()
    {
        return COLORS[numMinesAround];
    }

    public void setNumMinesAround(int numMinesAround)
    {
        this.numMinesAround = numMinesAround;
    }
}
