package com.udav.chesstimer;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
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
        
        Cursor mCursor = DBHelper.getDataFromDB(getApplicationContext());
        mCursor.moveToFirst();
        
        /*GridView statistic = (GridView)findViewById(R.id.statisticGridView);
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, mCursor, new String[]{"id"}, new int[]{android.R.id.text1});
        statistic.setAdapter(sca);*/
        
        for (int i=0; i<mCursor.getCount(); i++){
        	System.out.println(mCursor.getInt(0));
        	System.out.println(mCursor.getString(mCursor.getColumnIndex("win_pl")));
        	System.out.println(mCursor.getString(2));
        	System.out.println(mCursor.getString(3));
        	System.out.println(mCursor.getString(4));
        	System.out.println(mCursor.getString(5));
        	System.out.println("----------------------------");
        	mCursor.moveToNext();
        }
        
        // заполнять GridView данными из базы
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
			this.finish();
			break;
		}
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
