package com.wr.util;

import com.wr.minesweeper.Board;

public interface IBoardActionListener
{
    void refresh(Board.TileOperation tileOperation,  int x, int y);
}
