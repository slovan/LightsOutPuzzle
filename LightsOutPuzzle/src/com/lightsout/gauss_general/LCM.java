package com.lightsout.gauss_general;
public class LCM {

	public static int findLCM(int a, int b) {
		// use formula GCD(a,b)*LCM(a,b) = (a*b)
		// to find least common multiple (LCM)
		int valueLCM = a * b / GCD.findGCD(a, b);
		return valueLCM;
	}
}
