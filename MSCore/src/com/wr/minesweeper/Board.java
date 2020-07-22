package com.wr.minesweeper;

import com.wr.util.RandUtil;

public class Board
{
    public enum GameState {RUNNING, OVER_WIN, OVER_LOSE}
    public enum TileOperation {FLAG_TOGGLE, OPEN}

    private int xTiles;
    private int yTiles;
    private int numMines;
    private String name;
    private Tile[][] tiles;
    private GameState gameState = GameState.RUNNING;

    public interface TileHandler
    {
        Tile handle(Tile currentTile, int x, int y, int maxX, int maxY);

    }

    public Board(Difficulty difficulty)
    {
        xTiles = difficulty.getMinWidth() + RandUtil.nextInt(difficulty.getMaxWidth()-difficulty.getMinWidth());
        yTiles = difficulty.getMinHeight() + RandUtil.nextInt(difficulty.getMaxHeight()-difficulty.getMinHeight());
        numMines = (int) (difficulty.getBombRatio()*getXTiles()*getYTiles());
        initBoard();
    }

    public Board(int xTiles, int yTiles, int numMines)
    {
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        this.numMines = numMines;
        initBoard();
    }

    private static void iterateTiles(Tile[][] arr, TileHandler handleTile)
    {
        int maxX = arr.length;
        int maxY = arr[0].length;
        for (int x = 0; x < maxX; x++)
        {
            for (int y = 0; y < maxY; y++)
            {
                arr[x][y] = handleTile.handle(arr[x][y], x, y, maxX - 1, maxY - 1);
            }
        }
    }

    private void initBoard()
    {
        tiles = new Tile[xTiles][yTiles];
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

    private void calcWin()
    {
        for (int x = 0; x < xTiles; x++)
        {
            for (int y = 0; y < yTiles; y++)
            {
                Tile cTile = tiles[x][y];
                if (cTile.getTileState() == Tile.State.CLOSED)
                {
                    return;
                }
                else if (cTile.getTileState() == Tile.State.FLAGGED && !cTile.isHasMine())
                {
                    return;
                }

            }
        }
        setGameState(GameState.OVER_WIN);
    }


    public void performTileOperation(TileOperation tileOperation, int x, int y)
    {
        Tile selectedTile = getTile(x,y);
        if (tileOperation == TileOperation.FLAG_TOGGLE)
        {
            selectedTile.toggleFlag();
        }
        else if (tileOperation == TileOperation.OPEN)
        {
            if (selectedTile.getTileState() == Tile.State.FLAGGED)
            {
                return;
            }

            if (selectedTile.isHasMine())
            {
                selectedTile.setTileState(Tile.State.OPEN);
                setGameState(GameState.OVER_LOSE);
                return;
            }
            openTile(x, y);
        }
        calcWin();
    }

    private void openTile(int x, int y)
    {
        if (x < 0 || y < 0 || x >= getXTiles() || y >= getYTiles())
        {
            return;
        }

        Tile selectedTile = getTile(x,y);
        if (selectedTile.getTileState() == Tile.State.OPEN || selectedTile.getTileState() == Tile.State.FLAGGED)
        {
            return;
        }

        selectedTile.setTileState(Tile.State.OPEN);
        if (selectedTile.getNumMinesAround() == 0)
        {
            openTile(x - 1, y -1);
            openTile(x, y -1);
            openTile(x + 1, y - 1);
            openTile(x + 1, y);
            openTile(x - 1 , y);
            openTile(x - 1, y + 1);
            openTile(x, y + 1);
            openTile(x + 1, y + 1);
        }
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
