package com.example.mathgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameChallenge extends Activity{
	private int questionsDone;
	private String[] equations;
	private boolean[] equationCheck;
	private Button bTrue, bFalse;
	private TextView[] text;
	private CountDownTimer countDown;
	private TextView[] striks;
	private TextView textViewCountDown;
	private boolean[] strikCheck;
	private Math_Game2 mathGame;
	private long s1;
	private FileOutputStream out;
	private String[] scores;
	private boolean newHighScore;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle gameChallengeBundle) {
		super.onCreate(gameChallengeBundle);
		this.setContentView(R.layout.game_challenge);
		questionsDone = 0;
		newHighScore = false;
		startUPMathGame();
		startUpTextView();
		startUpButtonFalse();
		startUpButtonTrue();
		startUpStriks();
		startUpFont();
	}
	
	public void startUpFont(){                                                                                
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf"); 
		bTrue.setTypeface(crayon_crumble);     // set the font for all the buttons to crayon_crumble
		bTrue.setTextColor(Color.WHITE);
		bFalse.setTypeface(crayon_crumble);
		bFalse.setTextColor(Color.WHITE);
		textViewCountDown.setTypeface(crayon_crumble);
		textViewCountDown.setTextColor(Color.WHITE);
		for (int i = 0; i < text.length; i++) {
			text[i].setTypeface(crayon_crumble);
			text[i].setTextColor(Color.WHITE);
		}
	}
	
	//when the back button is pressed on the phone
	@Override
	public void onBackPressed() {
		if(countDown != null)
			countDown.cancel();
		text[1].setText("");
		text[2].setText("");
		text[3].setText("");
		text[4].setText("");
		Builder box = new AlertDialog.Builder(this);
        box.setMessage("Are you sure you want to exit?");
        box.setCancelable(false);
        box.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	Intent startPlayOptions = new Intent("com.example.mathgame.PLAYOPTIONS");
				startActivity(startPlayOptions);
				finish();
            }
        });
        box.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	if(countDown != null){
            		countDown = new CountDown(s1,100);
                	countDown.start();
                    dialog.cancel();
                    text[1].setText(equations[0]);
            		text[2].setText(equations[1]);
            		text[3].setText(equations[2]);
            		text[4].setText(equations[3]);
            	}
            	else{
            		text[1].setText(equations[0]);
            		text[2].setText(equations[1]);
            		text[3].setText(equations[2]);
            		text[4].setText(equations[3]);
            	}
            }
        });
       box.show();
	}
	//start up method
	public void startUpButtonTrue(){
		bTrue = (Button) findViewById(R.id.buttonTrue_Challenge);
		bTrue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!equationCheck[0]){
					try {
						addStrike();
					} catch (Exception e) {
						e.printStackTrace();
					}
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
				} else {
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
				}
			addNextEquation();
			}
		});//end of method clickListener
	}//end of method StartUpButtonTrue
	public void startUpButtonFalse(){
		bFalse = (Button) findViewById(R.id.buttonFalse_Challenge);
		bFalse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(equationCheck[0]){
					try {
						addStrike();
					} catch (Exception e) {
						e.printStackTrace();
					}
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
				} else {
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
				}
			addNextEquation();
			}
		});//end of method clickListener
	}//end of method StartUpButtonFalse
	public void startUpTextView(){
		int size = 4;
		
		text = new TextView[size + 1];
		text[0] = (TextView) findViewById(R.id.viewLastEquation_Challenge);
		text[1] = (TextView) findViewById(R.id.viewEquation1_Challenge);

		text[2] = (TextView) findViewById(R.id.viewEquation2_Challenge);
		text[3] = (TextView) findViewById(R.id.viewEquation3_Challenge);
		text[4] = (TextView) findViewById(R.id.viewEquation4_Challenge);
		
		image = (ImageView) findViewById(R.id.imageView_Challenge);
		
		equations = new String[size];
		equationCheck = new boolean[size];

		for (int i = 0; i < size; i++) {
			String[] n = mathGame.getAnEquation();
			equations[i] = n[0];
			equationCheck[i] = ((n[1].trim().equals("0"))? false : true);
			text[i + 1].setText(n[0]); 
		}//end for loop
		
		textViewCountDown = (TextView) findViewById(R.id.countDownChallenge);
		textViewCountDown.setText("5.00");
		
	}//end method StartUpTextView
	public void startUpStriks(){
		striks = new TextView[3];		
		strikCheck = new boolean[3];
		
		striks[0] = (TextView) findViewById(R.id.textViewStrick1);
		striks[1] = (TextView) findViewById(R.id.textViewStrick2);
		striks[2] = (TextView) findViewById(R.id.textViewStrick3);
		
		for (int i = 0; i < striks.length; i++) {
			strikCheck[i] = true;
		}//end for loop
	}//end method startUpStriks
	
	public void startUPMathGame(){
		boolean[] check = {true,true,true,true};
		int[] num = {0,10,0,10,0,10,0,10};
		mathGame = new Math_Game2(check,num);
	}//end method startUpMathGame
	
	public void startUpCountDown(){
		if(strikCheck[2]){
			countDown = new CountDown(5000,50);
			countDown.start();
		}	
	}
	
	public void startUpPopMenu(){
		
		textViewCountDown.setText("0.00");
		Builder dialogBox = new AlertDialog.Builder(this);
		dialogBox.setTitle("Game Over");
		dialogBox.setMessage("You solved " + (questionsDone - 3)
				+ " questions\n Do you want to continue? ");
		dialogBox.setCancelable(false);
		dialogBox.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							writeToFile();
						} catch (Exception e) {
							e.printStackTrace();
						}
						Intent startPlayOptions = new Intent("com.example.mathgame.PLAYOPTIONS");
						startActivity(startPlayOptions);
						finish();
					}
				});
		dialogBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			//restarts game challenge when user want to continue
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					writeToFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Intent startGameChallenge = getIntent();
				startActivity(startGameChallenge);
				finish();
			}
		});
		dialogBox.show();	
	}
	//making dialog box  
	
	//other public methods
	public void addNextEquation(){
		String[] n = mathGame.getAnEquation();
		mathGame.increaseDificult(questionsDone++);
		
		text[0].setText(equations[0]);
		
		for (int i = 0; i < equations.length - 1; i++) {// move all the equation by 1 up
			equations[i] = equations[i + 1];
			equationCheck[i] = equationCheck[i + 1];
			text[i + 1].setText(equations[i]);
		}// end for loop
		
		equations[equations.length - 1] = n[0];
		equationCheck[equationCheck.length - 1] = ((n[1].trim().equals("0"))? false : true);
		text[text.length - 1].setText(n[0]);
		if(countDown != null)
			countDown.cancel();
		startUpCountDown();
	}
	public void addStrike() throws Exception{
		
		for (int i = 0; i < strikCheck.length; i++) {
			if(strikCheck[i]){
				
				strikCheck[i] = false;
				striks[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.red_apple));
				if(i == 2){
					saveScore();
					if(newHighScore)
						newHighScorePopUp();
					else
						startUpPopMenu();
				}
				break;
			}// end if
		}//end for loop
	}//end method strike
	
	//inner class
	public class CountDown extends CountDownTimer{

		public CountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		
		@Override
		public void onFinish() {
			try {
				addStrike();
			} catch (Exception e) {
				e.printStackTrace();
			}
			addNextEquation();
			image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			s1=millisUntilFinished;
			textViewCountDown.setText("" + String.format("%.2f", millisUntilFinished/1000.0  ));
		}
	}
	
	public boolean fileExistance(String fname){
	       File file = getBaseContext().getFileStreamPath(fname);
	       if(file.exists()){
	           return true;
	       }
	       else{
	           return false;
	       }    
	}
	
	public void retrieveScores() throws Exception{
		FileInputStream in = openFileInput("scores");
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader bufferedReader = new BufferedReader(isr);
		String receiveString = "";
		StringBuilder stringBuilder = new StringBuilder();
		
		while ( (receiveString = bufferedReader.readLine()) != null ) {
            stringBuilder.append(receiveString);
        }
        in.close();
        scores = stringBuilder.toString().split(" ");
	}
	
	public void writeToFile(){
		try{
			String output = scores[0] + " " + scores[1] + " " + scores[2]
				+ " " + scores[3];
			out = openFileOutput("scores", Context.MODE_PRIVATE);
			out.write(output.getBytes());
			out.close();
		}catch(Exception e){
		}
	}
	
	public void saveScore() throws Exception{
		if(fileExistance("scores")){
			retrieveScores();
			if(scores[3].equals("#")){
				scores[3] = (questionsDone - 2) + "";
			}else{
				int highScore = Integer.parseInt(scores[3]);
				if((questionsDone - 2) > highScore){
					scores[3] = (questionsDone - 2) + "";
					newHighScore = true;
				}
			}
		}else{
			scores = new String[4];
			scores[0] = "#";
        	scores[1] = "#";
        	scores[2] = "#";
        	scores[3] = (questionsDone - 2) + "";
		}
		questionsDone++;
	}
	
	public void newHighScorePopUp(){
		textViewCountDown.setText("0.00");
		Builder dialogBox = new AlertDialog.Builder(this);
		dialogBox.setTitle("Game Over");
		dialogBox.setMessage("Congratulations, you just got a new high score in Challenge mode!!!!\n" +
				"You solved " + (questionsDone - 3)
				+ " questions\n Do you want to continue? ");
		dialogBox.setCancelable(false);
		dialogBox.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							writeToFile();
						} catch (Exception e) {
							e.printStackTrace();
						}
						finish();
					}
				});
		dialogBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			//restarts game challenge when user want to continue
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					writeToFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Intent startGameChallenge = getIntent();
				startActivity(startGameChallenge);
				finish();
			}
		});
		dialogBox.show();
		if(!MainMenu.checkSound()){
			Song.startVictory();
		}
	}
}