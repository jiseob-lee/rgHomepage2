package com.rg.test;

import static org.junit.Assert.assertEquals;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

//import org.junit.jupiter.api.Test;

public class CalculatorTest {

	@Test
	public void test123() {
		//fail("Not yet implemented");
//		Calculator calculator = new Calculator();
//		assertEquals(30, calculator.sum(10, 20));
		int boardListCount = 19;
		int listLimit = 10;
		System.out.println("123");
		System.out.println(Integer.valueOf(boardListCount).doubleValue() / Integer.valueOf(listLimit).doubleValue());
		//System.out.println(Math.boardListCount / listLimit);
		System.out.println(Math.ceil(boardListCount / listLimit));
		assertEquals(10, 10);
	}

	/*
	@Test
	public void testSum() {
		//fail("Not yet implemented");
		Calculator calculator = new Calculator();
		assertEquals(30, calculator.sum(10, 20));
	}
	*/

}
