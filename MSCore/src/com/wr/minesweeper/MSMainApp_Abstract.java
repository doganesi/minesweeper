package com.wr.minesweeper;

import com.wr.util.menu.*;

public abstract class MSMainApp_Abstract
{
    protected ApplicationMenu mainMenu             = new ApplicationMenu("Minesweeper Game");
    protected ApplicationMenu selectDifficultyMenu = new ApplicationMenu("Select Difficulty Level");

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
        }

        // configure mainMenu
        mainMenu.addMenuItem(new ApplicationSubMenuItem("Start Game", selectDifficultyMenu));
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

    public abstract void loadBoard(Board board);
}
