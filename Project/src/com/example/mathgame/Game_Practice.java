package com.example.mathgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_Practice extends Activity {

	private Button bMenu, bTrue, bFalse;
	private TextView[] texts;
	 //equation1, equation2, equation3, equation4, lastEquation, percentRight
	private Math_Game2 math;
	private String[] equation1;
	private String[] equation2;
	private String[] equation3;
	private String[] equation4;
	private double questionsDone;
	private double questionsRight;
	private String percent;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_practice);
		Bundle extras = getIntent().getExtras();
		math = new Math_Game2(extras.getBooleanArray("operatorsActive"), extras.getIntArray("limitsValue"));
		questionsDone = 0;
		questionsRight = 0;
		
		startUpTextViews();
		startUpButtonMenu();
		startUpButtonTrue();
		startUpButtonFalse();
		startUpFont();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent startPracticeOptions = new Intent("android.intent.action.PRACTICEOPTIONS");
		startActivity(startPracticeOptions);
		finish();
	}

	public void startUpFont(){
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf");
		for (int i = 0; i < texts.length; i++) {
			texts[i].setTypeface(crayon_crumble);
			texts[i].setTextColor(Color.WHITE);
		}
		bFalse.setTypeface(crayon_crumble);
		bFalse.setTextColor(Color.WHITE);
		bTrue.setTypeface(crayon_crumble);
		bTrue.setTextColor(Color.WHITE);
		bMenu.setTypeface(crayon_crumble);
		bMenu.setTextColor(Color.WHITE);
	}

	public void startUpTextViews(){
		texts = new TextView[6];
		texts[0] = (TextView) findViewById(R.id.viewEquation1_Practice);
		texts[1] = (TextView) findViewById(R.id.viewEquation2_Practice);
		texts[2] = (TextView) findViewById(R.id.viewEquation3_Practice);
		texts[3] = (TextView) findViewById(R.id.viewEquation4_Practice);
		texts[4] = (TextView) findViewById(R.id.viewLastEquation_Practice);
		texts[5] = (TextView) findViewById(R.id.viewPercentRight_Practice);
		
		equation1 = math.getAnEquation();
		equation2 = math.getAnEquation();
		equation3 = math.getAnEquation();
		equation4 = math.getAnEquation();
		texts[0].setText(equation1[0]);
		texts[1].setText(equation2[0]);
		texts[2].setText(equation3[0]);
		texts[3].setText(equation4[0]);
		
		image = (ImageView) findViewById(R.id.imageView_Practice);
	}
	
	public void startUpButtonMenu(){
		bMenu = (Button) findViewById(R.id.buttonMenu_Practice);
		bMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainMenu.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}
	
	public void startUpButtonTrue(){
		bTrue = (Button) findViewById(R.id.buttonTrue_Practice);
		bTrue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				texts[4].setText(equation1[0]);
				if(equation1[1].equals("1")){
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
					questionsRight++;
				}
				else
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
				update();
			}
		});
	}

	public void startUpButtonFalse(){
		bFalse = (Button) findViewById(R.id.buttonFalse_Practice);
		bFalse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				texts[4].setText(equation1[0]);
				if(equation1[1].equals("0")){
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.right));
					questionsRight++;
				}
				else
					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
				update();	
			}
		});
	}
	
	public void update(){
		questionsDone++;
		texts[0].setText(equation2[0]);
		texts[1].setText(equation3[0]);
		texts[2].setText(equation4[0]);
		equation1 = equation2;
		equation2 = equation3;
		equation3 = equation4;
		equation4 = math.getAnEquation();
		texts[3].setText(equation4[0]);
		double percentNum = (questionsRight/questionsDone) * 100;
		percentNum = (double)Math.round(percentNum*10)/10;
		percent = percentNum + "%";
		texts[5].setText(percent);
	}
	
}