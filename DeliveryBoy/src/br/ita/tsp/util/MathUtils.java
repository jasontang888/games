package br.ita.tsp.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MathUtils {


	public static float truncate(float Rval, int Rpl) {
		float p = (float) Math.pow(10, Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return (float) tmp / p;
	}

	public static int random() {
		int result = 0;
		try {
			result = SecureRandom.getInstance("SHA-1").nextInt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result % 5;
	}

	public static final float radToDeg(final float pRad) {
		return Constants.RAD_TO_DEG * pRad;
	}

}
