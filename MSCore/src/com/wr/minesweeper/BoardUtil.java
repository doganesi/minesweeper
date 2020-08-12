package com.wr.minesweeper;

import java.io.*;

public class BoardUtil
{
    public static boolean saveBoard(Board board, File file)
    {
        try
        {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(board);

            o.close();
            f.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return false;
    }

    public static Board loadBoard(File file)
    {
        try
        {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);

            Board board = (Board) oi.readObject();
            board.initListeners();

            oi.close();
            fi.close();

            return board;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;

    }
}
