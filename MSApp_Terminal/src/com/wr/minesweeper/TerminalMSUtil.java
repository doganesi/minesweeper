package com.wr.minesweeper;

import com.wr.util.array.BorderType;

import static com.wr.util.array.TerminalArrayUtil.*;

public class TerminalMSUtil
{
    public static void printBoard(Board board)
    {
        char[][] baseBoard = new char[board.getXTiles()][board.getYTiles()];

        iterate2DCharArray(baseBoard, new CharArrayCellHandler()
        {
            @Override
            public char handle(char currentValue, int x, int y, int maxX, int maxY)
            {
                Tile currentTile = board.getTile(x, y);
                if (currentTile == null)
                {
                    return 'E';
                }
                else if (currentTile.getBoard().getGameState() == Board.GameState.OVER && currentTile.isHasMine())
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
        });
        char[][] borderBoard = wrapBoard(baseBoard, new BorderCellHandler(BorderType.SINGLE));
        char[][] numbersBoard = wrapBoard(borderBoard, new NumberBorderCellHandler(1, 2));
        char[][] numbersBoard2 = wrapBoard(numbersBoard, new NumberBorderCellHandler(2, 2));
//        char[][] numbersBoard3 = wrapBoard(numbersBoard2, new NumberBorderCellHandler(3, 3));
        char[][] borderBoard2 = wrapBoard(numbersBoard2, new BorderCellHandler(BorderType.DOUBLE));
        iterate2DCharArray(borderBoard2, new PrintCellHandler());
    }
}
