package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;

import javax.swing.*;
import java.awt.*;

public class
ScoreComponent extends JPanel
{
    private Board board;
    private JLabel flagCounter;
    private JLabel timeElapsed;
    private Thread waitThread;

    public ScoreComponent(Board board)
    {
        this.board = board;
        this.flagCounter = new JLabel("099");
        this.timeElapsed = new JLabel();
        setLayout(new BorderLayout());
        add(flagCounter, BorderLayout.WEST);
        add(timeElapsed, BorderLayout.EAST);

        waitThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                while (board.getGameState() == Board.GameState.RUNNING)
                {
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            timeElapsed.setText(String.valueOf(board.getElapsedSeconds()));
                            flagCounter.setText(String.valueOf(board.getNumRemainingFlags()));
                        }
                    });
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        waitThread.setDaemon(true);
        waitThread.start();
    }
}
