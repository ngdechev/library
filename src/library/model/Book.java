package library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {

	private String title;
	private String author;
	private String isbn; // may contain letters
	private String description;
	private int year;
	private List<String> tags = new ArrayList<>();
	private float rating;
	private String genre;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@XmlElement
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@XmlElement
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@XmlElementWrapper(name="tags")
    @XmlElement(name = "tag")
    public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@XmlElement
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int compareByField(Book book, String field) {
        if (field.equals("title")) {
            return this.title.compareTo(book.title);
        } else if (field.equals("author")) {
            return this.author.compareTo(book.author);
        } else if (field.equals("isbn")) {
            return this.isbn.compareTo(book.isbn);
        } else if (field.equals("rating")) {
            return Math.round(this.rating - book.rating);
        } else if (field.equals("year")) {
            return this.getYear() - book.getYear();
        } else {
            return 0;
        }
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", isbn=" + isbn + ", description=" + description
				+ ", year=" + year + ", tags=" + tags + ", rating=" + rating + ", genre=" + genre + "]";
	}
	
}
