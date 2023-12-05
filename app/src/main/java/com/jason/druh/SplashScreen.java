//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class SplashScreen extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------

    /*ONCREATE METHOD*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //------------------------------------------------------------------------------------------

        /*TIMER METHODS*/

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        };//END TASK DEFINITION

        Timer time = new Timer();
        time.schedule(task, 2000);

        //------------------------------------------------------------------------------------------

    } // END ONCREATE

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------