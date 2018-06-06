package com.csc309.crowdcontrol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareScore();
            }
        });
        int data = getIntent().getExtras().getInt("Score");

        TextView val = (TextView) findViewById(R.id.scoreView);
        x = Integer.toString(data);
        val.setText("Score: " + x);


    }



    private void shareScore() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "New Highscore on Crowd Control!");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey scrub, check out my new highscore on Crowd Control. Try and beat it i dare ya :) " + x);
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
}

