package com.wr.minesweeper;

import com.wr.util.menu.ApplicationMenu;
import com.wr.util.menu.ApplicationMenuItem;
import com.wr.util.menu.IMenuItemCommand;

import java.util.Scanner;

public class MSMainApp_Terminal
{
    private ApplicationMenu mainMenu = new ApplicationMenu("Minesweeper Game");
    private ApplicationMenu selectDifficultyMenu = new ApplicationMenu("Select Difficulty Level");

    public MSMainApp_Terminal()
    {
        mainMenu.getMenuItems().add(new ApplicationMenuItem("Start Game", null));
        mainMenu.getMenuItems().add(new ApplicationMenuItem("Exit", new IMenuItemCommand()
        {
            @Override
            public void runCommand()
            {
                System.exit(1);
            }
        }));

        for (int i = 0; i < Difficulty.DIFFICULTY_LEVELS.length; i++)
        {
            Difficulty difficulty = Difficulty.DIFFICULTY_LEVELS[i];
            selectDifficultyMenu.getMenuItems().add(new ApplicationMenuItem(difficulty.getName(), null));
        }
        selectDifficultyMenu.getMenuItems().add(new ApplicationMenuItem("Back", null));
    }

    public void start()
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println(" "); // Prints a backspace

            for (int i = 0; i < mainMenu.getMenuItems().size(); i++)
            {
                ApplicationMenuItem menuItem = mainMenu.getMenuItems().get(i);
                System.out.println((i+1) + ") " + menuItem.getName());
            }
            String userInput = scanner.next();
            if (userInput == null || userInput.length() > 1)
            {
                System.out.println("No idea what you want.");
                continue;
            }

            try
            {
                int menuIndex = Integer.parseInt(userInput);
                if (menuIndex == 0 || menuIndex > mainMenu.getMenuItems().size())
                {
                    System.out.println("No idea what you want.");
                    continue;
                }

                ApplicationMenuItem selectedMenuItem = mainMenu.getMenuItems().get(menuIndex-1);
                if (selectedMenuItem.getCommand() == null)
                {
                    System.out.println("Sorry, no defined command for menu item <" + selectedMenuItem.getName() + ">");
                }
                else
                {
                    selectedMenuItem.getCommand().runCommand();
                }
            }
            catch(Exception e)
            {
                System.out.println("No idea what you want.");
                continue;
            }

        }


//        System.out.println(selectedItemNumber);
    }
}
