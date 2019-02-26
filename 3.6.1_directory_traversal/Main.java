import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	/**
	 * Execute the safe program named by the first argument to this program.
	 * 
	 * @param args
	 *            must be at least one non-empty string, namely the executable name
	 *            for execSafeProgram
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			// must have an argument
			System.err.println("Must have at least one argument.");
			System.err.println("Usage: java Main \"executable_name\"");
			System.exit(-1);
		}
		if (args[0].length() < 1) {
			// first argument must be non-empty executable name
			System.err.println("Executable name must not be empty.");
			System.err.println("Usage: java Main \"executable_name\"");
			System.exit(-1);
		}

		// execute the program and print the exit code
		int retVal = execSafeProgram(args[0]);
		System.out.println();
		System.out.println("Program Exit Code: " + retVal);
	}

	/**
	 * Execute a program found within the directory "safe_programs" in the current
	 * working directory. Use as helper function to execute any of a pre-specified
	 * "safe" programs found in the safe directory.
	 * 
	 * @param programName
	 *            name of executable found in safe_programs
	 * @return exit value of program or -1 if unable to start, wait for, and join
	 *         the child process
	 * @throws SecurityException
	 *             if the program name tries to escape the safe directory
	 */
	private static int execSafeProgram(String programName) {
		// find the program to execute
		Path safeDir = Paths.get("safe_programs");
		Path exePath = safeDir.resolve(programName);

		Path execRealPath;
		// get real path of executable
		try {
			execRealPath = exePath.toRealPath();
		} catch (IOException ex) {
			// error getting real path
			System.out.println("Error running program: " + ex.getMessage());
			ex.printStackTrace();
			return -1;
		}

		// get the parent of the executable
		Path execParentPath = execRealPath.getParent();
		
		// check whether the executable parent directory matches with the safe directory
		// using absolute path because there is exactly one way to represent it
		// if not the same, then print error and return -1
		// else, continue
		if (!execParentPath.toAbsolutePath().equals(safeDir.toAbsolutePath())) {
			System.out.println("Bad executable path.");
			return -1;
		}
		
		// configure program runtime to execute ./safe_programs/programName executable
		ProcessBuilder procBuild = new ProcessBuilder(exePath.toString());

		// capture output and print to current shell
		procBuild.redirectErrorStream(true);
		procBuild.redirectOutput(Redirect.INHERIT);

		try {
			// execute the program
			Process p = procBuild.start();
			// wait for program to return and exit
			return p.waitFor();
		} catch (IOException ex) {
			// error starting process
			System.out.println("Error running program: " + ex.getMessage());
			ex.printStackTrace();
			return -1;
		} catch (InterruptedException ex) {
			// error waiting for process
			System.out.println("Error running program: " + ex.getMessage());
			ex.printStackTrace();
			return -1;
		}
	}
}