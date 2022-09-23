package library.utils;

/**
 * Class that contains values that are used throughout the application more than once, or are part of a configuration.
 */
public class Constants {

	// FIXME: Set the correct path to the XML files
	public static class Files {
		public static final String BOOKS_DB_XML = "storage/books.xml";
		public static final String USERS_DB_XML = "storage/users.xml";
	}

	public static class Messages {
		public static final String NO_RIGHTS = "You do not have rights to do this.";
		public static final String INVALID_INPUT ="Missing options. Make sure your input looks like the following:";
		public static final String UNKNOWN_COMMAND = "Unknown command. Type \"help\" to see the list of available commands.";
	}
	
	public static class Commands {
//		public static final String OPEN = "open";
//		public static final String CLOSE = "close";
//		public static final String SAVE = "save";
//		public static final String SAVE_AS = "saveas";
		public static final String HELP = "help";
		
		public static final String LOGIN = "login";
		public static final String LOGOUT = "logout";
		
		public static final String EXIT = "exit";
		
		public static final String BOOKS_ALL = "books all";
		public static final String BOOKS_FIND = "books find";
		public static final String BOOKS_SORT = "books sort";
		public static final String BOOKS_VIEW = "books view";
		public static final String BOOKS_INFO = "books info";
		public static final String BOOKS_ADD = "books add";
		public static final String BOOKS_REMOVE = "books remove";

		public static final String USERS_ADD = "users add";
		public static final String USERS_REMOVE = "users remove";
	}

}
