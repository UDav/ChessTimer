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
	
	private long gameTime = 0;
	
	public static final int SET_TIME_ID = 1;
	public static final String SET_TIME_KEY = "set_time"; // для заворачивания/разворачивания intentoв	
	
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED)
			return;
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SET_TIME_ID:
				Bundle extras = data.getExtras();
				gameTime = extras.getLong(SET_TIME_KEY);// забираем значение из SettingsActivity
				break;
			default:
				break;
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

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
				startActivityForResult(intent, SET_TIME_ID);
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
				buttonFirstPlayer.setEnabled(false);
				buttonSecondPlayer.setEnabled(true);
				if (secondTimer != null) secondTimer.stop();
				
				if (firstTimer == null) {
					firstTimer = new MyTimer(tvSecondPlayer, gameTime, "Player 2", getApplicationContext());
				}else
					if (!firstTimer.getEnd()){
						if (secondTimer != null) firstTimer.proceed(secondTimer.getTime());
						else firstTimer.proceed(gameTime);
					} else buttonSecondPlayer.setEnabled(false);
				
				break;
			case R.id.buttonSecondPlayer:
				buttonFirstPlayer.setEnabled(true);
				buttonSecondPlayer.setEnabled(false);
				
				if (firstTimer != null) firstTimer.stop();
				
				if (secondTimer == null) {
					secondTimer = new MyTimer(tvFirstPlayer, gameTime, "Player 1", getApplicationContext());
				}else
					if (!secondTimer.getEnd()) {
						if (firstTimer != null) secondTimer.proceed(firstTimer.getTime());
						else secondTimer.proceed(gameTime);
					} else buttonFirstPlayer.setEnabled(false);
						
				
				break;
		}
	}
}
