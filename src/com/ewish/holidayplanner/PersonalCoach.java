package com.ewish.holidayplanner;

import android.graphics.Color;

public class PersonalCoach {
	private double weight;
	private double height;
	public PersonalCoach(){};
	public PersonalCoach(double weight, double height) {
		super();
		this.weight = weight;
		this.height = height;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public double getBMI(){
		double result = this.weight/Math.pow(this.height/100,2.0);
		return result;
	}
	
	public String getBMIString(double bmi){
		String result = "";
		if(bmi <18.5){
			result = "Underweight";
		}else if(bmi < 25.0){
			result = "Normal";
		}else if(bmi < 30.0){
			result = "Overweight";
		}else if(bmi >= 30){
			result = "Obesity";
		}else{
			result = "Error!";
		}
		return result;
	}
	public int getBMIColor(double bmi){
		int color = Color.WHITE;
		if(bmi <18.5){
			color = Color.RED;
		}else if(bmi < 25.0){
			color = Color.GREEN;
		}else if(bmi < 30.0){
			color = Color.YELLOW;
		}else if(bmi >= 30){
			color = Color.RED;
		}else{
			color = Color.BLACK;
		}
		return color;
	}
	
	
	
}
