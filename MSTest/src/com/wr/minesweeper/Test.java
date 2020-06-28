package com.wr.minesweeper;

import com.wr.util.RandUtil;

public class Test
{
    public static void main(String[] args)
    {
        test_3();
    }

    private static void test_3()
    {
        MSMainApp_Terminal mainApp = new MSMainApp_Terminal();
        mainApp.start();
    }

    private static void test_2()
    {
        // Generate 20 boards (use loop)
        // we only want MEDIUM, HARD or EXTREME (randomly)
        // print difficulty level on one line
        // follow by printing the board
        // add empty line

        Board[] boardArray = new Board[6];
        for (int i = 0; i <= 5; i++)
        {
            int randomIndex = RandUtil.nextInt(3);
            Difficulty difficulty = Difficulty.DIFFICULTY_LEVELS[randomIndex];
            System.out.println(difficulty.getName() + " : " + randomIndex);
            Board board1 = new Board(difficulty);
            board1.setGameState(Board.GameState.OVER);
//            TerminalUtil.printBoard(board1);
//            DesktopUtil.drawBoard(board1);
//            System.out.println(' ');
            boardArray[i] = board1;
        }

        DesktopUtil.drawBoards(boardArray);
    }

    private static void test_1()
    {
//        Random rand = new Random(System.currentTimeMillis());
//        for (int i = 0; i < 100; i++)
//        {
//            int numMines = rand.nextInt(50);
//            Board board = new Board(10, 5, numMines);
//            System.out.println(board.getNumMines() + " - " + board.getLevel());
//        }

//        Board board1 = new Board(Difficulty.IMPOSSIBLE);
//        Board board24 = new Board(20, 20, 4);
//        board1.setName("FirstBoard");
//        board1.getTile(2,3).setTileState(Tile.State.FLAGGED);
//        board1.getTile(2,4).setTileState(Tile.State.FLAGGED);
//        board1.setGameState(Board.GameState.OVER);
//        System.out.println(board1.getDifficulty().getName());
//        DesktopUtil.drawBoard(board1);
//        TerminalUtil.printBoard(board1);
//        Board board2 = new Board(10, 5, 12);
//        board2.setName("SecondBoarda[dflgp[flgsldf][pals;fg;");
//        Board board3 = new Board(10, 5, 12);
//        board1.setName("Ff");
//        Board board4 = new Board(10, 5, 12);
//        int mineNumber = numMines;
//            System.out.println("Number of mines is " + mineNumber);
    }



}
