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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class PracticeOptions extends Activity{

	private Button bDone, bDefault;
	private CheckBox[] checkBoxes; //pos0: Add, pos1: Sub, pos2: Mul, pos3: Div
	private EditText[] limits; //pos0: AddMin, pos1: AddMax, pos2: SubMin, pos3: SubMax
							   //pos4: MulMin, pos5: MulMax, pos6: DivMin, pos7: DivMax
	private boolean[] operatorsActive;
	private int[] limitsValue;
	private String options;
	private String[] tokens;
	private FileOutputStream out;
	private TextView[] texts; // pos0: title, pos1-4: from1-4, pos5-8: to1-4
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.practice_options);
		startUpCheckBoxes();
		startUpTextFields();
		startUpOptions();
		startUpButtonDone();
		startUpButtonDefault();
		
		texts = new TextView[9];
		texts[0] = (TextView) findViewById(R.id.textViewOptions);
		texts[1] = (TextView) findViewById(R.id.textViewFrom1);
		texts[2] = (TextView) findViewById(R.id.textViewFrom2);
		texts[3] = (TextView) findViewById(R.id.textViewFrom3);
		texts[4] = (TextView) findViewById(R.id.textViewFrom4);
		texts[5] = (TextView) findViewById(R.id.textViewTo1);
		texts[6] = (TextView) findViewById(R.id.textViewTo2);
		texts[7] = (TextView) findViewById(R.id.textViewTo3);
		texts[8] = (TextView) findViewById(R.id.textViewTo4);
		
		startUpFont();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent startPlayOptions = new Intent("com.example.mathgame.PLAYOPTIONS");
		startActivity(startPlayOptions);
		finish();
	}

	public void startUpFont(){                                                                                
		Typeface crayon_crumble = Typeface.createFromAsset(getAssets(), "fonts/dk_crayon_crumble.ttf"); 
		bDone.setTypeface(crayon_crumble);     // set the font for all the buttons to crayon_crumble
		bDone.setTextColor(Color.WHITE);
		bDefault.setTypeface(crayon_crumble);
		bDefault.setTextColor(Color.WHITE);
		
		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i].setTypeface(crayon_crumble);
			checkBoxes[i].setTextColor(Color.WHITE);
		}
		for (int i = 0; i < limits.length; i++) {
			limits[i].setTypeface(crayon_crumble);
			limits[i].setBackgroundColor(Color.TRANSPARENT);
			limits[i].setTextColor(Color.WHITE);
		}
		
		for (int i = 0; i < texts.length; i++) {
			texts[i].setTypeface(crayon_crumble);
			texts[i].setTextColor(Color.WHITE);
		}
	}
	
	public void startUpCheckBoxes(){
		checkBoxes = new CheckBox[4];
		checkBoxes[0] = (CheckBox) findViewById(R.id.checkBoxAdd);
		checkBoxes[1] = (CheckBox) findViewById(R.id.checkBoxSub);
		checkBoxes[2] = (CheckBox) findViewById(R.id.checkBoxMul);
		checkBoxes[3] = (CheckBox) findViewById(R.id.checkBoxDiv);
	}
	
	public void startUpTextFields(){
		limits = new EditText[8];
		limits[0] = (EditText) findViewById(R.id.editAddMin);
		limits[1] = (EditText) findViewById(R.id.editAddMax);
		limits[2] = (EditText) findViewById(R.id.editSubMin);
		limits[3] = (EditText) findViewById(R.id.editSubMax);
		limits[4] = (EditText) findViewById(R.id.editMulMin);
		limits[5] = (EditText) findViewById(R.id.editMulMax);
		limits[6] = (EditText) findViewById(R.id.editDivMin);
		limits[7] = (EditText) findViewById(R.id.editDivMax);
	}
	
	public void startUpOptions(){
		if(fileExistance("practiceOptions")){
			retrieveOptions();
            
            checkBoxes[0].setChecked((tokens[0].equals("true")) ? true : false);
			checkBoxes[1].setChecked((tokens[1].equals("true")) ? true : false);
			checkBoxes[2].setChecked((tokens[2].equals("true")) ? true : false);
			checkBoxes[3].setChecked((tokens[3].equals("true")) ? true : false);
			limits[0].setText(tokens[4]);
			limits[1].setText(tokens[5]);
			limits[2].setText(tokens[6]);
			limits[3].setText(tokens[7]);
			limits[4].setText(tokens[8]);
			limits[5].setText(tokens[9]);
			limits[6].setText(tokens[10]);
			limits[7].setText(tokens[11]);
			
		}
		else{
			checkBoxes[0].setChecked(true);
			checkBoxes[1].setChecked(true);
			checkBoxes[2].setChecked(true);
			checkBoxes[3].setChecked(true);
			limits[0].setText("0");
			limits[1].setText("20");
			limits[2].setText("0");
			limits[3].setText("20");
			limits[4].setText("0");
			limits[5].setText("12");
			limits[6].setText("0");
			limits[7].setText("12");
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
	
	public void startUpButtonDone(){
		bDone = (Button) findViewById(R.id.buttonDone);
		bDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(checkOptions()){
					errorPopUp();
				}else{
					operatorsActive = new boolean[4];
					operatorsActive[0] = checkBoxes[0].isChecked();
					operatorsActive[1] = checkBoxes[1].isChecked();
					operatorsActive[2] = checkBoxes[2].isChecked();
					operatorsActive[3] = checkBoxes[3].isChecked();
					
					limitsValue = new int[8];
					limitsValue[0] = Integer.parseInt(limits[0].getText().toString());
					limitsValue[1] = Integer.parseInt(limits[1].getText().toString());
					limitsValue[2] = Integer.parseInt(limits[2].getText().toString());
					limitsValue[3] = Integer.parseInt(limits[3].getText().toString());
					limitsValue[4] = Integer.parseInt(limits[4].getText().toString());
					limitsValue[5] = Integer.parseInt(limits[5].getText().toString());
					limitsValue[6] = Integer.parseInt(limits[6].getText().toString());
					limitsValue[7] = Integer.parseInt(limits[7].getText().toString());
					
					saveOptions();
					
					Intent startGamePractice = new Intent("android.intent.action.GAMEPRACTICE");
					startGamePractice.putExtra("operatorsActive", operatorsActive);
					startGamePractice.putExtra("limitsValue", limitsValue);
					startActivity(startGamePractice);
					finish();
				}
			}
		});
	}
	
	public void startUpButtonDefault(){
		bDefault = (Button) findViewById(R.id.buttonDefault);
		bDefault.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkBoxes[0].setChecked(true);
				checkBoxes[1].setChecked(true);
				checkBoxes[2].setChecked(true);
				checkBoxes[3].setChecked(true);
				limits[0].setText("0");
				limits[1].setText("20");
				limits[2].setText("0");
				limits[3].setText("20");
				limits[4].setText("0");
				limits[5].setText("12");
				limits[6].setText("0");
				limits[7].setText("12");
			}
		});
	}
	
	public void saveOptions(){
		try {
			out = openFileOutput("practiceOptions", Context.MODE_PRIVATE);
			options = ((operatorsActive[0]) ? "true" : "false") + " " + 
					((operatorsActive[1]) ? "true" : "false") + " " + 
					((operatorsActive[2]) ? "true" : "false") + " " + 
					((operatorsActive[3]) ? "true" : "false") + " " +
					limitsValue[0] + " " + limitsValue[1] + " " + 
					limitsValue[2] + " " + limitsValue[3] + " " + 
					limitsValue[4] + " " + limitsValue[5] + " " + 
					limitsValue[6] + " " + limitsValue[7];
			out.write(options.getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retrieveOptions(){
		
		try {
			FileInputStream in = openFileInput("practiceOptions");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader bufferedReader = new BufferedReader(isr);
			String receiveString = "";
			StringBuilder stringBuilder = new StringBuilder();
			
			while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }
			in.close();
			
			options = stringBuilder.toString();
			tokens = options.split(" ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public boolean checkOptions(){
		int min;
		int max;
		int checkBoxesChecked = 0;
		int temp = 0;
		for (int i = 0; i < limits.length; i++) {
			if(limits[i].getText().toString().matches(""))
				limits[i].setText("0");
		}
		for (int i = 0; i < 4; i++) {
			if(checkBoxes[i].isChecked()){
				checkBoxesChecked++;				
			}
		}
		if(checkBoxesChecked == 0)
			return true;
		for (int i = 0; i < checkBoxes.length; i++) {
			if(checkBoxes[i].isChecked()){
				min = Integer.parseInt(limits[temp].getText().toString());
				max = Integer.parseInt(limits[temp+1].getText().toString());
				if(i == 0 || i == 1){
					if(min > 999 || max > 999)
						return true;
				}
				if(i == 2 || i == 3){
					if(min > 999 || max > 999)
						return true;
				}
				if(min == 0 && max == 0)
					limits[temp+1].setText("1");
				if(min > max){
					limits[temp].setText(max + "");
					limits[temp+1].setText(min + "");
				}
			}
			else{
				if(limits[temp].getText().toString().equals("0") && limits[temp+1].getText().toString().equals("0"))
					limits[temp+1].setText("1");
			}
			temp += 2;
		}
		return false;
	}
	
	public void errorPopUp(){
		Builder box = new AlertDialog.Builder(this);
        box.setMessage("You must check at least one operator. Minimum and maximum" +
        		" for selected operator cannot exceed 999 and cannot be 0, 0.");
        box.setCancelable(false);
        box.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        box.show();
	}
}