package com.csc309.crowdcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);
    }

    public void showSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


    //Brads'S DANK ASS test CODE.
    public void showLevel(View view)
    {
        Intent intent = new Intent(this, PlayLevelActivity.class);
        startActivity(intent);
    }

    //Leaving this typo as is. The integrity of the app depends on it.
    public void showDeubg(View view)
    {
        Intent intent = new Intent(this, Debug.class);
        startActivity(intent);
    }
}
