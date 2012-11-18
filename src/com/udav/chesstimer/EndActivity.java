package com.udav.chesstimer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EndActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_end, menu);
        return true;
    }
}
