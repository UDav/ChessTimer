package com.udav.chesstimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MyTimer {
	private final int INTERVAL = 100;
	private TextView tv, subTv;
	private long milliseconds = 0, oldTime;
	private CountDownTimer timer, subTimer;
	private boolean end = false;
	private String name;
	private long enemyTime;
	private int moveCounter = 0;
	private Context context;
	
	public static final String TEXT_KEY = "text";
	
	public static final int TIMER_SIMPLE = 0;
	public static final int TIMER_WITH_DELAY = 1; // delay = 5sec
	public static final int TIMER_ADAGIO = 2; // if limit not use - return timer state 
	public static final int TIMER_FISHER = 3; // increse timer on limit value
	private int typeTimer;
	
	private long limit;
	private final int LIMIT_TIME;
	
	/**
	 * Create MyTimer
	 * @param tv1 - main TextView for display time
	 * @param tv2 - slave TextView for display limit time 
	 * @param gameTime - set game time
	 * @param typeTimer - selected timer type
	 * @param limitTime - set limit time
	 * @param playerName - set Player name
	 * @param context - application context
	 */
	public MyTimer(TextView tv1, TextView tv2, long gameTime, int typeTimer, int limitTime,String playerName, Context context) {
		this.tv = tv1;
		this.subTv = tv2;
		milliseconds = gameTime;
		this.name = playerName;
		enemyTime = gameTime;
		this.context = context;
		this.typeTimer = typeTimer;
		LIMIT_TIME = limitTime*1000;
		
		this.proceed(gameTime);
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
		limit = LIMIT_TIME;
		switch (typeTimer) {
			case TIMER_SIMPLE:
				timer = createTimer().start();
				break;
			case TIMER_WITH_DELAY:
				//launch subTimer
				//when subtimer finish - launch main timer
				subTimer = new CountDownTimer(limit, INTERVAL) {
					@Override
					public void onTick(long millisUntilFinished) {
						limit = millisUntilFinished;
						subTv.setText(millisecondToTime(limit));
					}
					@Override
					public void onFinish() {
						subTv.setText("");
						timer = createTimer().start();	
					}
				};
				subTimer.start();
				break;
			case TIMER_ADAGIO:
				//save current time
				//launch main timer
				oldTime = milliseconds;
				timer = createTimer().start();
				break;
			case TIMER_FISHER:
				timer = createTimer().start();
				break;
		}
		
	}
	
	public void stop(){
		if (timer != null){
			timer.cancel();
			timer = null;
		}
		subTv.setText("");
		switch (typeTimer) {
			case TIMER_SIMPLE:
				break;
			case TIMER_WITH_DELAY:
				if (subTimer != null) {
					subTimer.cancel();
					subTimer = null;
				}
				break;
			case TIMER_ADAGIO:
				if (limit>=0) {
					milliseconds = oldTime;
					tv.setText(millisecondToTime(milliseconds));
				}
				break;
			case TIMER_FISHER:
				//add to milliseconds limit
				milliseconds += LIMIT_TIME;
				tv.setText(millisecondToTime(milliseconds));
				break;
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
				if (typeTimer == TIMER_ADAGIO) {
					// count limit time
					limit -= INTERVAL;
					if (limit>=0) subTv.setText(millisecondToTime(limit));
				}
				milliseconds = millisUntilFinished;
				tv.setText(millisecondToTime(milliseconds));
			}
			
			@Override
			public void onFinish() {
				end = true;
				
				String text = name+" lose!\nСделано ходов: "+moveCounter+
				"\nОсталось времени у противника "+millisecondToTime(enemyTime);
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
