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

    public static class NumberBorderCellHandler implements CharArrayCellHandler
    {
        private int level;
        private int maxLevel;

        public NumberBorderCellHandler(int level, int maxLevel)
        {
            this.level = level;
            this.maxLevel = maxLevel;
        }

        private char getOrderChar(int actualIndex, int order)
        {
            int remainder = actualIndex % order;
            int result = actualIndex / order;
            if (result > 9)
            {
                result = result % 10;
            }
            return remainder == 0 ? (char) (48 + result) : ' ';
        }

        @Override
        public char handle(char currentValue, int x, int y, int maxX, int maxY)
        {
            if (x == 0 && y > level && y < maxY - level)
            {
                return getOrderChar(y - level, (int) Math.pow(10, level - 1));
            }
            else if (x == maxX && y > level && y < maxY - level)
            {
                return getOrderChar(y - level, (int) Math.pow(10, maxLevel - level));
            }
            else if (y == 0 && x > level && x < maxX - level)
            {
                return getOrderChar(x - level, (int) Math.pow(10, (level - 1)));
            }
            else if (y == maxY && x > level && x < maxX - level)
            {
                return getOrderChar(x - level, (int) Math.pow(10, maxLevel - level));
            }
            else
            {
                return ' ';
            }
        }
    }
    public static class PrintCellHandler implements CharArrayCellHandler
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

    /**
     *
     * @param sourceBoard
     * @param borderCellHandler
     * @return
     */
    public static char[][] wrapBoard(char[][] sourceBoard, CharArrayCellHandler borderCellHandler)
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
                    return borderCellHandler.handle(currentValue, x, y, maxX, maxY);
                }
            }
        });
        return wrappedBoard;
    }

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
        char[][] numbersBoard = wrapBoard(borderBoard, new NumberBorderCellHandler(1, 2));
        char[][] numbersBoard2 = wrapBoard(numbersBoard, new NumberBorderCellHandler(2, 2));
//        char[][] numbersBoard3 = wrapBoard(numbersBoard2, new NumberBorderCellHandler(3, 3));
        char[][] borderBoard2 = wrapBoard(numbersBoard2, new BorderCellHandler(BorderType.DOUBLE));
        iterate2DCharArray(borderBoard2, new PrintCellHandler());
    }

}
