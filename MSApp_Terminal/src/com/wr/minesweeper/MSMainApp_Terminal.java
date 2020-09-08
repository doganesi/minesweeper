package com.wr.minesweeper;

import com.wr.util.file.IFileHandler;
import com.wr.util.file.TerminalFileHandler;
import com.wr.util.menu.TerminalMenuUtil;

import java.io.File;
import java.util.Scanner;

public class MSMainApp_Terminal extends MSMainApp_Abstract
{
    public MSMainApp_Terminal()
    {

    }

    public void start()
    {
        TerminalMenuUtil.showMenu(mainMenu);
    }

    @Override
    protected void reportError(String error)
    {
        System.err.println("Error: " + error);
    }

    @Override
    protected String getServerIP()
    {
        System.out.println("Enter Game Host's IP Address : ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.next();
        return userInput;
    }

    @Override
    public void loadBoard(IBoard board)
    {
        TerminalMSGame newGame = new TerminalMSGame(board);
        newGame.startGame();
    }

    @Override
    public void loadGame()
    {
        TerminalFileHandler.handleFile("Enter file path to load game", "minesweeper", false, new IFileHandler()
        {
            @Override
            public void handleFile(File file)
            {
                IBoard loadedBoard = BoardUtil.loadBoard(file);
                if (loadedBoard != null)
                {
                    loadBoard(loadedBoard);
                }
            }
        });
    }
}
