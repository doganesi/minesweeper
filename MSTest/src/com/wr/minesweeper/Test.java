package com.wr.minesweeper;

import java.util.Random;

public class Test
{
    public static void main(String[] args)
    {
        Random rand = new Random(System.currentTimeMillis());
//        for (int i = 0; i < 100; i++)
//        {
//            int numMines = rand.nextInt(50);
//            Board board = new Board(10, 5, numMines);
//            System.out.println(board.getNumMines() + " - " + board.getLevel());
//        }
        Board board1 = new Board(10, 5, 12);
        board1.setName("FirstBoard");
        Board board2 = new Board(10, 5, 12);
        board2.setName("SecondBoarda[dflgp[flgsldf][pgls;fg;");
        Board board3 = new Board(10, 5, 12);
        board1.setName("Ff");
        Board board4 = new Board(10, 5, 12);
    }

}
