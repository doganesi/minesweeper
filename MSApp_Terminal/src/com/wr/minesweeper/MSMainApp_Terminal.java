package com.wr.minesweeper;

import com.wr.util.menu.*;

public class MSMainApp_Terminal extends MSMainApp_Abstract
{
    public MSMainApp_Terminal()
    {

    }

    public void start()
    {
        TerminalMenuUtil.showMenu(mainMenu);
    }

    @Override
    public void loadLevel(Difficulty difficulty)
    {
        TerminalMSGame newGame = new TerminalMSGame(difficulty);
        newGame.startGame();
    }
}
