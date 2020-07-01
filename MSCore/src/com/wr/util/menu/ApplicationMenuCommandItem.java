package com.wr.util.menu;

public class ApplicationMenuCommandItem extends ApplicationMenuItem
{
    private IMenuItemCommand command;

    public ApplicationMenuCommandItem(String name, IMenuItemCommand command)
    {
        super(name);
        this.command = command;
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
