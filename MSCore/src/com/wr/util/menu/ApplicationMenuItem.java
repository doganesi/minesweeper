package com.wr.util.menu;

public class ApplicationMenuItem
{
    private String name;
    private IMenuItemCommand command;

    public ApplicationMenuItem(String name, IMenuItemCommand command)
    {
        this.name = name;
        this.command = command;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public IMenuItemCommand getCommand()
    {
        return command;
    }

    public void setCommand(IMenuItemCommand command)
    {
        this.command = command;
    }
}
