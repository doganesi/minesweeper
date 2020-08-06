package com.wr.minesweeper;

import com.wr.util.menu.*;

import javax.swing.*;
import java.awt.*;

public class MSMainApp_Desktop extends MSMainApp_Abstract
{
    protected JFrame mainFrame = new JFrame("Minesweeper");

    public MSMainApp_Desktop()
    {

    }

    public void start()
    {
        mainFrame.setSize(new Dimension(550, 250));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);
        mainFrame.getContentPane().setLayout(new BorderLayout());
        menuBar.add(DesktopMenuUtil.getJMenu(mainFrame, null, mainMenu));

        mainFrame.setVisible(true);
    }

    @Override
    public void loadBoard(Board board)
    {
        DesktopMSGame newGame = new DesktopMSGame(board);
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(newGame.getBoardComponent(), BorderLayout.CENTER);
        mainFrame.getContentPane().add(newGame.getScoreComponent(), BorderLayout.NORTH);
        mainFrame.pack();
    }
}
