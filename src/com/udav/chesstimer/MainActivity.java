package com.udav.chesstimer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button buttonFirstPlayer;
	private Button buttonSecondPlayer;
	
	private TextView tvFirstPlayer;
	private TextView tvSecondPlayer;
	
	private Thread th1, th2;
	private MyTimer firstTimer, secondTimer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonFirstPlayer:
				System.out.println("first");
				buttonFirstPlayer.setEnabled(false);
				buttonSecondPlayer.setEnabled(true);
				if (secondTimer != null) secondTimer.stop();
				
				if (firstTimer == null) {
					firstTimer = new MyTimer(tvSecondPlayer);
					firstTimer.start();
					th1 = new Thread(firstTimer);
					th1.start();
				}
				
				if (th1.getState() == Thread.State.TERMINATED) {
					firstTimer.start();
					th1 = new Thread(firstTimer);
					th1.start();
				}
				
				break;
			case R.id.buttonSecondPlayer:
				System.out.println("second");
				buttonFirstPlayer.setEnabled(true);
				buttonSecondPlayer.setEnabled(false);
				
				if (firstTimer != null) firstTimer.stop();
				
				if (secondTimer == null) {
					secondTimer = new MyTimer(tvFirstPlayer);
					secondTimer.start();
					th2 = new Thread(secondTimer);
					th2.start();
				}
				
				if (th2.getState() == Thread.State.TERMINATED) {
					secondTimer.start();
					th2 = new Thread(secondTimer);
					th2.start();
				}
				
				break;
		}
		
	}
}
