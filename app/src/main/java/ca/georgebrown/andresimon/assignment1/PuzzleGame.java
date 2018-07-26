package ca.georgebrown.andresimon.assignment1;

import static java.lang.Math.abs;

/**
 * Created by andresimon on 2017-02-28.
 *
 * Based on Mikhael Goikhman code, founded in http://migo.sixbit.org/puzzles/fifteen/
 */

public class PuzzleGame
{
    private int[] numbers;
    private int empty_x;
    private int empty_y;

    private int scrambleSeed = 173;  // [0 .. 299]

    public PuzzleGame()
    {
        numbers = new int[16];
    }

    public int getNumbersAtIndex( int index )
    {
        return numbers[index];
    }

    public int getEmpty_x()
    {
        return empty_x;
    }

    public int getEmpty_y()
    {
        return empty_y;
    }

    private int getSign(int d)
    {
        if (d < 0) return -1;
        if (d > 0) return 1;
        return 0;
    }

    private int getNumberAtPosition( int y, int x )
    {
        int i = (y - 1) * 4 + x - 1;
        return numbers[i];
    }

    private void setNumberAtPosition( int y, int x, int n)
    {
        int i = (y - 1) * 4 + x - 1;
        numbers[i] = n;
    }

    public boolean processMove( int y, int x, boolean playing )
    {
        int step_x = empty_x - x;
        int step_y = empty_y - y;

        if ( playing && ( abs(step_x) > 1 || abs(step_y) > 1) ) { return false;}

        step_x = getSign(step_x);
        step_y = getSign(step_y);
        if (step_x != 0 && step_y != 0) { return false; }

        while (empty_x != x || empty_y != y)
        {
            setNumberAtPosition(empty_y, empty_x, getNumberAtPosition(empty_y - step_y, empty_x - step_x));
            empty_x -= step_x;
            empty_y -= step_y;
        }
        setNumberAtPosition(empty_y, empty_x, 0);
        return true;
    }

    public void resetNumbers()
    {
        int n;

        for (int y = 1; y <= 4; y++)
        {
            for (int x = 1; x <= 4; x++)
            {
                n = (y - 1) * 4 + x;
                if (n == 16) n = 0;
                setNumberAtPosition(y, x, n);
            }
        }
        empty_x = 4;
        empty_y = 4;
    }

    public void shuffleNumbers()
    {
        int rand_x = 4;
        int rand_y = 4;

        scrambleSeed = (scrambleSeed + 95) % 300;
        for (int r = 0; r < 250 + scrambleSeed; r++)
        {
            if (r % 2 == 0)
            {
                rand_x = empty_x;
                rand_y = (rand_y + (r * (r + 1) % 3)) % 4 + 1;
            }
            else
            {
                rand_x = (rand_x + (r * (r + 1) % 3)) % 4 + 1;
                rand_y = empty_y;
            }

            processMove(rand_y, rand_x, false);
        }
    }

}

