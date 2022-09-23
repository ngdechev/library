package library.operations;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import library.exceptions.BookException;
import library.exceptions.LibraryException;
import library.model.Book;
import library.model.Books;
import library.utils.Constants.Files;
import library.xml.XmlReader;

public class BookOperations {

	public static void addBook(Book newBook) throws Exception {
		List<Book> books = getAllBooks();
		books.add(newBook);
		Books updatedBooks = new Books(books);
		updateBooksDb(updatedBooks);
	}

	public static List<Book> getAllBooks() throws JAXBException, Exception {
		Books booksList = getBooks();
		List<Book> books = booksList.getBooks();
		
		return books;
	}

	public static Book getBook(String isbn) throws JAXBException, Exception {
		List<Book> searchBook = searchBooks("isbn", isbn);
		if (searchBook.isEmpty()) {
			throw new BookException(String.format("Book with ISBN %s was not found.", isbn));
		} return searchBook.get(0);
	}

	public static void deleteBook(String isbn) throws Exception {
		List<Book> books = getAllBooks();
		Book bookToDelete = null;
		for (Book book : books) {
			if (book.getIsbn().equals(isbn)) {
				bookToDelete = book;
				break;
			}
		}
		if (bookToDelete == null) 
			throw new BookException(String.format("Book with ISBN %s does not exist.", isbn));
	
		books.remove(bookToDelete);
		
		Books updatedBooks = new Books(books);
		updateBooksDb(updatedBooks);
	}

	public static List<Book> sortBooks(String property, String direction) throws JAXBException, Exception {
		List<String> sortableProperties = Arrays.asList("title", "author", "year", "rating");
		if (!sortableProperties.contains(property)) {
			throw new LibraryException("Cannot sort by this property. Allowed properties: title, author, year, rating.");
		}
		List<Book> sortedBooks = BookSorter.sort(getAllBooks(), property, direction);
		return sortedBooks;
	}
	
	public static List<Book> searchBooks(String field, String value) throws JAXBException, Exception {
		List<Book> allBooks = getAllBooks();
		List<Book> searchResults = new ArrayList<Book>();
		switch (field) {
			case "title":
				searchByTitle(value, allBooks, searchResults);
				break;
			case "author":
				searchByAuthor(value, allBooks, searchResults);
				break;
			case "tag":
				searchByTag(value, allBooks, searchResults);
				break;
			case "isbn":
				searchByIsbn(value, allBooks, searchResults);
				break;
			default: 
				throw new LibraryException("Cannot search by this property. Allowed properties: title, author, tag, isbn.");
		}
		return searchResults;
	}

	private static Books getBooks() throws JAXBException, Exception {
		JAXBContext jc = JAXBContext.newInstance(Books.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
	
		XMLStreamReader xsr = XmlReader.readXml(Files.BOOKS_DB_XML);
		Books element = (Books) unmarshaller.unmarshal(xsr);
		return element;
	}

	private static void updateBooksDb(Books updatedBooks) throws Exception {
		JAXBContext contextObj = JAXBContext.newInstance(Books.class);
	
		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	
		marshallerObj.marshal(updatedBooks, new FileOutputStream(Files.BOOKS_DB_XML));
	}

	private static void searchByTag(String value, List<Book> allBooks, List<Book> searchResults) {
		for (Book book : allBooks) {
			if (book.getTags().contains(value.toLowerCase())) {
				searchResults.add(book);
			}
		}		
	}

	private static void searchByAuthor(String value, List<Book> allBooks, List<Book> searchResults) {
		for (Book book : allBooks) {
			if (book.getAuthor().toLowerCase().contains(value.toLowerCase())) {
				searchResults.add(book);
			}
		}		
	}

	private static void searchByTitle(String value, List<Book> allBooks, List<Book> searchResults) {
		for (Book book : allBooks) {
			if (book.getTitle().toLowerCase().contains(value.toLowerCase())) {
				searchResults.add(book);
			}
		}
	}

	private static void searchByIsbn(String value, List<Book> allBooks, List<Book> searchResults) {
		for (Book book : allBooks) {
			if (book.getIsbn().toLowerCase().contains(value.toLowerCase())) {
				searchResults.add(book);
			}
		}	
	}
	
}
