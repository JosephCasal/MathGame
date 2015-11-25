package com.example.mathgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends Activity{

	private String scores;
	private TextView textScoreEasy;
	private TextView textScoreMeduim;
	private TextView textScoreHard;
	private TextView textScoreChallenge;
	private Button bDelete;
	private TextView[] texts; //pos0: title, pos1:easy, pos2:medium, pos3:hard, pos4: challenge
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		textScoreEasy = (TextView) findViewById(R.id.textViewScoreEasy);
		textScoreMeduim = (TextView) findViewById(R.id.textViewScoreMeduim);
		textScoreHard = (TextView) findViewById(R.id.textViewScoreHard);
		textScoreChallenge = (TextView) findViewById(R.id.textViewScoreChallenge);
		startUpButtonDelete();
		try {
			FileInputStream in = openFileInput("scores");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader bufferedReader = new BufferedReader(isr);
			String receiveString = "";
			StringBuilder stringBuilder = new StringBuilder();
			
			while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }
            in.close();
            
            scores = stringBuilder.toString();
            String[] tokens = scores.split(" ");
            textScoreEasy.setText((tokens[0].equals("#"))? "Empty": tokens[0]);
    		textScoreMeduim.setText((tokens[1].equals("#"))? "Empty": tokens[1]);
    		textScoreHard.setText((tokens[2].equals("#"))? "Empty": tokens[2]);
    		textScoreChallenge.setText((tokens[3].equals("#"))? "Empty": tokens[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		texts = new TextView[5];
		texts[0] = (TextView) findViewById(R.id.textViewHighScores);
		texts[1] = (TextView) findViewById(R.id.textViewEasy);
		texts[2] = (TextView) findViewById(R.id.textViewMeduim);
		texts[3] = (TextView) findViewById(R.id.textViewHard);
		texts[4] = (TextView) findViewById(R.id.textViewChallenge);
		startUpFont();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	public void startUpFont(){                                                                                
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf"); 
		bDelete.setTypeface(crayon_crumble);     // set the font for all the buttons to crayon_crumble
		bDelete.setTextColor(Color.WHITE);
		textScoreEasy.setTypeface(crayon_crumble);
		textScoreEasy.setTextColor(Color.WHITE);
		textScoreMeduim.setTypeface(crayon_crumble);
		textScoreMeduim.setTextColor(Color.WHITE);
		textScoreHard.setTypeface(crayon_crumble);
		textScoreHard.setTextColor(Color.WHITE);
		textScoreChallenge.setTypeface(crayon_crumble);
		textScoreChallenge.setTextColor(Color.WHITE);
		for (int i = 0; i < texts.length; i++) {
			texts[i].setTypeface(crayon_crumble);
			texts[i].setTextColor(Color.WHITE);
		}
	}
	
	public void startUpButtonDelete() {
		bDelete = (Button) findViewById(R.id.buttonDelete);
		bDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				deletePopUp();
			}
		});
	}
	
	public void deletePopUp(){
			Builder dialogbox = new AlertDialog.Builder(this);
			dialogbox.setCancelable(false);
			dialogbox.setMessage("Are you sure you want to delete the high scores?");
			dialogbox.setNegativeButton("no", new DialogInterface.OnClickListener() {
					
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}); 
			dialogbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					
				@Override
				public void onClick(DialogInterface dialog, int which) {
					File dir = getFilesDir();
					File file = new File(dir, "scores");
					file.delete();
					Intent restart = getIntent();
					startActivity(restart);
					finish();
				}
			});		
			dialogbox.show();
	}
}