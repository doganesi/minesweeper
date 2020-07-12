package com.wr.minesweeper;

public class DesktopMSGame
{
    private Board board;
    private BoardComponent boardComponent;


    public DesktopMSGame(Difficulty difficulty)
    {
        this.board = new Board(difficulty);
        this.boardComponent = new BoardComponent(board, 20);
    }

    public BoardComponent getBoardComponent()
    {
        return boardComponent;
    }

}
