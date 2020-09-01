package com.wr.minesweeper;

import com.wr.util.file.IFileHandler;
import com.wr.util.file.TerminalFileHandler;
import com.wr.util.menu.TerminalMenuUtil;

import java.io.File;

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
