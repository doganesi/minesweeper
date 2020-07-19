package com.wr.minesweeper;

import java.util.Scanner;

public class TerminalMSGame
{
    private Board board;

    public TerminalMSGame(Difficulty difficulty)
    {
        this.board = new Board(difficulty);
    }

    public void startGame()
    {
        Scanner scanner = new Scanner(System.in);
        while(board.getGameState() == Board.GameState.RUNNING)
        {
            TerminalMSUtil.printBoard(board);
            System.out.println("To open a tile enter tile coordinates in the form: x,y");
            System.out.println("To flag/unflag a tile enter tile coordinates in the form: fx,y");
            System.out.println("To open a tile/s enter tile coordinates in the form: ox,y");
            System.out.println("type back to return to menu");
            System.out.print("Enter command: ");
            String userInput = scanner.next();

            // process command
            if (userInput.equalsIgnoreCase("back"))
            {
                return;
            }
            else if (userInput.startsWith("f"))
            {
                String coordinatesString = userInput.substring(1);
                performTileOperation(Board.TileOperation.FLAG_TOGGLE, coordinatesString);
            }
            else if(userInput.startsWith("o"))
            {
                String coordinatesString = userInput.substring(1);
                performTileOperation(Board.TileOperation.OPEN, coordinatesString);
            }
        }

        TerminalMSUtil.printBoard(board);
        if (board.getGameState() == Board.GameState.OVER_WIN)
        {
            System.out.println("Congratulations");
        }
        else
        {
            System.out.println("Game lost ;(");
        }

    }

    public void performTileOperation(Board.TileOperation tileOperation, String coordinatesString)
    {
        System.out.println("coordinatesString: " + coordinatesString);
        String[] coordinatesTokens = coordinatesString.split(",");
        if (coordinatesTokens.length != 2)
        {
            System.out.println("Invalid input");
            return;
        }

        String xCoordinateString = coordinatesTokens[0];
        String yCoordinateString = coordinatesTokens[1];

        int xCoordinateInt = 0;
        int yCoordinateInt = 0;
        try
        {
            xCoordinateInt = Integer.parseInt(xCoordinateString);
            yCoordinateInt = Integer.parseInt(yCoordinateString);
        }
        catch (NumberFormatException e)
        {
        }

        if (xCoordinateInt == 0 || xCoordinateInt > board.getXTiles() ||
                yCoordinateInt == 0 || yCoordinateInt > board.getYTiles())
        {
            System.out.println("Invalid Input");
            return;
        }

        System.out.println("(" + xCoordinateInt + ", " + yCoordinateInt + ")");

        board.performTileOperation(tileOperation, xCoordinateInt - 1,yCoordinateInt - 1);
    }

}
