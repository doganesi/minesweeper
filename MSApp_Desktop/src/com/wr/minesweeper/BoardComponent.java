package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardComponent extends JComponent implements IBoardActionListener
{
    private static final int BOARD_INDENT = 30;
    private int tileSizePX;
    private int widthPX;
    private int heightPX;
    private IBoard board;
    private TileDrawer tileDrawer = new TileDrawer();


    public BoardComponent(IBoard board, int tileSizePX)
    {
        this.board = board;
        this.tileSizePX = tileSizePX;
        widthPX = board.getXTiles() * tileSizePX;
        heightPX = board.getYTiles() * tileSizePX;
        board.addActionListener(this);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    performTileOperation(IBoard.TileOperation.FLAG_TOGGLE, e.getX(), e.getY());
                }
                else if (e.getButton() == MouseEvent.BUTTON1)
                {
                    performTileOperation(IBoard.TileOperation.OPEN, e.getX(), e.getY());
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

    public void performTileOperation(IBoard.TileOperation tileOperation, int mouseXCoordinate, int mouseYCoordinate)
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

    @Override
    public void refresh(IBoard.TileOperation tileOperation, int x, int y)
    {
        invalidate();
        repaint();
    }

    public static class TileDrawer
    {
        public void handleDrawing(Graphics g, int widthPX, int heightPX, Tile tile)
        {
            g.drawRect(0 , 0, widthPX , heightPX);
            int tileIndent = widthPX / 10;
            int charSizeW = g.getFontMetrics().charWidth(tile.getNumMinesAroundChar());
            int charSizeH = g.getFontMetrics().getHeight();
            int charX = (widthPX-charSizeW)/2;
            int charY = (int) ((heightPX-charSizeH)/2 + heightPX/1.5);


            if (tile.getBoard().getGameState() == IBoard.GameState.RUNNING)
            {
                if (tile.getTileState() == Tile.State.CLOSED)
                {
                    g.setColor(new Color(40, 100, 100));
                    g.drawRect(5,5, widthPX/2, heightPX/2);
                    g.fillRect(5,5, widthPX/2, heightPX/2);
                }
                else if (tile.getTileState() == Tile.State.FLAGGED)
                {
                    g.setColor(Color.RED);
                    g.drawRoundRect(tileIndent, tileIndent, widthPX - tileIndent * 2 , heightPX - tileIndent * 2, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                }
                else
                {
                    g.setColor(tile.getNumMinesAroundColor());
                    Font currentFont = g.getFont();
                    Font newFont = new Font(currentFont.getFontName(), Font.BOLD, currentFont.getSize());
                    g.setFont(newFont);
                    g.drawChars(new char[] {tile.getNumMinesAroundChar()}, 0, 1, charX, charY);
                }
            }
            else if (tile.getBoard().getGameState() == IBoard.GameState.OVER_LOSE)
            {
                if (tile.isHasMine())
                {
                    g.setColor(new Color (100, 10, 2));
                    g.drawOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                    g.fillOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                }
                else if(tile.getTileState() == Tile.State.OPEN)
                {
                    g.setColor(tile.getNumMinesAroundColor());
                    Font currentFont = g.getFont();
                    Font newFont = new Font(currentFont.getFontName(), Font.BOLD, currentFont.getSize());
                    g.setFont(newFont);
                    g.drawChars(new char[] {tile.getNumMinesAroundChar()}, 0, 1, charX, charY);
                }

            }
            else if(tile.getBoard().getGameState() == IBoard.GameState.OVER_WIN)
            {
                if (tile.isHasMine())
                {
                    g.setColor( new Color(34,139,34));
                    g.drawOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                    g.fillOval(tileIndent, tileIndent, widthPX - tileIndent * 2, heightPX - tileIndent * 2);
                }
                else if(tile.getTileState() == Tile.State.OPEN)
                {
                    g.setColor(tile.getNumMinesAroundColor());
                    Font currentFont = g.getFont();
                    Font newFont = new Font(currentFont.getFontName(), Font.BOLD, currentFont.getSize());
                    g.setFont(newFont);
                    g.drawChars(new char[] {tile.getNumMinesAroundChar()}, 0, 1, charX, charY);
                }
            }
        }
    }
}
