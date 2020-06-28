package com.wr.minesweeper.desktop;

import com.wr.minesweeper.Board;
import com.wr.minesweeper.Tile;

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
        TileDrawer tileDrawer = new TileDrawer();
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

    public interface GraphicsCellHandler
    {
        public void handleDrawing(Graphics g, int widthPX, int heightPX, Tile tile);
    }

    public static class TileDrawer implements GraphicsCellHandler
    {
        @Override
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
