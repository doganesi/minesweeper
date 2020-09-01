package com.wr.minesweeper;

import java.io.*;
import java.nio.file.Files;

public class BoardUtil
{
    public static boolean saveBoardBinary(Board board, File file)
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

    public static Board loadBoardBinary(File file)
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

    public static boolean saveBoard(IBoard board, File file)
    {
        Board actualBoard = null;
        if (board instanceof Board)
        {
            actualBoard = (Board) board;
        }
        else
        {
            return false;
        }

        try
        {
            String boardString = Board.saveBoardToString(actualBoard);
            Files.writeString(file.toPath(), boardString);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return false;
    }

    public static IBoard loadBoard(File file)
    {
        try
        {
            String savedBoardString = Files.readString(file.toPath());
            Board loadedBoard = Board.loadBoardFromString(savedBoardString);

            loadedBoard.initListeners();

            return loadedBoard;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;

    }


}
