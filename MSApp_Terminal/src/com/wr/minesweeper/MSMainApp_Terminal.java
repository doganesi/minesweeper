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
    public void loadBoard(Board board)
    {
        TerminalMSGame newGame = new TerminalMSGame(board);
        newGame.startGame();
    }
}
