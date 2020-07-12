package com.wr.minesweeper;

public class DesktopMSGame
{

    private Board board;

    public DesktopMSGame(Board board)
    {
        this.board = board;
    }

    public void startGameDesktop()
    {
        while(true)
        {
            DesktopUtil.drawBoard(board);
        }
    }
}
