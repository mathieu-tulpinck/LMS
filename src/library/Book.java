package library;l

import java.util.Scanner;

public class Book {

    private int bookID;
    private String title;
    private String author;
    private BorrowState borrowState;


    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrowState = BorrowState.AVAILABLE;
    }

    public Book(int bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.borrowState = BorrowState.AVAILABLE;
    }

    public int getBook_ID() {
        return bookID;
    }

    public void setBook_ID(int book_ID) {
        this.bookID = book_ID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BorrowState getBorrowState() {
        return borrowState;
    }

    public void setBorrowState(BorrowState borrowState) {
        this.borrowState = borrowState;
    }

    @Override
    public String toString() {
        return "library.Book{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowState=" + borrowState.name() +
                '}';
    }

    public Book createBook(Scanner console) {

        String title, author;

        System.out.println("Provide title:");
        title = console.next();
        System.out.println("Provide author:");
        author = console.next();

        Book book = new Book(title, author);

        return book;
    }
}