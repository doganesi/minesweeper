package com.wr.minesweeper;

public class BorderType
{
    public static final BorderType SINGLE = new BorderType('┌','┐', '└', '┘', '─', '|');
    public static final BorderType DOUBLE = new BorderType('╔','╗', '╚', '╝', '═', '║');

    private char upperLeft;
    private char upperRight;
    private char horizontal;
    private char lowerLeft;
    private char lowerRight;
    private char vertical;

    public BorderType(char universalChar)
    {
        this(universalChar, universalChar, universalChar, universalChar, universalChar, universalChar);
    }

    public BorderType(char upperLeft, char upperRight, char lowerLeft, char lowerRight, char horizontal, char vertical)
    {
        this.upperLeft  = upperLeft;
        this.upperRight = upperRight;
        this.horizontal = horizontal;
        this.lowerLeft  = lowerLeft;
        this.lowerRight = lowerRight;
        this.vertical   = vertical;
    }

    public char getUpperLeft()
    {
        return upperLeft;
    }

    public char getUpperRight()
    {
        return upperRight;
    }

    public char getHorizontal()
    {
        return horizontal;
    }

    public char getLowerLeft()
    {
        return lowerLeft;
    }

    public char getLowerRight()
    {
        return lowerRight;
    }

    public char getVertical()
    {
        return vertical;
    }
}
