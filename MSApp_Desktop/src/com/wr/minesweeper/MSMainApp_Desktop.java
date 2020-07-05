package com.wr.minesweeper;

import com.wr.util.menu.*;

import javax.swing.*;
import java.awt.*;

public class MSMainApp_Desktop
{
    private ApplicationMenu mainMenu = new ApplicationMenu("Minesweeper Game");
    private ApplicationMenu funMenu = new ApplicationMenu("Fun menu");
    private ApplicationMenu selectDifficultyMenu = new ApplicationMenu("Select Difficulty Level");

    public MSMainApp_Desktop()
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
        JFrame mainFrame = new JFrame("Minesweeper");
        mainFrame.setSize(new Dimension(550, 250));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        menuBar.add(DesktopMenuUtil.getJMenu(mainFrame, null, mainMenu));

//        BoardComponent boardComponent = new BoardComponent(board, 50);
//        mainFrame.getContentPane().add(boardComponent);
        mainFrame.setVisible(true);
//        mainFrame.pack();
    }

}
