package simulator.utils;

public class Validator {
	public static int getTypeFile(String str) {
		if (str.isEmpty() || Checker.isNumber(str) == false)
			return -1;
		else if (str.equals("MD5"))
			return 1;
		return 0;
	}
}
