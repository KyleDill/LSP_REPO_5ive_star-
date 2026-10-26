package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a collection of {@link Book} instances and coordinates
 * lending operations against them. Library is responsible for knowing
 * which books it holds, looking them up by ISBN, and enforcing
 * library-level lending policy (at most three books borrowed at once,
 * no duplicate ISBNs). It delegates the actual availability state
 * change to the Book itself, since only a Book may change its own
 * availability.
 *
 * <p>Books are stored internally in a {@link LinkedHashMap} keyed by
 * ISBN, which gives O(1) lookup by ISBN for {@link #findBook(String)}
 * while preserving insertion order for iteration.</p>
 *
 * @author Kyle Dill
 */
public class Library {

    private static final int MAX_BORROWED_BOOKS = 3;

    private final Map<String, Book> booksByIsbn = new LinkedHashMap<>();

    /**
     * Adds a book to the library's collection.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if a book with the same ISBN is
     *                                   already present; the original book
     *                                   is preserved unchanged
     */
    public void addBook(Book book) {
        if (booksByIsbn.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException(
                    "A book with ISBN " + book.getIsbn() + " already exists in the library.");
        }
        booksByIsbn.put(book.getIsbn(), book);
    }

    /**
     * Finds the book with the given ISBN.
     *
     * @param isbn the ISBN to search for
     * @return the matching Book instance, or {@code null} if no book with
     *         that ISBN is in the library
     */
    public Book findBook(String isbn) {
        return booksByIsbn.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN from this library. The library
     * locates the book and delegates the actual state change to the Book;
     * the library itself only enforces the three-book borrowing limit.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book with that ISBN exists
     * @throws IllegalStateException    if the book is already borrowed, or
     *                                   if three books are already borrowed
     *                                   from this library
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);

        if (!book.isAvailable()) {
            throw new IllegalStateException(
                    "Book " + isbn + " is already borrowed.");
        }
        if (getBorrowedBookCount() >= MAX_BORROWED_BOOKS) {
            throw new IllegalStateException(
                    "Cannot borrow book " + isbn + ": the library-wide limit of "
                            + MAX_BORROWED_BOOKS + " borrowed books has been reached.");
        }

        book.borrowBook();
    }

    /**
     * Returns the book with the given ISBN to this library. The library
     * locates the book and delegates the actual state change to the Book.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book with that ISBN exists
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        Book book = requireBook(isbn);

        if (book.isAvailable()) {
            throw new IllegalStateException(
                    "Book " + isbn + " is already available and cannot be returned.");
        }

        book.returnBook();
    }

    /**
     * Returns all books in this library that are currently available.
     *
     * @return a List of available books; empty if none are available
     */
    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book book : booksByIsbn.values()) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    /**
     * Returns all books in this library whose author matches the given
     * author exactly (case-sensitive), including currently borrowed books.
     *
     * @param author the author name to match exactly
     * @return a List of matching books; empty if none match
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> matches = new ArrayList<>();
        for (Book book : booksByIsbn.values()) {
            if (book.getAuthor().equals(author)) {
                matches.add(book);
            }
        }
        return matches;
    }

    /**
     * Returns all books in this library sorted by ISBN in ascending
     * lexicographical (String) order, regardless of insertion order or
     * borrowing status. Does not modify the library's contents or any
     * book's state.
     *
     * @return a List of all books sorted by ISBN; empty if the library
     *         has no books
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> sorted = new ArrayList<>(booksByIsbn.values());
        Collections.sort(sorted, Comparator.comparing(Book::getIsbn));
        return sorted;
    }

    /**
     * Returns the number of books currently borrowed from this library.
     *
     * @return the count of currently borrowed books
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : booksByIsbn.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Looks up a book by ISBN, throwing if it is not present in this
     * library. Used internally by borrow/return to share the "unknown
     * ISBN" validation.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book
     * @throws IllegalArgumentException if no book with that ISBN exists
     */
    private Book requireBook(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException(
                    "No book with ISBN " + isbn + " exists in this library.");
        }
        return book;
    }
}