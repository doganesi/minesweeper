package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;
import com.wr.util.scanner.NonBlockingScanner;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class BoardProxy implements IBoard
{
    public static final int PORT_NUMBER = 50006;

    protected Board actualBoard;
    private transient List<IBoardActionListener> actionListeners = new ArrayList<>();
    protected  boolean needsSendUpdate;

    public BoardProxy()
    {
    }

    protected void createTubes()
    {
        Thread sendReceiveThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Socket socket = createSocket();
                    PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);
                    NonBlockingScanner receiver = new NonBlockingScanner(socket.getInputStream());

                    while(true)
                    {
                        synchronized (this)
                        {
                            if (needsSendUpdate)
                            {
                                String boardInfo = Board.saveBoardToString(actualBoard);
                                sender.println(boardInfo);
                                needsSendUpdate = false;
                            }
                            String infoFromPeer = receiver.getUserInput();
                            if (infoFromPeer != null)
                            {
                                actualBoard = Board.loadBoardFromString(infoFromPeer);
                                SwingUtilities.invokeLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        alertListeners(null, -1, -1);
                                    }
                                });
                            }
                            if (actualBoard != null && actualBoard.getGameState() != GameState.RUNNING && !needsSendUpdate)
                            {
                                break;
                            }
                        }

                    }
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        });
        sendReceiveThread.setDaemon(true);
        sendReceiveThread.start();
    }

    protected abstract Socket createSocket() throws IOException;

    @Override
    public int getXTiles()
    {
        return actualBoard.getXTiles();
    }

    @Override
    public int getYTiles()
    {
        return actualBoard.getYTiles();
    }

    @Override
    public void addActionListener(IBoardActionListener listener)
    {
        actionListeners.add(listener);
    }

    protected void alertListeners(TileOperation tileOperation, int x, int y)
    {
        for (IBoardActionListener actionListener : actionListeners)
        {
            actionListener.refresh(tileOperation, x, y);
        }
    }

    @Override
    public void performTileOperation(TileOperation tileOperation, int tileX, int tileY)
    {
        synchronized (this)
        {
            actualBoard.performTileOperation(tileOperation, tileX, tileY);
            needsSendUpdate = true;
        }
        alertListeners(tileOperation, tileX, tileY);
    }

    @Override
    public Tile getTile(int x, int y)
    {
        return actualBoard.getTile(x, y);
    }

    @Override
    public GameState getGameState()
    {
        return actualBoard.getGameState();
    }

    @Override
    public int getElapsedSeconds()
    {
        return actualBoard.getElapsedSeconds();
    }

    @Override
    public int getNumRemainingFlags()
    {
        return actualBoard.getNumRemainingFlags();
    }
}
