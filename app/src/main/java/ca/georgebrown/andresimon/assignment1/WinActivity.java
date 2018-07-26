package ca.georgebrown.andresimon.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class WinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        final Button btnWin = (Button) findViewById(R.id.btnWin);
        btnWin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mainMenu();
            }
        });
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
