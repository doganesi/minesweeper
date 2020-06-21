package com.wr.minesweeper.desktop;

import com.wr.minesweeper.Board;

import javax.swing.*;
import java.awt.*;

public class BoardComponent extends JComponent
{
    private int tileSizePX;
    private int widthPX;
    private int heightPX;
    private Board board;


    public BoardComponent(Board board, int tileSizePX)
    {
        this.board = board;
        this.tileSizePX = tileSizePX;
        widthPX = board.getXTiles() * tileSizePX;
        heightPX = board.getYTiles() * tileSizePX;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(widthPX + 50, heightPX + 50);
    }

    @Override
    public Dimension getSize()
    {
        return new Dimension(widthPX + 50, heightPX + 50);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);


        g.setColor(Color.RED);

        for (int y = 0; y < board.getYTiles(); y++)
        {
            for (int x = 0; x < board.getXTiles(); x++)
            {
                int ulcX = x * tileSizePX + 25;
                int ulcY = y * tileSizePX + 25;
                g.drawRect(ulcX, ulcY , tileSizePX, tileSizePX);

            }
        }

        g.setColor(Color.BLACK);


        g.drawRect(25, 25, widthPX, heightPX);
    }
}
