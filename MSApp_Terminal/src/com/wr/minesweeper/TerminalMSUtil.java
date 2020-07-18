package com.wr.minesweeper;

import com.wr.util.array.BorderType;
import com.wr.util.array.TerminalArrayUtil;

import java.awt.*;

import static com.wr.util.array.TerminalArrayUtil.*;

public class TerminalMSUtil
{
    public static void printBoard(Board board)
    {
        char[][] baseBoard = new char[board.getXTiles()][board.getYTiles()];
        Color[][] baseBoardColors = new Color[board.getXTiles()][board.getYTiles()];

        iterate2DCharArray(baseBoard, new CharArrayCellHandler()
        {
            @Override
            public char handle(char currentValue, int x, int y, int maxX, int maxY)
            {
                Tile currentTile = board.getTile(x, y);


                if (currentTile.getBoard().getGameState() == Board.GameState.DEBUG)
                {
                    if (currentTile.isHasMine())
                    {
                        return '*';
                    }
                    else
                    {
                        baseBoardColors[x][y] = currentTile.getNumMinesAroundColor();
                        return currentTile.getNumMinesAroundChar();
                    }

                }
                else
                {
                    if (currentTile.getBoard().getGameState() == Board.GameState.OVER && currentTile.isHasMine())
                    {
                        return '*';
                    }
                    else if (currentTile.getTileState() == Tile.State.FLAGGED)
                    {
                        return 'F';
                    }
                    else
                    {
                        return '\u25A1';
                    }
                }



            }
        });
        char[][] borderBoard = wrapBoard(baseBoard, new BorderCellHandler(BorderType.SINGLE));
        char[][] numbersBoard = wrapBoard(borderBoard, new NumberBorderCellHandler(1, 2));
        char[][] numbersBoard2 = wrapBoard(numbersBoard, new NumberBorderCellHandler(2, 2));
//        char[][] numbersBoard3 = wrapBoard(numbersBoard2, new NumberBorderCellHandler(3, 3));
        char[][] borderBoard2 = wrapBoard(numbersBoard2, new BorderCellHandler(BorderType.DOUBLE));

        int colorArrayOffset = (borderBoard2.length - baseBoard.length) / 2;
        int startColorX = colorArrayOffset;
        int startColorY = colorArrayOffset;
        int endColorX = colorArrayOffset + baseBoard.length;
        int endColorY = colorArrayOffset + baseBoard[0].length;
        iterate2DCharArray(borderBoard2, new CharArrayCellHandler()
        {
            @Override
            public char handle(char currentValue, int x, int y, int maxX, int maxY)
            {
                Color c = null;
                if (x < startColorX || x >= endColorX || y < startColorY || y >= endColorY)
                {
                    c = Color.WHITE;
                }
                else
                {
                    c = baseBoardColors[x - colorArrayOffset][y - colorArrayOffset];
                }
                if (c == null)
                {
                    c = Color.WHITE;
                }


                TerminalArrayUtil.colorSystemOut(String.valueOf(currentValue), c, false, false);
                if (x == maxX)
                {
                    System.out.print('\n');
                }
                return currentValue;
            }
        });
    }
}
