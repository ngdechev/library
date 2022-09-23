package library;

import java.util.List;

import library.exceptions.BookException;
import library.exceptions.LibraryException;
import library.exceptions.UserException;
import library.model.Book;
import library.model.User;
import library.operations.BookOperations;
import library.operations.CommonOperations;
import library.operations.UserOperations;
import library.utils.BookForm;
import library.utils.ConsoleUserInterface;
import library.utils.Constants.Commands;
import library.utils.Constants.Messages;

/**
 * The entry point of this application.
 */
public class App {

	private static ConsoleUserInterface ui;
	private static boolean inStart = true;
	private static User loggedInUser;
	private static boolean isLoggedIn = false;

	public static void main(String[] args) throws Exception {
		ui = ConsoleUserInterface.get();
		
		startScreen();
	}

	private static void startScreen() throws Exception {
		inStart = true;
		do {
			System.out.println("\tWelcome to The Library!");
			String input = ui.readLine("How do you wish to continue? [login, help, exit]:");
			if (input == null) {
				System.out.println(Messages.UNKNOWN_COMMAND);
				continue;
			}
			input = input.trim();
			if (input.equals(Commands.LOGIN)) {
				if (!isLoggedIn) {
					boolean shouldTryAgain = false;
					do { 
						System.out.println("\tLog In to The Library");
						String username = ui.readLine("Username:");
						String password = ui.readPassword("Password:");
						try {
							loggedInUser = UserOperations.find(username, password);
	
							inStart = false;
							isLoggedIn = true;
							System.out.printf("\tWelcome, %s!\n", loggedInUser.getName());
						} catch (UserException e) {
							System.out.println(e.getMessage());
							boolean invalidAnswer = true;
							do {
								String response = ui.readLine("Try again? (yes/no)");
								invalidAnswer = !response.contentEquals("yes") && !response.equals("no");
								
								if (!invalidAnswer) {
									shouldTryAgain = response.equals("yes");
								}
							} while(invalidAnswer);
						}
					} while (!isLoggedIn && shouldTryAgain);
					startSession();
				}
			} else if (input.equals(Commands.EXIT)) {
				CommonOperations.exit();
			} else if (input.equals(Commands.HELP)) {
				showHelp();
			} else {
				System.out.println("You are not allowed to enter other commands while you're logged out. Returning.\n");
			}
		} while (inStart);
	}

	private static void startSession() throws Exception {
		if (!isLoggedIn) {
			System.out.println("Not logged in. Returning to start page.");
			startScreen();
		}
		while (isLoggedIn) {
			String input = ui.readLine("What would you like to do next? (Type \"help\" to see all available commands.) ");
			if (input == null) {
				System.out.println(Messages.UNKNOWN_COMMAND);
				continue;
			}
			input = input.trim(); // remove all whitespace before checking
			if (input.equals(Commands.BOOKS_ALL)) {
				List<Book> allBooks = BookOperations.getAllBooks();
				System.out.println("\tLibrary");
				if (allBooks == null || allBooks.isEmpty()) {
					System.out.println("The Library has no available books at the moment.");
				} else {
					displayTableOfBooks(allBooks);
				}
			} else if (input.startsWith(Commands.BOOKS_INFO)) {
				String isbn = extractOptions(input, Commands.BOOKS_INFO); 
				showBookDetails(isbn);
			} else if (input.startsWith(Commands.BOOKS_VIEW)) {
				String isbn = extractOptions(input, Commands.BOOKS_VIEW); 
				showBookDetails(isbn);
			} else if (input.startsWith(Commands.BOOKS_FIND)) {
				String findOptions = extractOptions(input, Commands.BOOKS_FIND);
				if (findOptions == null) {
					System.out.println(Messages.INVALID_INPUT);
					System.out.println("books find <property> <search term>");
				} else {
					String[] options = findOptions.split(" ");
					if (options.length < 2) {
						System.out.println(Messages.INVALID_INPUT);
						System.out.println("books find <property> <search term>");
					} else {
						String property = options[0];
						String searchTerm = options[1];
						try {
							List<Book> results = BookOperations.searchBooks(property, searchTerm);
							System.out.println(String.format("\tSearch Results for %s: %s", property, searchTerm));
							if (results.isEmpty()) {
								System.out.println("No matching records were found.");
							} else {
								displayTableOfBooks(results);
							}
						} catch (LibraryException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			} else if (input.startsWith(Commands.BOOKS_SORT)) {
				String sortOptions = extractOptions(input, Commands.BOOKS_SORT);
				if (sortOptions == null || sortOptions.trim().isEmpty()) {
					System.out.println(Messages.INVALID_INPUT);
					System.out.println("books sort <property> [asc | desc]");
				} else {
					String[] options = sortOptions.split(" ");
					String property = options[0].trim();
					String direction = options.length > 1 ? options[1].trim() : "asc";
					try {
						List<Book> sortedBooks = BookOperations.sortBooks(property, direction);
						System.out.println("\tLibrary");
						System.out.println(String.format("Sorted by %s %s", property, direction));
						if (sortedBooks.isEmpty()) {
							System.out.println("The Library has no available books at the moment.");
						} else {
							displayTableOfBooks(sortedBooks);
						}
					} catch (LibraryException e) {
						System.out.println(e.getMessage());
					}
				}
			} else if (input.startsWith(Commands.BOOKS_ADD)) {
				if (!loggedInUser.isAdmin()) {
					System.out.println(Messages.NO_RIGHTS);
				} else {
					System.out.println("\tAdd a new book");
					BookForm bookForm = new BookForm(ui);
					Book newBook = bookForm.read();
					BookOperations.addBook(newBook);
					System.out.println("Successfully added new book.");
				}
			} else if (input.startsWith(Commands.BOOKS_REMOVE)) {
				if (!loggedInUser.isAdmin()) {
					System.out.println(Messages.NO_RIGHTS);
				} else {
					String isbn = extractOptions(input, Commands.BOOKS_REMOVE);
					if (isbn == null || isbn.isEmpty()) {
						System.out.println("Missing ISBN.");
					} else {
						try {
							BookOperations.deleteBook(isbn);
							System.out.println("Successfully removed book.");
						} catch (BookException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			} else if (input.startsWith(Commands.USERS_ADD)) {
				if (!loggedInUser.isAdmin()) {
					System.out.println(Messages.NO_RIGHTS);
				} else {
					String userInfo = extractOptions(input, Commands.USERS_ADD);
					if (userInfo == null || userInfo.trim().isEmpty()) {
						System.out.println("Missing username and password.");
					} else {
						String[] credentials = userInfo.split(" ");
						if (credentials.length < 2) {
							System.out.println("Username and password are required to create a new user.");
						} else {
							if (credentials.length > 2) {
								System.out.println("Password should not contain space characters.");
							} else {
								User newUser = new User();
								newUser.setName(credentials[0]);
								newUser.setPassword(credentials[1]);
								try {
									UserOperations.add(newUser);
									System.out.println(String.format("Successfully added new user %s.", newUser.getName()));
								} catch (UserException e) {
									System.out.println(e.getMessage());
								}
							}
						}
					}
				}
			} else if (input.startsWith(Commands.USERS_REMOVE)) {
				if (!loggedInUser.isAdmin()) {
					System.out.println(Messages.NO_RIGHTS);
				} else {
					try {
						String username = extractOptions(input, Commands.USERS_REMOVE);
						if (username == null) {
							System.out.println("Missing username.");
						} else {
							// delete from xml
							UserOperations.remove(username);
							System.out.println(String.format("Successfully removed user %s.", username));
						}
					} catch (UserException e) {
						System.out.println(e.getMessage());
					}
				}
			} else if (input.equals(Commands.HELP)) {
				showHelp();
			} else if (input.equals(Commands.LOGOUT)) {
				// logout session
				System.out.println("Logging out...");
				loggedInUser = null;
				isLoggedIn = false;
				startScreen();
			} else if (input.equals(Commands.LOGIN)) {
				System.out.println("You are already logged in. Type \"logout\" and then try again if you need to switch accounts.");
			} else if (input.equals(Commands.EXIT)) {
				System.out.println("Shutting down.");
				System.exit(0);
			} else {
				System.out.println(Messages.UNKNOWN_COMMAND);
			}
			System.out.println();
		}
	}

	/**
	 * Display a list of Books in the form of a table.
	 * @param allBooks
	 */
	private static void displayTableOfBooks(List<Book> allBooks) {
		System.out.println("--------------------------------");
		System.out.println("| Title | Author | Genre | ISBN |");
		System.out.println("--------------------------------");
		for (Book book : allBooks) {
			System.out.printf("%s | %s | %s | %s\n", book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn());
		}
		System.out.println("--------------------------------");
	}

	/**
	 * Display a Book's Details
	 * @param isbn
	 * @throws Exception
	 */
	private static void showBookDetails(String isbn) throws Exception {
		if (isbn == null || isbn.trim().isEmpty()) {
			System.out.println("Missing ISBN.");
		} else {
			System.out.println("\tBook Details");
			try {
				Book book = BookOperations.getBook(isbn);
				System.out.printf("Title:\t%s\nAuthor:\t%s\nISBN:\t%s\nGenre:\t%s\nDescription:\t%s\nYear of publication:\t%d\nRating:\t%.1f stars\nTags:\t%s\n",
						book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.getDescription().replace("\\n", "\n"), book.getYear(), book.getRating(), String.join(",", book.getTags()));
			} catch (BookException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param input the whole user input
	 * @param command the used command that will be ignored while extracting the options after it
	 * @return the options part of the input
	 */
	private static String extractOptions(String input, String command) {
		int optionsStartIndex = input.indexOf(command) + command.length() + 1;
		if (optionsStartIndex < 0 || optionsStartIndex > input.length()) return null;
		return input.substring(optionsStartIndex).trim();
	}

	private static void showHelp() throws Exception {
		System.out.println("\tAvailable features");
		String help = CommonOperations.help();
		System.out.println(help);
	}
}
