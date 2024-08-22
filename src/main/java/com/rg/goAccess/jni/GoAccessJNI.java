package com.rg.goAccess.jni;

public class GoAccessJNI {

	public native int parse();

	public static void main(String[] args) {

		new GoAccessJNI().parse();

	}

	static {

		System.loadLibrary("GoAccessJNI");

	}

}
