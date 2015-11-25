package com.example.mathgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayOptions extends Activity {

	private Button bEasy, bMedium, bHard, bChallenge, bPractice;

	@Override
	protected void onCreate(Bundle playOptionsMenu) {
		super.onCreate(playOptionsMenu);
		setContentView(R.layout.play_options);
		startUpButtonEasy();
		startUpButtonMedium();
		startUpButtonHard();
		startUpButtonChallenge();
		startUpButtonPractice();
		startUpFont();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	public void startUpFont(){
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf");
		bEasy.setTypeface(crayon_crumble);
		bMedium.setTypeface(crayon_crumble);
		bHard.setTypeface(crayon_crumble);
		bChallenge.setTypeface(crayon_crumble);
		bPractice.setTypeface(crayon_crumble);
	}
	
	// methods StartUps
	public void startUpButtonEasy() {
		bEasy = (Button) findViewById(R.id.buttonEasy);
		bEasy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openGameE = new Intent("com.example.mathgame.GAMEEMH");
				boolean [] check = {true,true,false,false};// addition and subtraction only
				int [] limits = { 0, 20, 0, 20, 0, 0, 0, 0 };// from 0 to 20
				String difficulty = "easy";
				openGameE.putExtra("check", check);
				openGameE.putExtra("limits", limits);
				openGameE.putExtra("difficulty", difficulty);
				startActivity(openGameE);
				finish();
			}
		});// end of click listener method
		bEasy.setTextColor(Color.WHITE);
	}// end start up method for easy button

	public void startUpButtonMedium() {
		bMedium = (Button) findViewById(R.id.buttonMedium);
		bMedium.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openGameE = new Intent("com.example.mathgame.GAMEEMH");
				boolean [] check = {true,true,true,false};// addition, subtraction and multiplication
				int [] limits = { 0, 30, 0, 30, 0, 12, 0, 0 };// add and sub from 0 to 30, mul from 0 to 12
				String difficulty = "meduim";
				openGameE.putExtra("check", check);
				openGameE.putExtra("limits", limits);
				openGameE.putExtra("difficulty", difficulty);
				startActivity(openGameE);
				finish();
			}
		});// end of click listener method
		bMedium.setTextColor(Color.WHITE);
	}// end start up method for Medium button

	public void startUpButtonHard() {
		bHard = (Button) findViewById(R.id.buttonHard);
		bHard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openGameE = new Intent("com.example.mathgame.GAMEEMH");
				boolean [] check = {true,true,true,true};
				int [] limits = { 0, 50, 0, 50, 0, 12, 0, 12};
				String difficulty = "hard";
				openGameE.putExtra("check", check);
				openGameE.putExtra("limits", limits);
				openGameE.putExtra("difficulty", difficulty);
				startActivity(openGameE);
				finish();
			}
		});// end of click listener method
		bHard.setTextColor(Color.WHITE);
	}// end start up method for Hard button

	public void startUpButtonChallenge() {
		bChallenge = (Button) findViewById(R.id.buttonChallenge);
		bChallenge.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startGameChallenge = new Intent("android.intent.action.GAMECHALLENGE");
				startActivity(startGameChallenge);
				finish();
			}
		});// end of click listener method
		bChallenge.setTextColor(Color.WHITE);
	}// end start up method for Challenge button

	public void startUpButtonPractice() {
		bPractice = (Button) findViewById(R.id.buttonPractice);
		bPractice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startPracticeOptions = new Intent("android.intent.action.PRACTICEOPTIONS");
				startActivity(startPracticeOptions);
				finish();
			}
		});// end of click listener method
		bPractice.setTextColor(Color.WHITE);
	}// end start up method for Practice button
}