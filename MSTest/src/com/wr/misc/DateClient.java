package com.wr.misc;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class DateClient
{
    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Socket socket = new Socket(args[0], 50006);
        Scanner in = new Scanner(socket.getInputStream());
        System.out.println("Server response: ");
        while (in.hasNextLine())
        {
            System.out.println(in.nextLine());
        }

    }
}
