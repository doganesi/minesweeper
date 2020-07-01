package com.wr.util.menu;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMenu
{
    private String name;
    private List<ApplicationMenuItem> menuItems = new ArrayList<>();

    public ApplicationMenu(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<ApplicationMenuItem> getMenuItems()
    {
        return menuItems;
    }

    public void setMenuItems(List<ApplicationMenuItem> menuItems)
    {
        this.menuItems = menuItems;
    }

    public void addMenuItem(ApplicationMenuItem menuItem)
    {
        menuItems.add(menuItem);
    }
}
