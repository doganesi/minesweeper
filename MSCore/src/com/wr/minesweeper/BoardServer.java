package com.wr.minesweeper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BoardServer extends BoardProxy
{
    public BoardServer(Difficulty difficulty) throws IOException
    {
        actualBoard = new Board(difficulty);
        needsSendUpdate = true;
        createTubes();
    }

    @Override
    protected Socket createSocket() throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        return serverSocket.accept();
    }
}
