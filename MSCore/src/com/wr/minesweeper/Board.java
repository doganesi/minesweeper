package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;
import com.wr.util.RandUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Board
{
    public enum GameState {RUNNING, OVER_WIN, OVER_LOSE}
    public enum TileOperation {FLAG_TOGGLE, OPEN}

    private int xTiles;
    private int yTiles;
    private int numMines;
    private int numFlags;
    private Date startDate;
    private String name;
    private Tile[][] tiles;
    private GameState gameState = GameState.RUNNING;
    private List<IBoardActionListener> actionListeners = new ArrayList<>();


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

    public void addActionListener(IBoardActionListener listener)
    {
        actionListeners.add(listener);
    }

    private void alertListeners(TileOperation tileOperation, int x, int y)
    {
        for (IBoardActionListener actionListener : actionListeners)
        {
            actionListener.refresh(tileOperation, x, y);
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
        if (startDate == null)
        {
            startDate = new Date();
        }
        Tile selectedTile = getTile(x,y);
        if (tileOperation == TileOperation.FLAG_TOGGLE)
        {
            if (selectedTile.getTileState() == Tile.State.CLOSED && getNumRemainingFlags() == 0)
            {
                return;
            }
            selectedTile.toggleFlag();
            calcNumFlags();

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
        alertListeners(tileOperation, x, y);
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

    public int getNumRemainingFlags()
    {
        return numMines - numFlags;
    }

    public void calcNumFlags()
    {
        numFlags = 0;
        for (int x = 0; x < xTiles; x++)
        {
            for (int y = 0; y < yTiles; y++)
            {
                Tile currentTile = tiles[x][y];
                if (currentTile.getTileState() == Tile.State.FLAGGED)
                {
                    numFlags++;
                }
            }
        }
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public int getElapsedSeconds()
    {
        if (startDate == null)
        {
            return 0;
        }

        Date currentDate = new Date();
        long elapsedMilliseconds = currentDate.getTime() - startDate.getTime();
        int elapsedSeconds = (int) (elapsedMilliseconds / 1000);
        return elapsedSeconds;
    }


    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }
}
