package library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for list of Book objects in XML file
 *
 */
@XmlRootElement
public class Books {

	private List<Book> books = new ArrayList<>();
	
	public Books() {	}

	public Books(List<Book> books) {
		this.setBooks(books);
	}

	@XmlElement(name="book")
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
