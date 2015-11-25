package com.example.mathgame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainMenu extends Activity {
	
	private Button bPlay,bScore;
	private static CheckBox cbSound;
	private ExecutorService threadExecutor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		startUpButtonPlay();
		startUpButtonScore();
		startUpCheckBox();
		startUpFont();
		threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(new Song(MainMenu.this));
		threadExecutor.shutdown();
	}	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Song.pause();
	}

	// adds a new font from the assets folder/ font 
	public void startUpFont(){                                                                                
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf"); 
		bPlay.setTypeface(crayon_crumble);     // set the font for all the buttons to crayon_crumble
		bScore.setTypeface(crayon_crumble);
	}
	
	public void startUpCheckBox(){
		cbSound = (CheckBox) findViewById(R.id.checkBoxSound);
		cbSound.setButtonDrawable(getResources().getDrawable(R.drawable.sound_on));
		cbSound.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				
				if(cbSound.isChecked()){
					cbSound.setButtonDrawable(getResources().getDrawable(R.drawable.sound_off));
					Song.pause();
				}
				else{
					cbSound.setButtonDrawable(getResources().getDrawable(R.drawable.sound_on));
					Song.start();
				}
			}	
		});
	}
	
	public void startUpButtonPlay(){
		bPlay = (Button) findViewById(R.id.buttonPlay);
		bPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent startPlayOptions = new Intent("com.example.mathgame.PLAYOPTIONS");
				startActivity(startPlayOptions);
			}
		});
		bPlay.setTextColor(Color.WHITE);
		
	}
	public void startUpButtonScore(){
		bScore = (Button) findViewById(R.id.buttonScore);
		bScore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent startScore = new Intent("android.intent.action.SCORE");
				startActivity(startScore);
			}
		});
		bScore.setTextColor(Color.WHITE);
		
	}
	
	public static boolean checkSound(){
		if(cbSound.isChecked())
			return true;
		else
			return false;
	}
	
}