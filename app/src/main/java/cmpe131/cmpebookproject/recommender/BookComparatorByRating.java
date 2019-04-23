package cmpe131.cmpebookproject.recommender;

import java.util.Comparator;

import cmpe131.cmpebookproject.book.Book;

public class BookComparatorByRating implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return (int)(b1.getRating() - b2.getRating());
    }
}
