package com.udav.chesstimer;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyTimer {
	private final int INTERVAL = 100;
	private TextView tv;
	private long miliseconds = 100000;
	private CountDownTimer timer;
	private boolean end = false;
	
	public MyTimer(TextView tv1) {
		this.tv = tv1;
		timer = createTimer().start();
	}
	
	/**
	 * Convert milliseconds to string hh:mm:ss.ms
	 * @return
	 */
	private String milisecondToTime() {
		long second = miliseconds/1000;
		long showMiliseconds = miliseconds % 1000;
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
	
	public void setTime(long ms) {
		miliseconds = ms;
	}
	
	public void proceed(){
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
	
	private CountDownTimer createTimer() {
		return new CountDownTimer(miliseconds, INTERVAL) {
			@Override
			public void onTick(long millisUntilFinished) {
				miliseconds = millisUntilFinished;
				tv.setText(milisecondToTime());
			}
			
			@Override
			public void onFinish() {
				end = true;
				tv.setText("Player lose!");
			}
		};
	}

}
