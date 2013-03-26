package com.udav.chesstimer;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button buttonFirstPlayer;
	private Button buttonSecondPlayer;
	
	private TextView tvFirstPlayer;
	private TextView tvSecondPlayer;
	private TextView subTvFirstPlayer, subTvSecondPlayer;
	private TextView firstPlName, secondPlName;
	
	private MyTimer firstTimer, secondTimer;
	
	private long gameTime = 0;
	
	private int typeTimer;
	private int limitTime;
	
	private SharedPreferences sp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        buttonFirstPlayer = (Button)findViewById(R.id.buttonFirstPlayer);
        buttonSecondPlayer = (Button)findViewById(R.id.buttonSecondPlayer);
        
        tvFirstPlayer = (TextView)findViewById(R.id.tvFirstPlayer);
        tvSecondPlayer = (TextView)findViewById(R.id.tvSecondPlayer);
        
        subTvFirstPlayer = (TextView)findViewById(R.id.subTvFirstPl);
        subTvSecondPlayer = (TextView)findViewById(R.id.subTvSecondPl);
        
        firstPlName = (TextView)findViewById(R.id.firstPlName);
        secondPlName = (TextView)findViewById(R.id.secondPlName);
        
        buttonFirstPlayer.setOnClickListener(this);
        buttonSecondPlayer.setOnClickListener(this);
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

	@Override
	protected void onResume() {
		String time = sp.getString("timePref", "10");
		gameTime = Long.parseLong(time)*60000;
		
		firstPlName.setText(sp.getString("pl1", "Player 1"));
		secondPlName.setText(sp.getString("pl2", "Player 2"));
		
		typeTimer = Integer.parseInt(sp.getString("listPrefTimerType", "0"));
		limitTime = Integer.parseInt(sp.getString("limitTime", "5"));
		
		if (firstTimer != null) firstTimer.stop();
		if (secondTimer != null) secondTimer.stop();
		firstTimer = null;
		secondTimer = null;
		
		long milliseconds = Long.parseLong(time) * 60000; 
		tvFirstPlayer.setText(MyTimer.millisecondToTime(milliseconds));
		tvSecondPlayer.setText(MyTimer.millisecondToTime(milliseconds));
		buttonFirstPlayer.setEnabled(true);
		buttonSecondPlayer.setEnabled(true);
		
		super.onResume();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        menu.findItem(R.id.menu_settings).setIntent(new Intent(this, PrefActivity.class));
		menu.findItem(R.id.menu_exit).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				System.exit(0);
				return false;
			}
		});
        return true;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonFirstPlayer:
				buttonFirstPlayer.setEnabled(false);
				buttonSecondPlayer.setEnabled(true);
				if (secondTimer != null) secondTimer.stop();
				
				if (firstTimer == null) {
					firstTimer = new MyTimer(tvSecondPlayer, subTvSecondPlayer, 
							gameTime, typeTimer, limitTime, 
							secondPlName.getText().toString(), getApplicationContext());
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
					secondTimer = new MyTimer(tvFirstPlayer, subTvFirstPlayer, 
							gameTime, typeTimer, limitTime, 
							firstPlName.getText().toString(), getApplicationContext());
				}else
					if (!secondTimer.getEnd()) {
						if (firstTimer != null) secondTimer.proceed(firstTimer.getTime());
						else secondTimer.proceed(gameTime);
					} else buttonFirstPlayer.setEnabled(false);
				break;
		}
	}

	
	
}
