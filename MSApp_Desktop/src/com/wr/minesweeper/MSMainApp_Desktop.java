package com.wr.minesweeper;

import com.wr.util.file.DesktopFileHandler;
import com.wr.util.file.IFileHandler;
import com.wr.util.menu.DesktopMenuUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class MSMainApp_Desktop extends MSMainApp_Abstract
{

    protected JFrame mainFrame = new JFrame("Minesweeper");
    protected JMenu runningGameMenu = new JMenu("Running Game");
    private IBoard activeBoard;

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
        menuBar.add(runningGameMenu);
        JMenuItem saveBoardMenuItem;
        saveBoardMenuItem = new JMenuItem("Save Board as File");
        saveBoardMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DesktopFileHandler.handle(mainFrame, "minesweeper", true, new IFileHandler()
                {
                    @Override
                    public void handleFile(File file)
                    {
                        // Serializing current board
                        BoardUtil.saveBoard(activeBoard, file);
                    }
                });


            }
        });
        runningGameMenu.add(saveBoardMenuItem);
        runningGameMenu.setEnabled(false);
        mainFrame.setVisible(true);
    }

    @Override
    protected String getServerIP()
    {
        return JOptionPane.showInputDialog(mainFrame, "Enter Game Host's IP Address");
    }

    @Override
    protected void reportError(String error)
    {
        JOptionPane.showMessageDialog(mainFrame, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void loadBoard(IBoard board)
    {
        DesktopMSGame newGame = new DesktopMSGame(board);
        activeBoard = board;
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(newGame.getBoardComponent(), BorderLayout.CENTER);
        mainFrame.getContentPane().add(newGame.getScoreComponent(), BorderLayout.NORTH);
        mainFrame.pack();
        runningGameMenu.setEnabled(true);
    }

    @Override
    public void loadGame()
    {
        DesktopFileHandler.handle(mainFrame, "minesweeper", false, new IFileHandler() {
            @Override
            public void handleFile(File file)
            {
                IBoard loadedBoard = BoardUtil.loadBoard(file);
                if (loadedBoard != null)
                {
                    loadBoard(loadedBoard);
                }
            }
        });
    }
}
