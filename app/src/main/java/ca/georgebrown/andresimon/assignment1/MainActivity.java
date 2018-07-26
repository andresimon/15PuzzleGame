package ca.georgebrown.andresimon.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button quit=  (Button) findViewById(R.id.quit);
        Button play = (Button) findViewById(R.id.play);
        ButtonListener buttonListener = new ButtonListener();
        quit.setOnClickListener(buttonListener);
        play.setOnClickListener(buttonListener);
    }

    private void startPlay()
    {
        Intent intent = new Intent(this,PlayActivity.class);
        startActivity(intent);
        finish();
    }

    private class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            String buttonTag = v.getTag().toString();
            if(buttonTag.equals("quit"))
                finish();
            else
            {
                startPlay();
            }
        }
    }








}
