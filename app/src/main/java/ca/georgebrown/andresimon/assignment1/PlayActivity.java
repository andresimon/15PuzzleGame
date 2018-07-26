package ca.georgebrown.andresimon.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends Activity
{
    private ArrayList<Integer> myButtonImageList = new ArrayList<>();
    private ImageButton[] buttons;

    private PuzzleGame puzzleGame;

    private int moveCount = 0;
    private TextView moveCountText;

    private void showTitle()
    {
        try
        {
            ((View) findViewById(android.R.id.title).getParent())
                    .setVisibility(View.VISIBLE);
        }
        catch (Exception e)
        {
        }
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        final Button btnShuffle = (Button) findViewById(R.id.btnShuffle);
        btnShuffle.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                puzzleGame.shuffleNumbers();
                moveCount = 0;
                updateBoard();
            }
        });

        final Button btnCheat = (Button) findViewById(R.id.btnCheat);
        btnCheat.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                puzzleGame.resetNumbers();
                updateBoard();
            }
        });

        final Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               mainMenu();
            }
        });

        moveCountText = (TextView) findViewById(R.id.moveCountText);

        buttons = findButtons();
        createButtonImageList();
        createButtonListeners();

        puzzleGame = new PuzzleGame();
        puzzleGame.resetNumbers();
        puzzleGame.shuffleNumbers();

        updateBoard();

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();


        return super.onTouchEvent(motionEvent);

    }

    void createButtonImageList()
    {
        myButtonImageList.clear();

        for ( int i = 0; i <= 15; i++)
            myButtonImageList.add(PlayActivity.this.getResources().getIdentifier("b"+i, "drawable", PlayActivity.this.getPackageName()));
    }

    void updateBoard()
    {
        int n;
        for (int i = 0; i <=15; i++)
        {
            buttons[i].setImageResource(myButtonImageList.get(puzzleGame.getNumbersAtIndex(i)));
        }

        moveCountText.setText(""+moveCount);
    }

    public ImageButton[] findButtons()
    {
        ImageButton[] b = new ImageButton[16];

        b[0] = (ImageButton) findViewById(R.id.b0);
        b[1] = (ImageButton) findViewById(R.id.b1);
        b[2] = (ImageButton) findViewById(R.id.b2);
        b[3] = (ImageButton) findViewById(R.id.b3);
        b[4] = (ImageButton) findViewById(R.id.b4);
        b[5] = (ImageButton) findViewById(R.id.b5);
        b[6] = (ImageButton) findViewById(R.id.b6);
        b[7] = (ImageButton) findViewById(R.id.b7);
        b[8] = (ImageButton) findViewById(R.id.b8);
        b[9] = (ImageButton) findViewById(R.id.b9);
        b[10] = (ImageButton) findViewById(R.id.b10);
        b[11] = (ImageButton) findViewById(R.id.b11);
        b[12] = (ImageButton) findViewById(R.id.b12);
        b[13] = (ImageButton) findViewById(R.id.b13);
        b[14] = (ImageButton) findViewById(R.id.b14);
        b[15] = (ImageButton) findViewById(R.id.b15);

        return b;
    }

    private void processClick( int y, int x )
    {
        if (x != puzzleGame.getEmpty_x() && y != puzzleGame.getEmpty_y())
        {
            return;
        }
        if (x == puzzleGame.getEmpty_x() && y == puzzleGame.getEmpty_y())
        {
            return;
        }
        if ( puzzleGame.processMove(y, x, true) ) {moveCount++;}

        updateBoard();

        if ( checkVictory() )
        {
            Intent intent = new Intent(this,WinActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void createButtonListeners()
    {
        int n;

        for (int y = 1; y <= 4; y++)
        {
            for (int x = 1; x <= 4; x++)
            {
                n = (y - 1) * 4 + x;

                final int finalY = y;
                final int finalX = x;
                buttons[n-1].setOnClickListener(new View.OnClickListener() {
                                                    public void onClick(View v) {
                                                        processClick(finalY, finalX); } } );
            }
        }

    }

    private boolean checkVictory()
    {
        if (moveCount < 0) { return false; }

        for (int i = 0; i < 16; i++) {
            if (i == 13 && puzzleGame.getNumbersAtIndex(i) == 15 && puzzleGame.getNumbersAtIndex(i + 1) == 14)
            {
               // TODO LostActivity - Impossible to solve !
                return false;
            }
            if (puzzleGame.getNumbersAtIndex(i) != i + 1 && i != 15)
            {
                return false;
            }
        }

        return true;
    }

    private void mainMenu()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }
}
