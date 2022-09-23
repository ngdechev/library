package library.utils;

import library.model.Book;

public class BookForm {
	
	public ConsoleUserInterface ui;
	public Book book;
	
	public BookForm(ConsoleUserInterface ui) {
		this.book = new Book();
		this.ui = ui;
	}

	/**
	 * Begins to prompt user for each property required for a Book object. Will ask for a property more than once until a valid input is given.
	 * @return The new Book object created from user input.
	 */
	public Book read() {
		this
			.title()
			.author()
			.isbn()
			.genre()
			.year()
			.rating()
			.description()
			.tags();
		
		return book;
	}
	
	private BookForm title() {
		boolean valid = false;
		do {
			String title = ui.readLine("Title:");
			if (title != null) {
				title = title.trim();
				if (!title.isEmpty()) {
					valid = true;
					this.book.setTitle(title);
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm author() {
		boolean valid = false;
		do {
			String author = ui.readLine("Author:");
			if (author != null) {
				author = author.trim();
				if (!author.isEmpty()) {
					valid = true;
					this.book.setAuthor(author);
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm isbn() {
		boolean valid = false;
		do {
			String isbn = ui.readLine("ISBN:");
			if (isbn != null) {
				isbn = isbn.trim();
				if (!isbn.isEmpty()) {
					valid = true;
					this.book.setIsbn(isbn);
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm genre() {
		boolean valid = false;
		do {
			String genre = ui.readLine("Genre:");
			if (genre != null) {
				genre = genre.trim();
				if (!genre.isEmpty()) {
					valid = true;
					this.book.setGenre(genre);
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm year() {
		boolean valid = false;
		do {
			String year = ui.readLine("Year:");
			if (year != null) {
				year = year.trim();
				if (!year.isEmpty()) {
					try {
						int yearNumber = Integer.parseInt(year);
						valid = true;
						this.book.setYear(yearNumber);
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm rating() {
		boolean valid = false;
		do {
			String rating = ui.readLine("Rating:");
			if (rating != null) {
				rating = rating.trim();
				if (!rating.isEmpty()) {
					try {
						float ratingScore = Float.parseFloat(rating);
						if (ratingScore > 10) {
							System.out.println("Maximum allowed rating is 10.");
							continue;
						}
						valid = true;
						this.book.setRating(ratingScore);
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm description() {
		boolean valid = false;
		do {
			String description = ui.readLine("Description (use \\n before new paragraph to add new line):");
			if (description != null) {
				description = description.trim();
				if (!description.isEmpty()) {
					valid = true;
					this.book.setDescription(description);
				}
			}
		} while (!valid);
		return this;
	}

	private BookForm tags() {
		boolean valid = false;
		do {
			String tagsList = ui.readLine("Tags (separate with a comma):");
			if (tagsList != null) {
				tagsList = tagsList.trim();
				if (!tagsList.isEmpty()) {
					String[] splitTags = tagsList.toLowerCase().split(",");
					for (String tag : splitTags) {
						tag = tag.trim();
						this.book.getTags().add(tag); // tags list is always initialized in a Book instance so we use the reference to it
					}
					valid = true;
				}
			}
		} while (!valid);
		return this;
	}
}
