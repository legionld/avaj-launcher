package simulator.utils;

public class ErrorsProvider {
	private static ErrorsProvider errorsProvider = null;

	private ErrorsProvider() {

	}

	public static ErrorsProvider getError() {
		if (errorsProvider == null) {
			errorsProvider = new ErrorsProvider();
		}
		return errorsProvider;
	}

	public static void printError(int error) {
		switch (error) {
			case 1:
				System.out.println("Error #sm-001: Bad count arguments. Programs is have one argument");
				break;
			case 20:
				System.out.println("Error #sm-020: Can not reade file.");
				break;
			case 21:
				System.out.println("Warning #sm-021: Can not write file. This problem may cause errors in the following executions.");
				break;
			case 22:
				System.out.println("Error #sm-022: Can not write file.");
				System.exit(0);
				break;
			case 30:
				System.out.println("Error #sm-030: Bad file.");
				break;
			case 31:
				System.out.println("Error #sm-031: Bad file. First line must have numbers > 0");
				break;
			case 32:
				System.out.println("Warning #sm-032: First line value greater than 100, the program may take a long time to stimulate.");
				break;
			case 33:
				System.out.println("Error #sm-033: Bad file. A coordinate have undefine symbol");
				break;
			case 34:
				System.out.println("Error #sm-034: Bad file. A type is unknown");
				break;
			default:
				System.out.println("Error #sm-000: Unknown error.");
		}
	}
}
