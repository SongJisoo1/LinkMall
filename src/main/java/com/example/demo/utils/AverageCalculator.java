package com.example.demo.utils;

import java.text.DecimalFormat;

public class AverageCalculator {
	
	public static Double getAverage(double rate, int count) {
		Double average = (double)(rate / count);
		System.out.println("average : " + average);
		DecimalFormat df = new DecimalFormat("#.#");
	    // 반올림한 값을 문자열 변환 
	    String formattedValue = df.format(average);
	    // 문자열을 double로 파싱
	    double roundedValue = Double.parseDouble(formattedValue);
	    
	    return roundedValue;
	}

}
