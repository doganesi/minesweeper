package com.wr.minesweeper;

public class DesktopMSGame
{
    private Board board;
    private BoardComponent boardComponent;
    private ScoreComponent scoreComponent;


    public DesktopMSGame(Board board)
    {
        this.board = board;
        this.boardComponent = new BoardComponent(board, 20);
        this.scoreComponent = new ScoreComponent(board);
    }

    public BoardComponent getBoardComponent()
    {
        return boardComponent;
    }

    public ScoreComponent getScoreComponent()
    {
        return scoreComponent;
    }
}
