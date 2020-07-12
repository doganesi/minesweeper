package com.wr.minesweeper;

import com.wr.util.array.TerminalArrayUtil;
import com.wr.util.menu.*;

public class MSMainApp_Terminal
{
    private ApplicationMenu mainMenu = new ApplicationMenu("Minesweeper Game");
    private ApplicationMenu selectDifficultyMenu = new ApplicationMenu("Select Difficulty Level");

    public MSMainApp_Terminal()
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
                    TerminalMSGame newGame = new TerminalMSGame(difficulty);
                    newGame.startGame();
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

    public void start()
    {
        TerminalMenuUtil.showMenu(mainMenu);
    }


}
