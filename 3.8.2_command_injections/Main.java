import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main execution class for cmd_injection exercise. Prompts user for input to
 * the nslookup command and prints the output.
 * 
 * @author Joseph Eichenhofer
 *
 */
public class Main {

	/**
	 * Prompts user for hostname to lookup. Performs DNS resolution and prints
	 * address/info for the given hostname.
	 * 
	 * @param args
	 *            n/a
	 */
	public static void main(String[] args) {
		Console terminal = System.console();

		if (terminal == null) {
			System.out.println("Error fetching console. Are you running from an IDE?");
			System.exit(-1);
		}

		while (true) {
			String hostname = terminal.readLine("hostname to lookup: ");

			if (hostname.toLowerCase().equals("exit"))
				break;

			try {
				System.out.println(rDomainName(hostname));
			} catch (IOException e) {
				System.out.println("error executing nslookup");
			}
		}
	}

	/**
	 * Lookup given hostname using nslookup command. Return the output/error of the
	 * nslookup command as string.
	 * 
	 * @param hostname
	 *            hostname/domain to lookup
	 * @return string output of nslookup command
	 * @throws IOException
	 *             if unable to execute the command or read its output
	 */
	private static String rDomainName(String hostname) throws IOException {
		// execute the nslookup command
		String[] cmd = { "/bin/sh", "-c", "nslookup " + hostname };
		Process proc = Runtime.getRuntime().exec(cmd);

		// capture output from command
		BufferedReader stdOut = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		StringBuilder output = new StringBuilder();
		String currLine = null;
		while ((currLine = stdOut.readLine()) != null) {
			output.append(currLine + "\n");
		}
		while ((currLine = stdErr.readLine()) != null) {
			output.append(currLine + "\n");
		}

		// return the result
		return output.toString();
	}

}
