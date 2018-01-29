package fhooe.se.android.rezeptapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = p.getBoolean("PREFERENCE_FIRST_RUN", true);
        if(firstRun) {
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                //
            }

        }
        Intent i = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(i);
    }
}
