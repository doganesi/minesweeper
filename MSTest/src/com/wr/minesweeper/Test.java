package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;
import com.wr.util.RandUtil;
import com.wr.util.menu.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class Test
{
    protected static ApplicationMenu testMenu = new ApplicationMenu("Minesweeper Loading Harness");

    public static void main(String[] args)
    {
//        testMenu.addMenuItem(new ApplicationMenuCommandItem("Start Desktop Application", Test::startDesktopApplication));
//        testMenu.addMenuItem(new ApplicationMenuCommandItem("Start Terminal Application", Test::startTerminalApplication));
//        testMenu.addMenuItem(new ApplicationMenuCommandItem("Start Synchronized Test", Test::startApplicationSyncTest));
//        TerminalMenuUtil.showMenu(testMenu);
        startApplicationSyncTest(null);
//        startTerminalApplication(null);
    }

    public static void startDesktopApplication(ApplicationMenuItem menuItem)
    {
        MSMainApp_Desktop mainApp_Desktop = new MSMainApp_Desktop();
        mainApp_Desktop.start();
    }

    public static void startTerminalApplication(ApplicationMenuItem menuItem)
    {
        MSMainApp_Terminal mainApp_Terminal = new MSMainApp_Terminal();
        mainApp_Terminal.start();
    }

    public static void startApplicationSyncTest(ApplicationMenuItem menuItem)
    {
        Board board = new Board(Difficulty.MEDIUM);

//        board.addActionListener(new GameFileWriter());

        MSMainApp_Desktop mainApp_Desktop = new MSMainApp_Desktop();
        mainApp_Desktop.start();
        mainApp_Desktop.loadBoard(board);

        MSMainApp_Terminal mainApp_Terminal = new MSMainApp_Terminal();
        mainApp_Terminal.loadBoard(board);
    }

    private static class GameFileWriter implements IBoardActionListener
    {
        private File file = new File("TestFile.txt");
        private PrintWriter printWriter;

        public GameFileWriter()
        {
            if(!file.exists())
            {
                try
                {
                    file.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                printWriter = new PrintWriter(file);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void refresh(Board.TileOperation tileOperation, int x, int y)
        {
            if (printWriter != null)
            {
                printWriter.println(tileOperation.toString() + " : " + x + ", " + y);
                printWriter.flush();
            }
        }
    }

}
