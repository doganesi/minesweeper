package com.wr.minesweeper;

import com.wr.minesweeper.desktop.BoardComponent;

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
}
