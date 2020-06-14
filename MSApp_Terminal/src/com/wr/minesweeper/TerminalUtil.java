package com.wr.minesweeper;

public class TerminalUtil
{
    public interface CharArrayCellHandler
    {
        /**
         * Handle method will called from methods that ietrate over 2-dimensional char arrays
         *
         * @param currentValue value in the array cell
         * @param x x coordinate of the array cell
         * @param y y coordinate of the array cell
         * @param maxX maximum x coordinate of the array (e.g. if the width of the array is 20, maxX = 19 because X coordinate in the array goes from 0 to width-1)
         * @param maxY maximum y coordinate of the array (e.g. if the height of the array is 20, maxY = 19 because Y coordinate in the array goes from 0 to height-1)
         * @return the job of the function is to return the char value that will be written into the array cell
         */
        char handle(char currentValue, int x, int y, int maxX, int maxY);
    }

    public static class BorderCellHandler implements CharArrayCellHandler
    {
        BorderType borderType;

        public BorderCellHandler()
        {
            this(BorderType.SINGLE);
        }

        public BorderCellHandler(BorderType borderType)
        {
            this.borderType = borderType;
        }

        @Override
        public char handle(char currentValue, int x, int y, int maxX, int maxY)
        {
            if (x == 0 && y == 0)
            {
                // inner rim top left
                return borderType.getUpperLeft();
            }
            else if (x == maxX && y == 0)
            {
                // inner rim top right
                return borderType.getUpperRight();
            }
            else if (x == maxX && y == maxY)
            {
                // inner rim bottom right
                return borderType.getLowerRight();
            }
            else if (x == 0 && y == maxY)
            {
                // inner rim bottom left
                return borderType.getLowerLeft();
            }
            else if (x == 0 || x == maxX)
            {
                return borderType.getVertical();
            }
            else if (y == 0 || y == maxY)
            {
                return borderType.getHorizontal();
            }
            throw new RuntimeException("(" + x + ", " + y + ") is a cell not on the border -- no idea how to handle it!!");

        }
    }

    public static void iterate2DCharArray(char[][] arr, CharArrayCellHandler arrayCellHandler)
    {
        int maxX = arr.length;
        int maxY = arr[0].length;
        for (int y = 0; y < maxY; y++)
        {
            for (int x = 0; x < maxX; x++)
            {
                arr[x][y] = arrayCellHandler.handle(arr[x][y], x, y, maxX - 1, maxY -1);
            }
        }
    }

    public static char[][] wrapBoard(char[][] sourceBoard, CharArrayCellHandler arrayCellHandler)
    {
        int sourceBoardX = sourceBoard.length;
        int sourceBoardY = sourceBoard[0].length;
        int wrappedBoardX = sourceBoardX + 2;
        int wrappedBoardY = sourceBoardY + 2;
        char[][] wrappedBoard = new char[wrappedBoardX][wrappedBoardY];
        iterate2DCharArray(wrappedBoard, new CharArrayCellHandler()
        {
            @Override
            public char handle(char currentValue, int x, int y, int maxX, int maxY)
            {
                if (y > 0 && x > 0 && y < maxY && x < maxX)
                {
                    // we are not on the rim -- we just copy from the source board
                    return sourceBoard[x - 1][y - 1];
                }
                else
                {
                    // we are dealing with one of the cells of the rim
                    // we will ask the outerRimCell filler to tell us what character to place there
                    return arrayCellHandler.handle(currentValue, x, y, maxX, maxY);
                }
            }
        });
        return wrappedBoard;
    }

    public static void printBoard2(Board board)
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
                else if (currentTile.isHasMine())
                {
                    return '*';
                }
                else
                {
                    return '\u25A1';
                }
            }
        });


        char[][] borderBoard = wrapBoard(baseBoard, new BorderCellHandler(BorderType.SINGLE));

        char[][] numbersBoard = wrapBoard(borderBoard, new CharArrayCellHandler()
        {
            @Override
            public char handle(char currentValue, int x, int y, int maxX, int maxY)
            {
                if (y == 0 && (x < 2 || x > maxX - 2))
                {
                    return ' ';
                }
                else if (y == maxY && (x < 2 || x > maxX - 2))
                {
                    return ' ';
                }
                else if (x == 0 && (y < 2 || y > maxY - 2))
                {
                    return ' ';
                }
                else if (x == maxX && (y < 2 || y > maxY - 2))
                {
                    return ' ';
                }
                else if (x == 0 || x == maxX)
                {
                    return (char) (47+y);
                }
                else if (y == 0 || y == maxY)
                {
                    return (char) (47+x);
                }
                return currentValue;
            }
        });

        char[][] borderBoard2 = wrapBoard(numbersBoard, new BorderCellHandler(BorderType.SINGLE));
        char[][] borderBoard3 = wrapBoard(borderBoard2, new BorderCellHandler(new BorderType('*','*', '*', '*', '-', '|')));
        char[][] borderBoard4 = wrapBoard(borderBoard3, new BorderCellHandler(new BorderType('*')));

        iterate2DCharArray(borderBoard4, new CharArrayCellHandler()
        {
            @Override
            public char handle(char currentValue, int x, int y, int maxX, int maxY)
            {
                System.out.print(currentValue);
                if (x == maxX)
                {
                    System.out.print('\n');
                }
                return currentValue;
            }
        });
    }

    public static void printBoard(Board board)
    {
        char[][] boardPrintout = new char[board.getXTiles()+4][board.getYTiles()+4];
        for (int y = 0; y < board.getYTiles() + 4; y++)
        {
            for (int x = 0; x < board.getXTiles() + 4; x++)
            {
                char c;
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
