package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;

public interface IBoard 
{
    public enum GameState {RUNNING, OVER_WIN, OVER_LOSE}
    public enum TileOperation {FLAG_TOGGLE, OPEN}

    int getXTiles();

    int getYTiles();

    void addActionListener(IBoardActionListener listener);

    void performTileOperation(TileOperation tileOperation, int tileX, int tileY);

    Tile getTile(int x, int y);

    GameState getGameState();

    int getElapsedSeconds();

    int getNumRemainingFlags();

}
