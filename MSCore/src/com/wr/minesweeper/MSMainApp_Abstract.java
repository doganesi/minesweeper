package com.wr.minesweeper;

import com.wr.util.menu.*;

import java.io.IOException;

public abstract class MSMainApp_Abstract
{
    protected ApplicationMenu mainMenu             = new ApplicationMenu("Minesweeper Game");
    protected ApplicationMenu startGameMenu        = new ApplicationMenu("Start Game");
    protected ApplicationMenu multiplayerMenu        = new ApplicationMenu("Multiplayer");
    protected ApplicationMenu selectDifficultyMenu = new ApplicationMenu("Select Difficulty Level");
    protected ApplicationMenu selectDifficultyMenuHost = new ApplicationMenu("Select Difficulty Level");

    public MSMainApp_Abstract()
    {
        configureMenu();
    }

    protected void configureMenu()
    {
        // configure selectDifficultyMenu
        for (int i = 0; i < Difficulty.DIFFICULTY_LEVELS.length; i++)
        {
            Difficulty difficulty = Difficulty.DIFFICULTY_LEVELS[i];
            selectDifficultyMenu.addMenuItem(new ApplicationMenuCommandItem(difficulty.getName(), new IMenuItemCommand()
            {
                @Override
                public void runCommand(ApplicationMenuItem menuItem)
                {
                    loadLevel(difficulty);
                }
            }));
            selectDifficultyMenuHost.addMenuItem(new ApplicationMenuCommandItem(difficulty.getName(), new IMenuItemCommand()
            {
                @Override
                public void runCommand(ApplicationMenuItem menuItem)
                {
                    loadLevelServer(difficulty);
                }
            }));
        }

        // configure mainMenu
        mainMenu.addMenuItem(new ApplicationSubMenuItem(null, startGameMenu));
        {
            startGameMenu.addMenuItem(new ApplicationSubMenuItem("Single Player", selectDifficultyMenu));
            startGameMenu.addMenuItem(new ApplicationSubMenuItem(null, multiplayerMenu));
            {
                multiplayerMenu.addMenuItem(new ApplicationSubMenuItem("Host Game", selectDifficultyMenuHost));
                multiplayerMenu.addMenuItem(new ApplicationMenuCommandItem("Join Game", new IMenuItemCommand()
                {
                    @Override
                    public void runCommand(ApplicationMenuItem menuItem)
                    {
                        String ipAddress = getServerIP();
                        if (ipAddress == null || ipAddress.length() == 0)
                        {
                            reportError("Invalid Address");
                            return;
                        }
                        try
                        {
                            BoardClient clientBoard = new BoardClient(ipAddress);
                            while(!clientBoard.isReady())
                            {
                                System.out.println("waiting");
                                try
                                {
                                    Thread.sleep(1000);
                                }
                                catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            loadBoard(clientBoard);
                        }
                        catch (IOException ioException)
                        {
                            ioException.printStackTrace();
                            reportError(ioException.getMessage());
                        }
                    }
                }));
            }
        }
        mainMenu.addMenuItem(new ApplicationMenuCommandItem("Load Game", new IMenuItemCommand() {
            @Override
            public void runCommand(ApplicationMenuItem menuItem)
            {
                loadGame();
            }
        }));
        mainMenu.addMenuItem(new ApplicationMenuCommandItem("Exit", new IMenuItemCommand()
        {
            @Override
            public void runCommand(ApplicationMenuItem menuItem)
            {
                System.exit(1);
            }
        }));
    }

    public abstract void start();

    public void loadLevel(Difficulty difficulty)
    {
        Board board = new Board(difficulty);
        loadBoard(board);
    }

    public void loadLevelServer(Difficulty difficulty)
    {
        try
        {
            BoardServer board = new BoardServer(difficulty);
            loadBoard(board);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
            reportError(ioException.getMessage());
        }
    }

    protected abstract String getServerIP();

    protected abstract void reportError(String error);

    public abstract void loadBoard(IBoard board);

    public abstract void loadGame();
}
