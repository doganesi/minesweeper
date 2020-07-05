package com.wr.util.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DesktopMenuUtil
{
    public static JMenu getJMenu(Component parentComponent, String subMenuName, ApplicationMenu menu)
    {
        List<ApplicationMenuItem> menuItems = menu.getMenuItems();
        JMenu jMenu = new JMenu();
        if (subMenuName != null)
        {
            jMenu.setText(subMenuName);
            JLabel menuTitle = new JLabel(menu.getName());
            jMenu.add(menuTitle);
            jMenu.addSeparator();
        }
        else
        {
            jMenu.setText(menu.getName());
        }

        for (ApplicationMenuItem menuItem : menuItems)
        {
            if (menuItem instanceof ApplicationSubMenuItem)
            {
                ApplicationSubMenuItem subMenuItem = (ApplicationSubMenuItem) menuItem;
                ApplicationMenu subMenu = subMenuItem.getApplicationMenu();
                JMenu subJMenu = getJMenu(parentComponent, subMenuItem.getName(), subMenu);
                jMenu.add(subJMenu);
            } else if (menuItem instanceof ApplicationMenuCommandItem)
            {
                ApplicationMenuCommandItem commandItem = (ApplicationMenuCommandItem) menuItem;
                IMenuItemCommand command = commandItem.getCommand();
                JMenuItem jMenuItem = new JMenuItem(commandItem.getName());
                jMenuItem.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (command == null)
                        {
                            JOptionPane.showMessageDialog(parentComponent, "No Command Defined", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            command.runCommand(commandItem);
                        }
                    }
                });
                jMenu.add(jMenuItem);
            }
        }
        return jMenu;
    }

}
