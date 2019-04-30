package com.example.thread39;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Handler pnjThreadHandler = new Handler()
    {
      // Alt+Insert - override

        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            // NB never put user-interface-altering code within a thread - use a handler
            EditText pnjText = (EditText) findViewById(R.id.pnj_text);
            pnjText.setText("Clicked!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // bespoke onClick method
    public void clickButton(View view)
    {

        Runnable r = new Runnable()
        {
            // by Alt+Insert - override
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                // to demonstrate, wait 10s, to simulate a lengthy calculation/query
                // could be making an API call in here, or similar
                long futureTime = System.currentTimeMillis() + 10000;
                while(System.currentTimeMillis() < futureTime)
                {
                    synchronized (this)    // prevent multiple threads from colliding
                    {
                        try
                        {
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e)
                        {

                        }
                    }
                }
                pnjThreadHandler.sendEmptyMessage(0);   // trigger handler (with nil parameter)
            }
        };

        Thread pnjThread = new Thread(r);       // use runnable with code defined above
        pnjThread.start();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
