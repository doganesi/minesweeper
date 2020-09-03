package com.wr.util.menu;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class TerminalMenuUtil
{

    public static void showMenu(ApplicationMenu menu)
    {
        Stack<ApplicationMenu> menuStack = new Stack<>();
        menuStack.add(menu);
        handleMenuStack(menuStack);
    }

    /**
     * Generic function to show series of command-line menus
     *  - supports navigation to sub-menus
     *  - supports Back navigation to the previous menu
     * @param menuStack
     */
    private static void handleMenuStack(Stack<ApplicationMenu> menuStack)
    {
        ApplicationMenu menu = menuStack.peek();
        Scanner scanner = new Scanner(System.in);
        List<ApplicationMenuItem> menuItems = menu.getMenuItems();
        while(true)
        {
            System.out.println(" ===== " + menu.getName() + " ===== ");

            // print all menu items
            for (int i = 0; i < menuItems.size(); i++)
            {
                ApplicationMenuItem menuItem = menuItems.get(i);
                String name = menuItem.getName();
                if (name == null && menuItem instanceof ApplicationSubMenuItem)
                {
                    name = ((ApplicationSubMenuItem)menuItem).getApplicationMenu().getName();
                }
                System.out.println((i + 1) + ") " + name);
            }

            int backOptionUserIndex = menuItems.size() + 1;
            if (menuStack.size() > 1)
            {
                // we are in a sub-menu, activate Back option
                System.out.println(backOptionUserIndex + ") Back");
            }

            String userInput = scanner.next();
            int menuIndex = 0;
            try
            {
                menuIndex = Integer.parseInt(userInput);
            }
            catch (NumberFormatException e)
            {
            }

            if (menuStack.size() > 1 && menuIndex == backOptionUserIndex)
            {
                // user asking us to go back
                menuStack.pop();
                return;
            }
            else if (menuIndex == 0 || menuIndex > menuItems.size())
            {
                System.out.println("No idea what you want.");
                continue;
            }

            ApplicationMenuItem selectedMenuItem = menuItems.get(menuIndex - 1);
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
                handleMenuStack(menuStack);
            }
        }
    }
}
