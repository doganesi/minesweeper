package com.wr.minesweeper;

import javax.swing.*;
import java.awt.*;

public class DesktopUtil
{
    public static void drawBoard(Board board)
    {
        JFrame mainFrame = new JFrame("Minesweeper");
//        mainFrame.setSize(new Dimension(550, 250));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BoardComponent boardComponent = new BoardComponent(board, 50);
        mainFrame.getContentPane().add(boardComponent);
        mainFrame.setVisible(true);
        mainFrame.pack();

    }

    public static void drawBoards(Board[] boards)
    {
        JFrame mainFrame = new JFrame("Minesweeper");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ScrollPane sp = new ScrollPane();
        mainFrame.getContentPane().add(sp);
        JPanel boardContainer = new JPanel();

        for (int i = 0; i < boards.length; i++)
        {
            Board board = boards[i];
            BoardComponent boardComponent = new BoardComponent(board, 50);
            boardContainer.add(boardComponent);
        }
        sp.add(boardContainer);
        mainFrame.setSize(new Dimension(600, 600));
        mainFrame.setVisible(true);
    }
}
