package com.wr.minesweeper;

import java.util.Random;

public class Test
{
    public static void main(String[] args)
    {
//        Random rand = new Random(System.currentTimeMillis());
//        for (int i = 0; i < 100; i++)
//        {
//            int numMines = rand.nextInt(50);
//            Board board = new Board(10, 5, numMines);
//            System.out.println(board.getNumMines() + " - " + board.getLevel());
//        }

        Board board1 = new Board(8, 7, 3);
        board1.setName("FirstBoard");
        TerminalUtil.printBoard(board1);
//        Board board2 = new Board(10, 5, 12);
//        board2.setName("SecondBoarda[dflgp[flgsldf][pals;fg;");
//        Board board3 = new Board(10, 5, 12);
//        board1.setName("Ff");
//        Board board4 = new Board(10, 5, 12);
//        int mineNumber = numMines;
//            System.out.println("Number of mines is " + mineNumber);

    }



}
