package com.wr.util.scanner;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NonBlockingScanner
{
    private BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    private boolean closed = false;
    private Thread queueThread;
    private Scanner scanner = new Scanner(System.in);

    public NonBlockingScanner()
    {
        queueThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                {
                    String userInput = scanner.next();
                    inputQueue.add(userInput);
                }
                closed = true;
            }
        });
        queueThread.setDaemon(true);
        queueThread.start();
    }

    public String getUserInput()
    {
        if (closed || inputQueue.isEmpty())
        {
            return null;
        }
        try
        {
            return inputQueue.poll(500, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            return null;
        }
    }

    public void close()
    {
        if (queueThread != null)
        {
            queueThread.interrupt();
            queueThread = null;
        }
    }
}
