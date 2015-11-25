package com.example.mathgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class GameEMH extends Activity {

	private Math_Game2 mathGame;
	private String[] equations;
	private Button bTrue, bFalse;
	private TextView[] displayEqs;
	private boolean[] checkEq;
	private long time = 0;
	private int questions = 0;
	private Chronometer chrono;
	private int strikes;
	private String difficulty;
	private String[] scores;
	private double highScore;
	private double currentScore;
	private FileOutputStream out;
	private boolean newHighScore;
	private double scoreTime;
	private int scorePenalty;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Bundle extras = getIntent().getExtras();
		mathGame = new Math_Game2(extras.getBooleanArray("check"),extras.getIntArray("limits"));
		difficulty = extras.getString("difficulty");
		newHighScore = false;
		startUpDisplay();
		startUpBTrue();
		startUpBFalse();
		startChrono();	
		startUpFont();
	}
	
	public void startUpFont(){
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf");
		for (int i = 0; i < displayEqs.length; i++) {
			displayEqs[i].setTypeface(crayon_crumble);
			displayEqs[i].setTextColor(Color.WHITE);
		}
		bFalse.setTypeface(crayon_crumble);
		bFalse.setTextColor(Color.WHITE);
		bTrue.setTypeface(crayon_crumble);
		bTrue.setTextColor(Color.WHITE);
		chrono.setTypeface(crayon_crumble);
		chrono.setTextColor(Color.WHITE);
	}
	
	//when the back button is pressed on the phone
	@Override
	public void onBackPressed() {
		stopChrono();
		displayEqs[1].setText("");
        displayEqs[2].setText("");
        displayEqs[3].setText("");
        displayEqs[4].setText("");
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
            	startChrono();
                dialog.cancel();
                displayEqs[1].setText(equations[0]);
                displayEqs[2].setText(equations[1]);
                displayEqs[3].setText(equations[2]);
                displayEqs[4].setText(equations[3]);
            }
        });
        box.show();
	}

	public void addingNewEqs() {
		String[] n = mathGame.getAnEquation();
		questions++;
		displayEqs[0].setText(equations[0]);
	
		for (int i = 0; i < equations.length - 1; i++) {										
			equations[i] = equations[i + 1];
			checkEq[i] = checkEq[i + 1];
			displayEqs[i + 1].setText(equations[i]);
		}// end for loop
		
		equations[equations.length - 1] = n[0];
		checkEq[checkEq.length - 1] = ((n[1].trim().equals("0"))? false : true);
		displayEqs[displayEqs.length - 1].setText(n[0]);
				
	}// end addingNewEqs method

	public void startUpDisplay() {

		int size = 4;
		displayEqs = new TextView[size + 1];
		displayEqs[0] = (TextView) findViewById(R.id.viewLastEquation_EMH);
		displayEqs[1] = (TextView) findViewById(R.id.viewEquation1_EMH);
		displayEqs[2] = (TextView) findViewById(R.id.viewEquation2_EMH);
		displayEqs[3] = (TextView) findViewById(R.id.viewEquation3_EMH);
		displayEqs[4] = (TextView) findViewById(R.id.viewEquation4_EMH);
		image = (ImageView) findViewById(R.id.imageView_EMH);
		
		equations = new String[size];
		checkEq = new boolean[size];

		for (int i = 0; i < size; i++) {
			String[] n = mathGame.getAnEquation();
			equations[i] = n[0];
			checkEq[i] = ((n[1].trim().equals("0")) ? false : true);
			displayEqs[i + 1].setText(n[0]);
		}// end for loop
	}// end startUpDisplay method

	public void startUpBTrue() { // setting button true
		bTrue = (Button) findViewById(R.id.buttonTrue_EMH);
		bTrue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!checkEq[0]) {
					strikes++;
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
				} else {
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
				}
				addingNewEqs();
				if(questions == 20){
					stopChrono();
					scoreTime = calTime();
					scorePenalty = calPenalty();
					calTotalScore();
					saveScore();
					if(newHighScore)
						newHighScorePopUp();
					else
						endGamePopUp();
				}
				if(questions == 17){
					displayEqs[4].setText("");
				}
				if(questions == 18){
					displayEqs[3].setText("");
					displayEqs[4].setText("");
				}
				if(questions == 19){
					displayEqs[2].setText("");
					displayEqs[3].setText("");
					displayEqs[4].setText("");
				}
				if(questions == 20){
					displayEqs[1].setText("");
					displayEqs[2].setText("");
					displayEqs[3].setText("");
					displayEqs[4].setText("");
				}
			}// end method onCLick
		});// end of method setOnClickListner from class Button
	} // end of public method set up true button

	public void startUpBFalse() {// setting button false
		bFalse = (Button) findViewById(R.id.buttonFalse_EMH);
		bFalse.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (checkEq[0]) {
					strikes++;
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
				} else {
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
				}
				addingNewEqs();
				if(questions == 20){
					stopChrono();
					scoreTime = calTime();
					scorePenalty = calPenalty();
					calTotalScore();
					saveScore();
					if(newHighScore)
						newHighScorePopUp();
					else
						endGamePopUp();
				}
				if(questions == 17){
					displayEqs[4].setText("");
				}
				if(questions == 18){
					displayEqs[3].setText("");
					displayEqs[4].setText("");
				}
				if(questions == 19){
					displayEqs[2].setText("");
					displayEqs[3].setText("");
					displayEqs[4].setText("");
				}
				if(questions == 20){
					displayEqs[1].setText("");
					displayEqs[2].setText("");
					displayEqs[3].setText("");
					displayEqs[4].setText("");
				}
			}// end method onCLick
		});// end of method setOnClickListner from class Button
	} // end of public method set up true button
	 
	public void startChrono(){
		chrono = (Chronometer)findViewById(R.id.timerEMH);
		chrono.setBase(SystemClock.elapsedRealtime()+time);
		chrono.start();
	}

	public void stopChrono(){
		time = chrono.getBase() - SystemClock.elapsedRealtime();
		chrono.stop();
	}
	//calculates the time
	public double calTime(){
		double result;
		result= (SystemClock.elapsedRealtime() - chrono.getBase());
		double endResult = (double)result/1000;
		
		return endResult;
	}
	//calculates the penalty according to the strikes
	public int calPenalty(){
		int penalty = (strikes * 5);
		return penalty;
	}
	//calcs the total score
	public String calTotalScore(){
		double totalScore = scoreTime + scorePenalty;
		//formats the total score 3 numbers after the dot
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		nf.setMaximumFractionDigits(3);
		String output = nf.format(totalScore);
		currentScore = Double.parseDouble(output);
		return output;
	}//end calTotalScore
	
	public void endGamePopUp(){
			Builder dialogbox = new AlertDialog.Builder(this);
			dialogbox.setCancelable(false);
			dialogbox.setMessage((20 - strikes) + " right out of 20, " + strikes + " wrong, in " + scoreTime + 
					" seconds + " + scorePenalty + " penalty = " + calTotalScore() + " seconds." + 
					"\n Do you want to continue playing?");
			dialogbox.setNegativeButton("no", new DialogInterface.OnClickListener() {
					
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent startPlayOptions = new Intent("com.example.mathgame.PLAYOPTIONS");
					startActivity(startPlayOptions);
					finish();
				}
			}); 
			dialogbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent startGameEMH = getIntent();
					startActivity(startGameEMH);
					finish();
				}
			});		
			dialogbox.show();
	}//end endGamePopUp method
	
	public boolean fileExistance(String fname){
	       File file = getBaseContext().getFileStreamPath(fname);
	       if(file.exists()){
	           return true;
	       }
	       else{
	           return false;
	       }    
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
	
	public void saveScore(){
		try {
			if(fileExistance("scores")){
				retrieveScores();
				if(difficulty.equals("easy")){
					if(scores[0].equals("#")){
						scores[0] = currentScore + "";
						writeToFile();
					}
					else{
						highScore = Double.parseDouble(scores[0]);
						if(currentScore < highScore){
		            		scores[0] = currentScore + "";
		            		newHighScore = true;
		            		writeToFile();
		            	}
					}
	            }
				else if(difficulty.equals("meduim")){
					if(scores[1].equals("#")){
						scores[1] = currentScore + "";
						writeToFile();
					}
					else{
						highScore = Double.parseDouble(scores[1]);
						if(currentScore < highScore){
		            		scores[1] = currentScore + "";
		            		newHighScore = true;
		            		writeToFile();
		            	}
					}
				}
				else{
					if(scores[2].equals("#")){
						scores[2] = currentScore + "";
						writeToFile();
					}
					else{
						highScore = Double.parseDouble(scores[2]);
						if(currentScore < highScore){
		            		scores[2] = currentScore + "";
		            		newHighScore = true;
		            		writeToFile();
		            	}
					}
				}
			}
			else{
				if(difficulty.equals("easy")){
					scores = new String[4];
	            	scores[0] = currentScore + "";
	            	scores[1] = "#";
	            	scores[2] = "#";
	            	scores[3] = "#";
	            	writeToFile();
	            }
				else if(difficulty.equals("meduim")){
					scores = new String[4];
	            	scores[0] = "#";
	            	scores[1] = currentScore + "";
	            	scores[2] = "#";
	            	scores[3] = "#";
	            	writeToFile();
				}
				else{
					scores = new String[4];
	            	scores[0] = "#";
	            	scores[1] = "#";
	            	scores[2] = currentScore + "";
	            	scores[3] = "#";
	            	writeToFile();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void newHighScorePopUp(){
		Builder dialogbox = new AlertDialog.Builder(this);
		dialogbox.setCancelable(false);
		dialogbox.setMessage("Congratulations, you just got a new high score!!!!\n" + (20 - strikes) + 
				" right, " + strikes + " wrong, in " + scoreTime + " seconds + " + scorePenalty + 
				" penalty = " + calTotalScore()+ " seconds."+ "\n Do you want to continue playing?");
		dialogbox.setNegativeButton("no", new DialogInterface.OnClickListener() {
				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}); 
		dialogbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent startGameEMH = getIntent();
				startActivity(startGameEMH);
				finish();
			}
		});		
		dialogbox.show();
		if(!MainMenu.checkSound()){
			Song.startVictory();
		}
	}
}