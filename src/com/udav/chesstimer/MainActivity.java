package com.udav.chesstimer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button buttonFirstPlayer;
	private Button buttonSecondPlayer;
	
	private TextView tvFirstPlayer;
	private TextView tvSecondPlayer;
	
	private MyTimer firstTimer, secondTimer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        buttonFirstPlayer = (Button)findViewById(R.id.buttonFirstPlayer);
        buttonSecondPlayer = (Button)findViewById(R.id.buttonSecondPlayer);
        
        tvFirstPlayer = (TextView)findViewById(R.id.tvFirstPlayer);
        tvSecondPlayer = (TextView)findViewById(R.id.tvSecondPlayer);
        
        buttonFirstPlayer.setOnClickListener(this);
        buttonSecondPlayer.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings :
				Intent intent = new Intent();
				intent.setClass(this, SettingsActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_exit :
				System.exit(0);
				break;
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonFirstPlayer:
				System.out.println("first");
				buttonFirstPlayer.setEnabled(false);
				buttonSecondPlayer.setEnabled(true);
				if (secondTimer != null) secondTimer.stop();
				
				if (firstTimer == null) {
					firstTimer = new MyTimer(tvSecondPlayer);
					//firstTimer.setTime(10000);
				}else
					if (!firstTimer.getEnd()){
						firstTimer.proceed();
					} else buttonSecondPlayer.setEnabled(false);
				
				break;
			case R.id.buttonSecondPlayer:
				System.out.println("second");
				buttonFirstPlayer.setEnabled(true);
				buttonSecondPlayer.setEnabled(false);
				
				if (firstTimer != null) firstTimer.stop();
				
				if (secondTimer == null) {
					secondTimer = new MyTimer(tvFirstPlayer);
					//secondTimer.setTime(10000);
				}else
					if (!secondTimer.getEnd()) {
						secondTimer.proceed();
					} else buttonFirstPlayer.setEnabled(false);
						
				
				break;
		}
		
	}
}
