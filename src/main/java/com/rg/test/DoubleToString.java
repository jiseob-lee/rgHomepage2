package com.rg.test;

public class DoubleToString {

	public static void main(String[] args) {
		Double d = 1.2d;
		System.out.println(d);
		System.out.println(String.valueOf(d));
		System.out.println(Double.valueOf(d).toString());
	}

}
