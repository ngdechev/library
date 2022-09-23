package library.utils;

import java.io.Console;

public class ConsoleUserInterface {
	
	private Console console;
	
	/**
	 * Hiding access to constructor because this is a Singleton class - only one instance is allowed to be used in the whole application.
	 */
	private ConsoleUserInterface() {
		this.console = System.console();
	}
	
	/**
	 * 
	 * @return The singleton instance of ConsoleUserInterface
	 */
	public static ConsoleUserInterface get() {
		return new ConsoleUserInterface();
	}
	
	public String readLine(String prompt) {
		return console.readLine(prompt);
	}

	public String readPassword(String prompt) {
		char[] readPassword = console.readPassword(prompt);
		String password = new String(readPassword);
		return password;
	}

}