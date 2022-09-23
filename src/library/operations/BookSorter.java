package library.operations;

import java.util.Arrays;
import java.util.List;

import library.model.Book;

public class BookSorter {

	/**
	 * Sort a list of Book objects using the Cocktail sorting method.
	 * @param books
	 * @param field The name of the Book field to sort by
	 * @param direction Allowed values: <i>asc</i> or <i>desc</i>
	 * @return Sorted list of Book objects.
	 */
	public static List<Book> sort(List<Book> books, String field, String direction) {
		Book[] booksArr = books.toArray(new Book[books.size()]);
		int n = booksArr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if ((booksArr[j].compareByField(booksArr[j + 1], field) > 0 && "asc".equals(direction)) 
                		|| (booksArr[j].compareByField(booksArr[j + 1], field) < 0 && "desc".equals(direction))) {
                    Book temp = booksArr[j];
                    booksArr[j] = booksArr[j + 1];
                    booksArr[j + 1] = temp;
                }
            }
        }

        List<Book> booksList = Arrays.asList(booksArr);
        return booksList;
    }
}
