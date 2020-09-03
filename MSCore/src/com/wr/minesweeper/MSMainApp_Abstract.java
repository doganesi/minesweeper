package com.wr.minesweeper;

import com.wr.util.menu.*;

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
                    // do nothing so far
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

    public abstract void loadBoard(IBoard board);

    public abstract void loadGame();
}
