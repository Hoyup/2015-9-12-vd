package com.vd.util;

import java.util.Random;

public class LicenceKeyGenerator {
	public static int randomRange = 36;//10 numbers and 26 letters
	public static char[] table= {'0','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	public static String getLinsenceKey() {
		int seed = (new Random()).nextInt();
		String ret = "";
		for(int i=1;i<=16;i++) {
			Random random = new Random(seed*i+seed*seed);
			int cur = random.nextInt(randomRange-1)+1;
			ret+=cur<27?table[cur]+"":(cur-27);
		}
		return ret;
	}
}
