package com.example.mathgame;

public class Math_Game2 {
	private int[] limits; // pos 0: addMin, pos 1: addMax, pos 2: subMin, pos 3: subMax
						  // pos 4: mulMin, pos 5: mulMax, pos 6: divMin, pos 7: divMax
	private boolean[] signs; // pos 0 : +, pos 1 : -, pos 2: *, pos 3: /;

	// constructors
	public Math_Game2(boolean[] signs, int[] limits) {
		setSigns(signs);
		setLimits(limits);
	}

	// setters
	public void setSigns(boolean[] signs) {
		this.signs = signs;
	}

	public void setLimits(int[] limits) {
		this.limits = limits;
	}

	// getters
	public int[] getLimits() {
		return limits;
	}

	public boolean[] getSigns() {
		return signs;
	}

	// methods
	public String[] getAnEquation() {
		// pos 0: equation , pos 1: 0 is false___ 1 is true
		while (true) {
			switch ((int) (Math.random() * (4))) {
			case 0:
				if (signs[0])
					return creatAdditionalEquation();
				else
					continue;
			case 1:
				if (signs[1])
					return creatSubtractionEquation();
				else
					continue;
			case 2:
				if (signs[2])
					return creatMultiplicationEquation();
				else
					continue;
			case 3:
				if (signs[3])
					return creatDivisonEquation();
				else
					continue;
			}// end of switch
		}// end of while loop
	}// end of getAnEquation method

	public void increaseDificult(int num){
		for (int i = 0; i < limits.length; i++) {
			if(i < 4 ){
				if(i%2 == 1) // check if is a max
					limits[i]++;
				else if(i%2 == 0 && num%5 == 0 && num != 0) // check if is a min 
					limits[i]++;
			} else if(i%2 == 1 && num%4 == 0 && num != 0)
				limits[i]++;
			else if(i%2 == 0 && num%9 == 0 && num != 0)
				limits[i]++;
		}
	}
	// private methods
	private String[] creatAdditionalEquation() {
		int num1 = (int) (limits[0] + Math.random() * (limits[1] + 1 - limits[0])); // make
																		// Number 1
		
		int num2 = (int) (limits[0] + Math.random() * (limits[1] + 1 - limits[0])); // make
																		// number 2
		int equals = num1 + num2;  // get the sum
		boolean check = true;  // is going to say if the equation is true or false     

		if (Math.random() < 0.5) { // is going to set the equation false if the
									// number is less than 0.5
			
			check = false;    // set check to false
			int equals2 = equals;  // we have to change the equal
			while (equals == equals2) { 

				if (((num1 == 1) || (num2 == 1)) && (Math.random() < 0.6)){ // if for getting a false equation when the result is 1  
						if (num1 == 1) {
							equals2 = num2;
							continue;
						} else if (num2 == 1) {
							equals2 = num1;
							continue;
					} 
				}else {
						if (limits[1] - limits[0] > 5) {
							int change = (int)((limits[1] - limits[0]) * 0.2);

							if ((num1 > change || num2 > change)
									&& Math.random() < 0.5)
								equals2 = num2 + num1 - (int)(1 + ( 1 +change)*Math.random());  // subtract change 
							else
								equals2 = num2 + num1 + (int)(1 + ( 1 +change)*Math.random()); //add change
							
						} else {

							equals2 = (int) (limits[0] + Math.random()
									* (2 * limits[1] + 1));
						}
					} // end of else

			} // end of while loop
			equals = equals2;

		} // end of if
		String[] equation = { num1 + " + " + num2 + " = " + equals, 
				(check) ? 1 + "" : 0 + "" };  // one means true, 0 means false
		return equation;
	}

	private String[] creatSubtractionEquation() {
		int num1 = (int) (limits[2] + Math.random() * (limits[3] + 1 - limits[2])); // make
																		// number 1
		int num2 = (int) (limits[2] + Math.random() * (limits[3] + 1 - limits[2])); // make
																		// number 2
		if (num1 < num2) {
			int switchh = num1;
			num1 = num2;
			num2 = switchh;
		}

		int equals = num1 - num2;
		boolean check = true;

		if (Math.random() < 0.5) { // is going to set the equation false if the
									// number is less than 0.5
			check = false;
			int equals2 = equals;
			while (equals == equals2) { 

				if (((num1 == 1) || (num2 == 1)) && (Math.random() < 0.6)){ // if for getting a false equation when the result is 1  
						if (num1 == 1) {
							equals2 = num2;
							continue;
						} else if (num2 == 1) {
							equals2 = num1;
							continue;
					} 
				}else {
						if (limits[3] - limits[2] > 5) {
							int change = (int)((limits[3] - limits[2]) * 0.2);

							if (( (num1 - num2) > change)
									&& Math.random() < 0.5)
								equals2 = num1 - num2 - (int)(1 + ( 1 +change)*Math.random());  // subtract change 
							else
								equals2 = num1 - num2 + (int)(1 + ( 1 +change)*Math.random()); //add change
							
						} else {

							equals2 = (int) (limits[2] + Math.random()
									* (limits[3] + 1));
						}
					} // end of else

			} // end of while loop
			equals = equals2;

		} // end of if
		String[] equation = { num1 + " - " + num2 + " = " + equals,
				(check) ? 1 + "" : 0 + "" };
		return equation;
	}

	private String[] creatMultiplicationEquation() {
		int[] num = getMultiplication(limits[4], limits[5]);
		boolean check = true;

		if (Math.random() < 0.5) { // is going to set the equation false if the
									// number is less than 0.5
			check = false;
			int equals2 = num[2];
			while (num[2] == equals2) {

				if (((num[0] == 1) || (num[1] == 1)) && Math.random() < 0.6){
						equals2 = (num[0] > num[1]) ? num[0] + 1 : num[1] + 1;
						continue;
						
					} else if ((num[0] == 0) || (num[1] == 0)) {
						equals2 = (num[0] > num[1]) ? num[0] : num[1];
						continue;
						
					} else if (limits[5] - limits[4] > 5) {
						int change = (int)((limits[5] - limits[4]) * 0.2);
						
						if(num[2] > change && Math.random() < 0.5){
							equals2 = (int) (num[2] - (1 + (change + 1)*Math.random()));
						}
						else equals2 = (int) (num[2] + (1 + (change + 1)*Math.random()));
					} else {
						
						equals2 = (int) (limits[4] + Math.random()
								* (limits[5] * limits[5] + 1));
						continue;
					}
			} // end of while loop
			num[2] = equals2;

		} // end of if

		String[] equation = { num[0] + " x " + num[1] + " = " + num[2],
				(check) ? 1 + "" : 0 + "" };
		return equation;
	}

	private String[] creatDivisonEquation() {
		int[] num = getMultiplication(limits[6], limits[7]);

		{// make switch
			int switchh;// this variable can only be use inside the switch
			if (num[1] == 0) { // numbers can't be divided by 0
				switchh = num[0];
				num[0] = num[1];
				num[1] = switchh;
			}
			switchh = num[0];
			num[0] = num[2];
			num[2] = switchh;
		}// end of switch
		boolean check = true;
		if (Math.random() < 0.5) { // is going to set the equation false if the
									// number is less than 0.5
			check = false;
			int equals2 = num[2];
			while (num[2] == equals2) {
				if (num[0] == 1) {
					equals2 = (Math.random() < 0.5) ? num[1] - 1 : num[1] + 1;
					continue;
				} else if (num[0] == 0) {
					equals2 = num[1];
					continue;
				}
				if (num[1] == 1) {
					equals2 = (Math.random() < 0.5) ? num[0] - 1 : num[0] + 1;
					continue;
				} else if (num[1] == 0) {
					equals2 = num[0];
					continue;
				}
				
				if (limits[7] - limits[6] > 5){
					int change = (int)((limits[5] - limits[4]) * 0.2);
					if(num[2] > change && Math.random() < 0.5)
						equals2 = (int) (num[2] - (1 + (change + 1)*Math.random()));
					
						else equals2 = (int) (num[2] + (1 + (change + 1)*Math.random()));
				}
				else equals2 = (int) (limits[6] + Math.random() * (limits[7] + 1));
			} // end of while loop
			num[2] = equals2;

		} // end of if
		String[] equation = { num[0] + " / " + num[1] + " = " + num[2],
				(check) ? 1 + "" : 0 + "" };
		return equation;
	}

	private int[] getMultiplication(int min, int max) {
		int num1, num2;
		do {
		 num1 = (int) (min + Math.random() * (max + 1 - min));
		 num2 = (int) (min + Math.random() * (max + 1 - min));
		}while (num1 == 0 && num2 == 0); // the two numbers cannot be 0
		
		int equals = num1 * num2;
		int[] num = { num1, num2, equals };
		return num;
	}
}