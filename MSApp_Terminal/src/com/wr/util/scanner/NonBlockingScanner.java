package com.wr.util.scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NonBlockingScanner
{
    private BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    private boolean closed = false;
    private Thread queueThread;


    public NonBlockingScanner()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        queueThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!closed)
                {
                    try
                    {
                        while (!br.ready())
                        {
                            if (closed)
                            {
                                return;
                            }
                            Thread.sleep(200);
                        }
                        String userInput = br.readLine();
                        inputQueue.add(userInput);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
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
        closed = true;
    }
}
