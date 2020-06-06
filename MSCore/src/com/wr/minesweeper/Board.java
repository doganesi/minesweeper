package com.wr.minesweeper;

import java.util.Random;

public class Board
{
    enum Level
    {EASY, MEDIUM, HARD, IMPOSSIBLE}

    private int xTiles;
    private int yTiles;
    private int numMines;
    private String name;
    private Tile[][] tiles;

//    todo add color attribute

    public Board(int xTiles, int yTiles, int numMines)
    {
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        this.numMines = numMines;
        this.tiles = new Tile[xTiles][yTiles];

        Random rand = new Random(System.currentTimeMillis());

        int counter = 0;
        while(counter < numMines)
        {
            for (int x = 0; x < xTiles; x++)
            {
                for (int y = 0; y < yTiles; y++)
                {
                    if (tiles[x][y] != null && tiles[x][y].isHasMine())
                    {
                        continue;
                    }

                    double mineProbability = numMines * 1.0 / getNumTiles();
                    double randomNumber = rand.nextDouble();
                    boolean hasMine = false;
                    if (randomNumber <= mineProbability && counter < numMines)
                    {
                        hasMine = true;
                        counter++;
                    }
                    tiles[x][y] = new Tile(x, y, hasMine);
                }
            }
            System.out.println("Loop");

        }
        System.out.println("Expected: " + numMines + " Placed: " + counter);

    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Level getLevel()
    {
        double percentMines = numMines * 100.0 / getNumTiles();
        if (percentMines < 15)
        {
            return Level.EASY;
        } else if (percentMines < 25)
        {
            return Level.MEDIUM;
        } else if (percentMines < 40)
        {
            return Level.HARD;
        }
        return Level.IMPOSSIBLE;
    }

    public int getNumTiles()
    {
        return xTiles * yTiles;
    }


    public int getXTiles()
    {
        return xTiles;
    }

    public void setXTiles(int xTiles)
    {
        this.xTiles = xTiles;
    }

    public int getYTiles()
    {
        return yTiles;
    }

    public void setYTiles(int yTiles)
    {
        this.yTiles = yTiles;
    }

    public int getNumMines()
    {
        return numMines;
    }

    public void setNumMines(int numMines)
    {
        this.numMines = numMines;
    }

}
