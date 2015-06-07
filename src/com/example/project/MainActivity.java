package com.example.project;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Game mGame;

	private Button mBoardButtons[];

	private TextView mInfoTextView;
	private TextView mHumanCount;
	private TextView mTieCount;
	private TextView mAndroidCount;

	private int mHumanCounter = 0;
	private int mTieCounter = 0;
	private int mAndroidCounter = 0;

	private boolean mUserFirst = true;
	private boolean mGameOver = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBoardButtons = new Button[mGame.getBoardSize()];
		mBoardButtons[0] = (Button) findViewById(R.id.one);
		mBoardButtons[1] = (Button) findViewById(R.id.two);
		mBoardButtons[2] = (Button) findViewById(R.id.three);
		mBoardButtons[3] = (Button) findViewById(R.id.four);
		mBoardButtons[4] = (Button) findViewById(R.id.five);
		mBoardButtons[5] = (Button) findViewById(R.id.six);
		mBoardButtons[6] = (Button) findViewById(R.id.seven);
		mBoardButtons[7] = (Button) findViewById(R.id.eight);
		mBoardButtons[8] = (Button) findViewById(R.id.nine);
		

		mInfoTextView = (TextView) findViewById(R.id.information);
		mHumanCount = (TextView) findViewById(R.id.humanCount);
		mTieCount = (TextView) findViewById(R.id.tiesCount);
		mAndroidCount = (TextView) findViewById(R.id.androidCount);

		mHumanCount.setText(Integer.toString(mHumanCounter));
		mTieCount.setText(Integer.toString(mTieCounter));
		mAndroidCount.setText(Integer.toString(mAndroidCounter));

		mGame = new Game();
		startNewGame();
		blink();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.newGame:
			startNewGame();
			break;
		case R.id.exitGame:
			MainActivity.this.finish();
			break;
		}
		return true;
	}


	private void startNewGame() {
		mGame.clearBoard();

		for (int i = 0; i < mBoardButtons.length; i++) {
			//mBoardButtons[i].setText(" ");
			mBoardButtons[i].setEnabled(true);
			mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
			mBoardButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));

		}
		if (mUserFirst) {
			mInfoTextView.setText(R.string.first_human);
			mUserFirst = false;
		} else {
			mInfoTextView.setText(R.string.turn_android);
			int move = mGame.getAndroidMove();
			setMove(mGame.PLAYER_ANDROID, move);
			mUserFirst = true;
		}
		mGameOver = false;
	}

	private class ButtonClickListener implements View.OnClickListener {

		int location;

		public ButtonClickListener(int location) {
			this.location = location;
		}

		public void onClick(View v) {

			if (!mGameOver) {
				if (mBoardButtons[location].isEnabled()) {

					setMove(mGame.PLAYER_USER, location);
					int winner = mGame.isWon();

					if (winner == 0) {
						mInfoTextView.setText(R.string.turn_android);
						int move = mGame.getAndroidMove();
						setMove(mGame.PLAYER_ANDROID, move);
						winner = mGame.isWon();
					}
					if (winner == 0) {
						mInfoTextView.setText(R.string.turn_human);
					} else if (winner == 1) {
						mInfoTextView.setText(R.string.result_tie);
						mTieCounter++;
						mTieCount.setText(Integer.toString(mTieCounter));
						mGameOver = true;
					} else if (winner == 2) {
						mInfoTextView.setText(R.string.result_human_wins);
						mHumanCounter++;
						mHumanCount.setText(Integer.toString(mHumanCounter));
						mGameOver = true;
					} else {
						mInfoTextView.setText(R.string.result_android_wins);
						mAndroidCounter++;
						mAndroidCount
								.setText(Integer.toString(mAndroidCounter));
						mGameOver = true;
					}
				}
			}
		}

	}

	private void setMove(char player, int location) {

		mGame.setMove(player, location);
		mBoardButtons[location].setEnabled(false);
		//mBoardButtons[location].setText(String.valueOf(player));
		
		if (player == mGame.PLAYER_USER) {
			mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
			//mBoardButtons[location].setTextColor(Color.GREEN);
		}
		else {
			mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
			//mBoardButtons[location].setTextColor(Color.RED);
		}
	}
	
	private void blink(){
	    final Handler handler = new Handler();
	    new Thread(new Runnable() {
	        @Override
	        public void run() {
	        int timeToBlink = 1000;    //in milissegunds
	        try{Thread.sleep(timeToBlink);}catch (Exception e) {}
	            handler.post(new Runnable() {
	                @Override
	                    public void run() {
	                    TextView txt = (TextView) findViewById(R.id.usage);
	                    if(txt.getVisibility() == View.VISIBLE){
	                        txt.setVisibility(View.INVISIBLE);
	                    }else{
	                        txt.setVisibility(View.VISIBLE);
	                    }
	                    blink();
	                }
	                });
	            }
	        }).start();
	}

}
