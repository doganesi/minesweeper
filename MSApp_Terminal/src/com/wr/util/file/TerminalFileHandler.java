package com.wr.util.file;

import java.io.File;
import java.util.Scanner;

public class TerminalFileHandler
{
    public static void handleFile(String prompt, String extension, boolean isSave,IFileHandler fileHandler)
    {
        System.out.print(prompt + " (*." + extension + "): ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.next();
        if (!userInput.endsWith(".minesweeper"))
        {
            userInput = userInput + ".minesweeper";
        }

        File file = new File(userInput);
        if (file.isDirectory())
        {
            System.out.println("Invalid file path");
            return;
        }

        if (!isSave && !file.exists())
        {
            System.out.println("File not found");
            return;
        }

        System.out.println("File path: " + file.getAbsolutePath());
        fileHandler.handleFile(file);

    }
}
