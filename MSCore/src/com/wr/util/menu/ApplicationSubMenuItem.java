package com.wr.util.menu;

public class ApplicationSubMenuItem extends ApplicationMenuItem
{
    private ApplicationMenu applicationMenu;

    public ApplicationSubMenuItem(String name, ApplicationMenu applicationMenu)
    {
        super(name);
        this.applicationMenu = applicationMenu;
    }

    public ApplicationMenu getApplicationMenu()
    {
        return applicationMenu;
    }

    public void setApplicationMenu(ApplicationMenu applicationMenu)
    {
        this.applicationMenu = applicationMenu;
    }
}
