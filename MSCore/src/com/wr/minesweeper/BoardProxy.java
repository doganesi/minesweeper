package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;
import com.wr.util.scanner.NonBlockingScanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class BoardProxy implements IBoard
{
    public static final int PORT_NUMBER = 50006;

    protected Board actualBoard;
    private transient List<IBoardActionListener> actionListeners = new ArrayList<>();
    protected boolean isRunning  = true;
    protected boolean needsSendUpdate = false;
    private PrintWriter sender;
    private NonBlockingScanner receiver;
    private Thread sendReceiveThread;


    public BoardProxy()
    {
    }

    protected void createTubes()
    {
        sendReceiveThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Socket socket = createSocket();
                    sender = new PrintWriter(socket.getOutputStream(), true);
                    receiver = new NonBlockingScanner(socket.getInputStream());

                    while(isRunning)
                    {
                        if (needsSendUpdate)
                        {
                            String boardInfo = Board.saveBoardToString(actualBoard);
                            System.out.println(BoardProxy.this.getClass().getName() + " [Sending] - " + boardInfo);
                            sender.println(boardInfo);
                            needsSendUpdate = false;
                        }
                        String infoFromPeer = receiver.getUserInput();
                        if (infoFromPeer != null)
                        {
                            System.out.println(BoardProxy.this.getClass().getName() + " [Receiving] - " + infoFromPeer);
                            actualBoard = Board.loadBoardFromString(infoFromPeer);
                            alertListeners(null, -1, -1);
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
        actualBoard.performTileOperation(tileOperation, tileX, tileY);
        alertListeners(tileOperation, tileX, tileY);
        needsSendUpdate = true;
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
