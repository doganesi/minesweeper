package com.wr.minesweeper;

public class ArrayUtil
{
    /**
     * Create IntArrayCellHandler interface
     * Create 2 classes that implement IntArrayCellHandler
     *    * NumberFiller (fill int array with incremental numbers (i.e. do what test() function already does
     *    * NumberPrinter (print int array)
     * Create iterate2DIntArray function
     *
     */
    public interface IntArrayCellHandler
    {
        int handle(int currentValue, int x, int y, int maxX, int maxY);
    }

    public static void iterate2DIntArray(int[][] arr, IntArrayCellHandler intArrayCellHandler)
    {
        int maxX = arr.length;
        int maxY = arr[0].length;
        for (int y = 0; y < maxY; y++)
        {
            for (int x = 0; x < maxX; x++)
            {
                //
                int handlerResult = intArrayCellHandler.handle(arr[x][y], x, y, maxX - 1, maxY -1);
                arr[x][y] = handlerResult;
            }
        }
    }

//    public static class NumberPrinter implements IntArrayCellHandler
//    {
//        int handleableDigitAmount;
//
//        public NumberPrinter(int digitAmount)
//        {
//            handleableDigitAmount = digitAmount;
//        }
//
//        @Override
//        public int handle(int currentValue, int x, int y, int maxX, int maxY)
//        {
//            String printout = String.valueOf(currentValue);
//            System.out.print(" ".repeat(handleableDigitAmount - printout.length()) + printout + " ");
//
//            if (x == maxX)
//            {
//                System.out.print('\n');
//            }
//            return currentValue;
//        }
//    }

    public static class SimpleCellFiller implements IntArrayCellHandler
    {
        int currentIntValue;

        public SimpleCellFiller(int paramIntValue)
        {
            this.currentIntValue = paramIntValue;
        }

        @Override
        public int handle(int currentValue, int x, int y, int maxX, int maxY)
        {
            return currentIntValue;
        }
    }

    public static class IncrementalCellFiller implements IntArrayCellHandler
    {
        @Override
        public int handle(int currentValue, int x, int y, int maxX, int maxY)
        {
            return y * (maxX + 1) + (x+1);
        }
    }

    public static void main(String[] args)
    {
        int[][] testArr = new int[50][50];
//        NumberPrinter numberPrinter = new NumberPrinter(4);
        SimpleCellFiller simpleCellFiller = new SimpleCellFiller(2);
        IncrementalCellFiller incrementalCellFiller = new IncrementalCellFiller();

//        iterate2DIntArray(testArr, simpleCellFiller);
//        iterate2DIntArray(testArr, numberPrinter);
        iterate2DIntArray(testArr, incrementalCellFiller);
//        iterate2DIntArray(testArr, numberPrinter);
    }


//    /**
//     *
//     * @param arr
//     */
//    public static void test(int[][] arr)
//    {
//        int maxX = arr.length;
//        int maxY = arr[0].length;
//        int testCounter = 1;
//        for (int y = 0; y < maxY; y++)
//        {
//            for (int x = 0; x < maxX; x++)
//            {
//                arr[x][y] = testCounter++;
//            }
//        }
//    }
//
//    public static void main(String[] args)
//    {
//        int[][] testArray = new int[2][3];
//        test(testArray);
//        int maxX = testArray.length;
//        int maxY = testArray[0].length;
//        for (int y = 0; y < maxY; y++)
//        {
//            for (int x = 0; x < maxX; x++)
//            {
//                System.out.print(testArray[x][y] + " ");
//            }
//            System.out.print("\n");
//        }
//
//        // -------------------
//
//
//
//    }
}
