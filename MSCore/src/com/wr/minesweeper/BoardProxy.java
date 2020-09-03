package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;

public class BoardProxy implements IBoard
{
    public static final int PORT_NUMBER = 50006;

    protected Board actualBoard;

    @Override
    public int getXTiles()
    {
        return actualBoard.getXTiles();
    }

    @Override
    public int getYTiles()
    {
        return actualBoard.getYTiles();
    }

    @Override
    public void addActionListener(IBoardActionListener listener)
    {

    }

    @Override
    public void performTileOperation(TileOperation tileOperation, int tileX, int tileY)
    {

    }

    @Override
    public Tile getTile(int x, int y)
    {
        return actualBoard.getTile(x, y);
    }

    @Override
    public GameState getGameState()
    {
        return actualBoard.getGameState();
    }

    @Override
    public int getElapsedSeconds()
    {
        return actualBoard.getElapsedSeconds();
    }

    @Override
    public int getNumRemainingFlags()
    {
        return actualBoard.getNumRemainingFlags();
    }
}
