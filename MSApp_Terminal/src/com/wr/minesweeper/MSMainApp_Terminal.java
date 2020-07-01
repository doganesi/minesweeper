package com.wr.minesweeper;

import com.wr.util.menu.*;

import java.util.Stack;

public class MSMainApp_Terminal
{
    private ApplicationMenu mainMenu = new ApplicationMenu("Minesweeper Game");
    private ApplicationMenu funMenu = new ApplicationMenu("Fun menu");
    private ApplicationMenu selectDifficultyMenu = new ApplicationMenu("Select Difficulty Level");

    public MSMainApp_Terminal()
    {

        // configure selectDifficultyMenu
        for (int i = 0; i < Difficulty.DIFFICULTY_LEVELS.length; i++)
        {
            Difficulty difficulty = Difficulty.DIFFICULTY_LEVELS[i];
            selectDifficultyMenu.addMenuItem(new ApplicationMenuCommandItem(difficulty.getName(), null));
        }

        // configure funMenu
        funMenu.addMenuItem(new ApplicationMenuCommandItem("Print cheerful message", new IMenuItemCommand()
        {
            @Override
            public void runCommand(ApplicationMenuItem menuItem)
            {
                System.out.println("It will all be OK in the end. If it's not OK, it's not the end");
            }
        }));
        funMenu.addMenuItem(new ApplicationSubMenuItem("Start Game", selectDifficultyMenu));

        // configure mainMenu
        mainMenu.addMenuItem(new ApplicationSubMenuItem("Have fun", funMenu));
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
        Stack<ApplicationMenu> menuStack = new Stack<>();
        menuStack.add(mainMenu);
        TerminalMenuUtil.showMenu(menuStack);
    }

}
