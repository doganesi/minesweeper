package com.wr.minesweeper;

import com.wr.util.IBoardActionListener;
import com.wr.util.RandUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Board implements Serializable
{
    private static String DELIMITER = "|";

    public enum GameState {RUNNING, OVER_WIN, OVER_LOSE}
    public enum TileOperation {FLAG_TOGGLE, OPEN}

    private int xTiles;
    private int yTiles;
    private int numMines;
    private int numFlags;
    private Date startDate;
    private Tile[][] tiles;
    private GameState gameState = GameState.RUNNING;
    private transient List<IBoardActionListener> actionListeners = new ArrayList<>();

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

    private Board()
    {

    }

    public static String saveBoardToString(Board board)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(board.xTiles).append(DELIMITER);
        sb.append(board.yTiles).append(DELIMITER);
        sb.append(board.numMines).append(DELIMITER);
        sb.append(board.numFlags).append(DELIMITER);
        if (board.startDate != null)
        {
            long elapsedMillis = new Date().getTime() - board.startDate.getTime();
            sb.append(elapsedMillis);
        }
        sb.append(DELIMITER);
        sb.append(board.gameState).append(DELIMITER);

        for (int x = 0; x < board.xTiles; x++)
        {
            for (int y = 0; y < board.yTiles; y++)
            {
                Tile tile = board.tiles[x][y];
                sb.append(Tile.saveTileToString(tile));
                sb.append(DELIMITER);
            }
        }

        return sb.toString();
    }
    public static Board loadBoardFromString(String string)
    {
        String[] savedTokens = string.split("\\"+DELIMITER);
        Board loadedBoard = new Board();

        loadedBoard.xTiles = Integer.parseInt(savedTokens[0]);
        loadedBoard.yTiles = Integer.parseInt(savedTokens[1]);
        loadedBoard.numMines = Integer.parseInt(savedTokens[2]);
        loadedBoard.numFlags = Integer.parseInt(savedTokens[3]);
        String elapsedString = savedTokens[4];
        if (elapsedString.length() > 0)
        {
            Long elapsedMillis = Long.parseLong(savedTokens[4]);
            Date savedDate = new Date(System.currentTimeMillis() - elapsedMillis);
            loadedBoard.startDate = savedDate;
        }
        loadedBoard.gameState = GameState.valueOf(savedTokens[5]);

        int counter = 6;
        loadedBoard.tiles = new Tile[loadedBoard.xTiles][loadedBoard.yTiles];

        for (int x = 0; x < loadedBoard.xTiles; x++)
        {
            for (int y = 0; y < loadedBoard.yTiles; y++)
            {
                String tokenString = savedTokens[counter++];
                Tile tile = Tile.loadTileFromString(tokenString, loadedBoard);
                loadedBoard.tiles[x][y] = tile;

            }
        }


        return loadedBoard;
    }

    public void initListeners()
    {
        this.actionListeners = new ArrayList<>();
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
