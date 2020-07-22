package com.wr.minesweeper;

public class Difficulty
{
    public static final Difficulty EASY = new Difficulty("Easy",0.1, 8, 10, 8, 10);
    public static final Difficulty MEDIUM = new Difficulty("Medium",0.1, 10, 17, 10, 18);
    public static final Difficulty HARD = new Difficulty("Hard", 0.1, 17, 20, 18, 20);
    public static final Difficulty EXTREME = new Difficulty("Extreme",0.1, 20, 23, 20, 25);
    public static final Difficulty IMPOSSIBLE = new Difficulty("Impossible",0.3, 23, 99, 25, 99);

    public static final Difficulty[] DIFFICULTY_LEVELS = new Difficulty[] {EASY, MEDIUM, HARD, EXTREME, IMPOSSIBLE};
                                                         //                  0      1     2      3           4

    private double bombRatio;
    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;
    private String name;

    public Difficulty (String name, double bombRatio, int minWidth, int maxWidth, int minHeight, int maxHeight)
    {
        this.bombRatio = bombRatio;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.name = name;
    }

    public double getBombRatio()
    {
        return bombRatio;
    }

    public int getMinWidth()
    {
        return minWidth;
    }

    public int getMaxWidth()
    {
        return maxWidth;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
