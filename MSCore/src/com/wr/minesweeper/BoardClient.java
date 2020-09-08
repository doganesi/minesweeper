package com.wr.minesweeper;

import java.io.IOException;
import java.net.Socket;

public class BoardClient extends BoardProxy
{
    protected String serverAddress;

    public BoardClient(String serverAddress) throws IOException
    {
        this.serverAddress = serverAddress;
        createTubes();
    }

    @Override
    protected Socket createSocket() throws IOException
    {
        return new Socket(serverAddress, PORT_NUMBER);
    }

    public boolean isReady()
    {
        return actualBoard != null;
    }
}
