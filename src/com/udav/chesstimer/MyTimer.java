package com.udav.chesstimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MyTimer {
	private final int INTERVAL = 100;
	private TextView tv;
	private long milliseconds = 0;
	private CountDownTimer timer;
	private boolean end = false;
	private String name;
	private long enemyTime;
	private int moveCounter = 0;
	private Context context;
	
	public static final String TEXT_KEY = "text";
	
	public MyTimer(TextView tv1, long gameTime, String name, Context context) {
		this.tv = tv1;
		milliseconds = gameTime;
		this.name = name;
		enemyTime = gameTime;
		this.context = context;
		
		timer = createTimer().start();
	}
	
	/**
	 * Convert milliseconds to string hh:mm:ss.ms
	 * @return
	 */
	private String millisecondToTime(long timeInMillisecond) {
		long second = timeInMillisecond/1000;
		long showMiliseconds = timeInMillisecond % 1000;
		long min = second/60;
		second %= 60;
		long hour = min/60;
		min %=60; 
		
		String resultStr = (String)((hour<10?"0"+hour:hour)+":"+
				(min<10?"0"+min:min)+":"+
				(second<10?"0"+second:second)+"."+
				(showMiliseconds/100));
		return resultStr;
	}
	
	public void proceed(long enemyTime){
		this.enemyTime = enemyTime; 
		timer = createTimer().start();
	}
	
	public void stop(){
		if (timer != null){
			timer.cancel();
			timer = null;
		}
	}
	
	public boolean getEnd() {
		return end;
	}
	
	public long getTime() {
		return milliseconds;
	}
	
	private CountDownTimer createTimer() {
		moveCounter++;
		return new CountDownTimer(milliseconds, INTERVAL) {
			@Override
			public void onTick(long millisUntilFinished) {
				milliseconds = millisUntilFinished;
				tv.setText(millisecondToTime(milliseconds));
			}
			
			@Override
			public void onFinish() {
				end = true;
				//tv.setText(name+" lose!");
				
				String text = name+" lose!\nСделано ходов: "+moveCounter+
				"\nОставшееся время противника "+millisecondToTime(enemyTime);
				Intent intent = new Intent(context, EndActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle extras = new Bundle();
				extras.putString(TEXT_KEY, text);
				intent.putExtras(extras);
				context.startActivity(intent);
			}
		};
	}

}
