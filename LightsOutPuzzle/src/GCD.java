
public class GCD {
	public static int findGCD(int a, int b){
		// find greatest common divisor (GCD) by using Euclidean algorithm
		if ((a < 0)||(b < 0))
			return -1;
		if (a == 0)
			return b;
		if (b == 0)
			return a;
		while (a != b) {
			if (a > b)
				a -= b;
			else
				b -= a;
		}
		return a;
	}
}
