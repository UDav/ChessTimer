package com.udav.chesstimer;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class MyTimer implements Runnable {
	
	private TextView tv;
	private boolean stoped = false;
	private long miliseconds = 10000;
	
	public MyTimer(TextView tv) {
		this.tv = tv;
	}
	
	@Override
	public void run() {
		while (!stoped && miliseconds > 0) {
			miliseconds -= 100;
			tv.post(new Runnable() {
				public void run() {
					tv.setText(milisecondToTime());
				}
			});
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (miliseconds == 0) {
			Intent intent = new Intent();
			Context context = tv.getContext();
			intent.setClass(context, EndActivity.class);
			context.startActivity(intent);
		}
	}
	
	private String milisecondToTime() {
		long second = miliseconds/1000;
		long showMiliseconds = miliseconds % 1000;
		long min = second/60;
		second %= 60;
		long hour = min/60;
		min %=60; 
		
		String resultStr = (String)((hour<10?"0"+hour:hour)+":"+
				(min<10?"0"+min:min)+":"+
				(second<10?"0"+second:second)+"."+//showMiliseconds;
				(showMiliseconds<10?"00"+showMiliseconds:showMiliseconds));
		return resultStr;
	}
	
	public void setTime(long ms) {
		miliseconds = ms;
	}
	
	public void start(){
		stoped = false;
	}
	
	public void stop(){
		stoped = true;
	}

}
