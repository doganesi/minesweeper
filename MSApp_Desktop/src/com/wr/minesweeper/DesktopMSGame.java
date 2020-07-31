package com.wr.minesweeper;

public class DesktopMSGame
{
    private Board board;
    private BoardComponent boardComponent;


    public DesktopMSGame(Board board)
    {
        this.board = board;
        this.boardComponent = new BoardComponent(board, 20);
    }

    public BoardComponent getBoardComponent()
    {
        return boardComponent;
    }

}
