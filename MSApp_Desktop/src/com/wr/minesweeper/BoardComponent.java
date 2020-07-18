package com.wr.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardComponent extends JComponent
{
    private static final int BOARD_INDENT = 30;
    private int tileSizePX;
    private int widthPX;
    private int heightPX;
    private Board board;
    private TileDrawer tileDrawer = new TileDrawer();


    public BoardComponent(Board board, int tileSizePX)
    {
        this.board = board;
        this.tileSizePX = tileSizePX;
        widthPX = board.getXTiles() * tileSizePX;
        heightPX = board.getYTiles() * tileSizePX;
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    performTileOperation(Board.TileOperation.FLAG_TOGGLE, e.getX(), e.getY());
                }
                else if (e.getButton() == MouseEvent.BUTTON2)
                {
                    performTileOperation(Board.TileOperation.OPEN, e.getX(), e.getY());
                }
            }
        });
    }

    public int getTileXFromMouseX(int mouseXCoordinate)
    {
        int tileX = (mouseXCoordinate - BOARD_INDENT) / tileSizePX;
        if (tileX >= board.getXTiles() || tileX < 0)
        {
            return -1;
        }
        return tileX;
    }

    public int getTileYFromMouseY(int mouseYCoordinate)
    {
        int tileY = (mouseYCoordinate - BOARD_INDENT) / tileSizePX;
        if (tileY >= board.getYTiles() || tileY < 0)
        {
            return -1;
        }
        return tileY;
    }

    public void performTileOperation(Board.TileOperation tileOperation, int mouseXCoordinate, int mouseYCoordinate)
    {
        // 0-based tile coordinates in the board
        int tileX = getTileXFromMouseX(mouseXCoordinate);
        int tileY = getTileYFromMouseY(mouseYCoordinate);
        if (tileX < 0 || tileY < 0)
        {
            return;
        }
        board.performTileOperation(tileOperation, tileX, tileY);
        repaint();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return getSize();
    }

    @Override
    public Dimension getSize()
    {
        return new Dimension(widthPX + BOARD_INDENT * 2, heightPX + BOARD_INDENT * 2);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BasicStroke bs = new BasicStroke(3.0f);
        ((Graphics2D)g).setStroke(bs);

        for (int y = 0; y < board.getYTiles(); y++)
        {
            for (int x = 0; x < board.getXTiles(); x++)
            {
                Graphics tileGraphics = g.create(x * tileSizePX + BOARD_INDENT, y * tileSizePX + BOARD_INDENT, tileSizePX, tileSizePX);
                tileDrawer.handleDrawing(tileGraphics, tileSizePX, tileSizePX, board.getTile(x, y));
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(BOARD_INDENT, BOARD_INDENT, widthPX, heightPX);
    }

    public static class TileDrawer
    {
        public void handleDrawing(Graphics g, int widthPX, int heightPX, Tile tile)
        {
            g.drawRect(0 , 0, widthPX , heightPX);
            int tileIndent = widthPX / 10;

            if (tile.getBoard().getGameState() == Board.GameState.DEBUG)
            {
                if (tile.isHasMine())
                {
                    g.setColor(Color.BLACK);
                    g.drawOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                    g.fillOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                }
                else
                {
                    g.setColor(tile.getNumMinesAroundColor());
                    g.drawChars(new char[] {tile.getNumMinesAroundChar()}, 0, 1, 15, 15);
                }

            }
            else
            {
                if (tile.getBoard().getGameState() == Board.GameState.OVER && tile.isHasMine())
                {
                    g.setColor(Color.BLACK);
                    g.drawOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                    g.fillOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                }
                if (tile.getTileState() == Tile.State.FLAGGED)
                {
                    g.setColor(Color.RED);
                    g.drawRoundRect(tileIndent, tileIndent, widthPX - tileIndent * 2 , heightPX - tileIndent * 2, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                }

            }
        }
    }
}
