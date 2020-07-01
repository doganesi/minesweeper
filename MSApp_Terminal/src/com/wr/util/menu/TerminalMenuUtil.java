package com.wr.util.menu;

import java.util.Scanner;
import java.util.Stack;

public class TerminalMenuUtil
{
    /**
     * Generic function to show series of command-line menus
     *  - supports navigation to sub-menus
     *  - supports Back navigation to the previous menu
     * @param menuStack
     */
    public static void showMenu(Stack<ApplicationMenu> menuStack)
    {
        ApplicationMenu menu = menuStack.peek();
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println(" ===== " + menu.getName() + " ===== ");

            // print all menu items
            for (int i = 0; i < menu.getMenuItems().size(); i++)
            {
                ApplicationMenuItem menuItem = menu.getMenuItems().get(i);
                System.out.println((i + 1) + ") " + menuItem.getName());
            }

            int backOptionUserIndex = menu.getMenuItems().size() + 1;
            if (menuStack.size() > 1)
            {
                // we are in a sub-menu, activate Back option
                System.out.println(backOptionUserIndex + ") Back");
            }

            String userInput = scanner.next();
            if (userInput == null || userInput.length() == 0)
            {
                System.out.println("No idea what you want.");
                continue;
            }

            try
            {
                int menuIndex = Integer.parseInt(userInput);
                if (menuStack.size() > 1 && menuIndex == backOptionUserIndex)
                {
                    // user asking us to go back
                    menuStack.pop();
                    return;
                }
                else if (menuIndex == 0 || menuIndex > menu.getMenuItems().size())
                {
                    System.out.println("No idea what you want.");
                    continue;
                }

                ApplicationMenuItem selectedMenuItem = menu.getMenuItems().get(menuIndex - 1);
                if (selectedMenuItem instanceof ApplicationMenuCommandItem)
                {
                    ApplicationMenuCommandItem commandItem = (ApplicationMenuCommandItem) selectedMenuItem;
                    if (commandItem.getCommand() == null)
                    {
                        System.out.println("Sorry, no defined command for menu item <" + selectedMenuItem.getName() + ">");
                    }
                    else
                    {
                        commandItem.getCommand().runCommand(commandItem);
                    }
                }
                else if (selectedMenuItem instanceof ApplicationSubMenuItem)
                {
                    ApplicationSubMenuItem subMenuItem = (ApplicationSubMenuItem) selectedMenuItem;
                    menuStack.add(subMenuItem.getApplicationMenu());
                    showMenu(menuStack);
                }
            }
            catch(Exception e)
            {
                System.out.println("No idea what you want. Most likely an invalid number entry");
            }

        }
    }
}
