package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a single book that can be lent out by a {@link Library}.
 * A Book is solely responsible for its own identifying information
 * (ISBN, title, author) and its own availability state. It does not
 * know about any Library that may hold it; all state transitions are
 * triggered on the Book itself and validated internally, so that
 * availability can never be changed except through {@link #borrowBook()}
 * and {@link #returnBook()}.
 *
 * @author Kyle Dill
 */
public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    /**
     * Creates a new Book. New books always start out available.
     *
     * @param isbn   the book's unique ISBN
     * @param title  the book's title
     * @param author the book's author
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns this book's ISBN.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns this book's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns this book's author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Indicates whether this book is currently available to be borrowed.
     *
     * @return true if available, false if currently borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Marks this book as borrowed. Only an available book may be borrowed.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException(
                    "Book " + isbn + " is already borrowed and cannot be borrowed again.");
        }
        available = false;
    }

    /**
     * Marks this book as returned (available again). Only a currently
     * borrowed book may be returned.
     *
     * @throws IllegalStateException if the book is not currently borrowed
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException(
                    "Book " + isbn + " is already available and cannot be returned.");
        }
        available = true;
    }

    /**
     * Returns a human-readable representation of this book, including its
     * ISBN, title, author, and current availability status.
     * Example: {@code B101 Effective Java by Joshua Bloch [AVAILABLE]}.
     *
     * @return the formatted string representation of this book
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author
                + (available ? " [AVAILABLE]" : " [BORROWED]");
    }
}