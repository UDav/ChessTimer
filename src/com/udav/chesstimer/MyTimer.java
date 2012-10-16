package com.udav.chesstimer;

import android.widget.TextView;

public class MyTimer implements Runnable {
	
	private TextView tv;
	private boolean stoped = false;
	private long milisecond = 10000;
	
	public MyTimer(TextView tv) {
		this.tv = tv;
	}
	
	@Override
	public void run() {
		while (!stoped) {
			milisecond -= 100;
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
	}
	
	private String milisecondToTime() {
		long second = milisecond/1000;
		long min = second/60;
		second %= 60;
		long hour = min/60;
		min %=60; 
		return hour+":"+min+":"+second;
	}
	
	public void start(){
		stoped = false;
	}
	
	public void stop(){
		stoped = true;
	}

}
