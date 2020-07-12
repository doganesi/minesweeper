package com.wr.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardComponent extends JComponent
{
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
            public void mouseClicked(MouseEvent e)
            {
                flagTile(e.getX(), e.getX());
            }
        });
    }

    public void flagTile(int mouseXCoordinate, int mouseYCoordinate)
    {
        System.out.println(mouseXCoordinate + ", " + mouseYCoordinate);

        // 0-based tile coordinates in the board
        int tileX = 5;
        int tileY = 5;

        Tile tile = board.getTile(tileX, tileY);
        tile.toggleFlag();
        repaint();
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

        g.setColor(Color.GRAY);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BasicStroke bs = new BasicStroke(3.0f);
        ((Graphics2D)g).setStroke(bs);

        for (int y = 0; y < board.getYTiles(); y++)
        {
            for (int x = 0; x < board.getXTiles(); x++)
            {
                Graphics tileGraphics = g.create(x * tileSizePX + 25, y * tileSizePX + 25, tileSizePX, tileSizePX);
                tileDrawer.handleDrawing(tileGraphics, tileSizePX, tileSizePX, board.getTile(x, y));
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(25, 25, widthPX, heightPX);
    }

    public static class TileDrawer
    {
        public void handleDrawing(Graphics g, int widthPX, int heightPX, Tile tile)
        {
            g.drawRect(0 , 0, widthPX , heightPX);
            if (tile.getBoard().getGameState() == Board.GameState.OVER && tile.isHasMine())
            {
                g.setColor(Color.BLACK);
                g.drawOval(5, 5 , 40  , 40);
                g.fillOval(5, 5, 40, 40);
            }
            if (tile.getTileState() == Tile.State.FLAGGED)
            {
                g.setColor(Color.RED);
                g.drawRoundRect(0, 0, 20, 20, 20 , 20);
            }
        }
    }
}
