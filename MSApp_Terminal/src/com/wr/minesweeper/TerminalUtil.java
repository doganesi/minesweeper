package com.wr.minesweeper;

public class TerminalUtil
{
    public static void printBoard(Board board)
    {
        char[][] boardPrintout = new char[board.getXTiles()+4][board.getYTiles()+4];
        for (int y = 0; y < board.getYTiles() + 4; y++)
        {
            for (int x = 0; x < board.getXTiles() + 4; x++)
            {
                char c = ' ';
                if (x == 0 && y == 0)
                {
                    //outer rim top left
                    c = ' ';
                }
                else if (x == board.getXTiles() + 3 && y == 0)
                {
                    //outer rim top right
                    c = ' ';
                }
                else if (x == board.getXTiles() + 3 && y == board.getYTiles() + 3)
                {
                    // outer rim bottom right
                    c = ' ';
                }
                else if (x == 0 && y == board.getYTiles() + 3)
                {
                    // outer rim bottom left
                    c = ' ';
                }
                else if (y == 0 && (x == 1 || x == board.getXTiles() + 2))
                {
                    // outer rim top gaps
                    c = ' ';
                }
                else if (y == 0)
                {
                    // outer rim top numbers
                    c = (char) (47+x);
                }
                else if (x == 0 && (y == 1 || y == board.getYTiles() + 2))
                {
                    // outer rim left gaps
                    c = ' ';
                }
                else if (x == 0)
                {
                    // outer rim left numbers
                    c = (char) (47+y);
                }
                else if (x == board.getXTiles() + 3 && (y == 1 || y == board.getYTiles() + 2))
                {
                    // outer rim right gaps
                    c = ' ';
                }
                else if (x == board.getXTiles() + 3)
                {
                    // outer rim right numbers
                    c = (char) (47+y);
                }
                else if (y == board.getYTiles() + 3 && (x == 1 || x == board.getXTiles() + 2))
                {
                    // outer rim bottom gaps
                    c = ' ';
                }
                else if (y == board.getYTiles() + 3)
                {
                    // outer rim bottom numbers
                    c = (char) (47+x);
                }
                else if (x == 1 && y == 1)
                {
                    // inner rim top left
                    c = '┌';
                }
                else if (x == board.getXTiles() + 2 && y == 1)
                {
                    // inner rim top right
                    c = '┐';
                }
                else if (x == board.getXTiles() + 2 && y == board.getYTiles() + 2)
                {
                    // inner rim bottom right
                    c = '┘';
                }
                else if (x == 1 && y == board.getYTiles() + 2)
                {
                    // inner rim bottom left
                    c = '└';
                }
                else if (y == 1)
                {
                    // inner rim top border
                    c = '-';
                }
                else if (x == 1)
                {
                    // inner rim left border
                    c = '|';
                }
                else if (x == board.getXTiles() + 2)
                {
                    // inner rim right border
                    c = '|';
                }
                else if (y == board.getYTiles() + 2)
                {
                    // inner rim bottom border
                    c = '-';
                }
                else
                {
                    // if we are here, it means that
                    // this is an actual board tile (not a border)
                    int actualBoardX = x - 2;
                    int actualBoardY = y - 2;
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
