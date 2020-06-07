package com.wr.minesweeper;

public class TerminalUtil
{
    public static void printBoard(Board board)
    {
        char[][] boardPrintout = new char[board.getXTiles()+2][board.getYTiles()+2];
        for (int y = 0; y < board.getYTiles() + 2; y++)
        {
            for (int x = 0; x < board.getXTiles() + 2; x++)
            {
                char c = ' ';
                if (x == 0 && y == 0)
                {
                    c = '┌';
                }
                else if (x == board.getXTiles() + 1 && y == 0)
                {
                    c = '┐';
                }
                else if (x == board.getXTiles() + 1 && y == board.getYTiles() + 1)
                {
                    c = '┘';
                }
                else if (x == 0 && y == board.getYTiles() + 1)
                {
                    c = '└';
                }
                else if (y == 0)
                {
                    c = '-';
                }
                else if (x == 0)
                {
                    c = '|';
                }
                else if (x == board.getXTiles() + 1)
                {
                    c = '|';
                }
                else if (y == board.getYTiles() + 1)
                {
                    c = '-';
                }
                else
                {
                    // if we are here, it means that
                    // this is an actual board tile (not a border)
                    int actualBoardX = x - 1;
                    int actualBoardY = y - 1;
                    Tile currentTile = board.getTile(actualBoardX, actualBoardY);
                    if (currentTile == null)
                    {
                        c = 'E';
                    }
                    else if (currentTile.isHasMine())
                    {
                        c = '*';
                    }
                    else
                    {
                        c = '\u25A1';
                    }
                }
                System.out.print(c);
            }
            System.out.print('\n');
        }
    }
}
