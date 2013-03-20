package com.udav.chesstimer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity implements OnClickListener{

	private Button buttonAgain;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Bundle extras = getIntent().getExtras();
        
        buttonAgain = (Button)findViewById(R.id.buttonAgain);
        buttonAgain.setOnClickListener(this);
        TextView tv = (TextView)findViewById(R.id.result);
        tv.setText(extras.getString(MyTimer.TEXT_KEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_end, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.buttonAgain:
			/*Intent intent = new Intent(getApplicationContext(), MainActivity.class); 
			startActivity(intent);*/
			this.finish();
			break;
		}
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
