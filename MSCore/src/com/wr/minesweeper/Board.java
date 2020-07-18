package com.wr.minesweeper;

import com.wr.util.RandUtil;

public class Board
{
    public enum GameState {RUNNING, OVER, DEBUG}
    public enum TileOperation {FLAG_TOGGLE, OPEN}

    private int xTiles;
    private int yTiles;
    private int numMines;
    private String name;
    private Tile[][] tiles;
    private GameState gameState = GameState.RUNNING;

    public Board(Difficulty difficulty)
    {
        this.xTiles = difficulty.getMinWidth() + RandUtil.nextInt(difficulty.getMaxWidth()-difficulty.getMinWidth());
        this.yTiles = difficulty.getMinHeight() + RandUtil.nextInt(difficulty.getMaxHeight()-difficulty.getMinHeight());
        this.numMines = (int) (difficulty.getBombRatio()*getXTiles()*getYTiles());
        initBoard();
    }

    public Board(int xTiles, int yTiles, int numMines)
    {
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        this.numMines = numMines;
        initBoard();
    }

    private void initBoard()
    {
        this.tiles = new Tile[xTiles][yTiles];
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
                    double randomNumber = RandUtil.nextDouble();
                    boolean hasMine = false;
                    if (randomNumber <= mineProbability && counter < numMines)
                    {
                        hasMine = true;
                        counter++;
                    }
                    tiles[x][y] = new Tile(this, x, y, hasMine);
                }
            }
//            System.out.println("Loop");

        }
        calcNumMinesAround();
        System.out.println("Expected: " + numMines + " Placed: " + counter);
    }

    public Tile getTile(int x, int y)
    {
        return tiles[x][y];
    }

    public boolean performTileOperation(TileOperation tileOperation, int x, int y)
    {
        Tile selectedTile = getTile(x,y);
        if (tileOperation == TileOperation.FLAG_TOGGLE)
        {
            return selectedTile.toggleFlag();
        }


        return false;
    }

    public boolean openTile(int x, int y)
    {
        Tile selectedTile = getTile(x,y);
        return false;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }



    public Difficulty getDifficulty()
    {

        double percentMines = numMines * 100.0 / getNumTiles();
        for (int i = 0; i < Difficulty.DIFFICULTY_LEVELS.length; i++)
        {
            Difficulty dif = Difficulty.DIFFICULTY_LEVELS[i];
            if (percentMines < dif.getBombRatio())
            {
                return dif;
            }
        }
        if (percentMines < 10)
        {
            return Difficulty.EASY;
        }
        else if (percentMines < 15)
        {
            return Difficulty.MEDIUM;
        }
        else if (percentMines < 20)
        {
            return Difficulty.HARD;
        }
        else if (percentMines < 25)
        {
            return Difficulty.EXTREME;
        }
        else
        {
            return Difficulty.IMPOSSIBLE;
        }
    }

    public void calcNumMinesAround()
    {
        for (int x = 0; x < xTiles; x++)
        {
            for (int y = 0; y < yTiles; y++)
            {
                Tile cTile = tiles[x][y];
                int numMinesAround = 0;

                numMinesAround += checkNeighbor(x - 1, y -1);
                numMinesAround += checkNeighbor(x, y -1);
                numMinesAround += checkNeighbor(x + 1, y - 1);
                numMinesAround += checkNeighbor(x + 1, y);
                numMinesAround += checkNeighbor(x - 1 , y);
                numMinesAround += checkNeighbor(x - 1, y + 1);
                numMinesAround += checkNeighbor(x, y + 1);
                numMinesAround += checkNeighbor(x + 1, y + 1);

                cTile.setNumMinesAround(numMinesAround);

            }
        }
    }

    public int checkNeighbor(int x, int y)
    {
        if (x < 0 || y < 0 || x >= getXTiles() || y >= getYTiles())
        {
            return 0;
        }

        Tile neighborTile = tiles[x][y];
        if (neighborTile.isHasMine())
        {
            return 1;
        }
        else
        {
            return 0;
        }
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

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }
}
