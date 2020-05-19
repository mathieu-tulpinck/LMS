package library;

import java.util.Scanner;

public class Book {

    private int bookID;
    private String title;
    private String author;
    private BookStateEnum bookState;


    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.bookState = BookStateEnum.AVAILABLE;
    }

    public Book(int bookID, String title, String author, BookStateEnum bookState) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.bookState = bookState;
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

    public BookStateEnum getBookState() {
        return bookState;
    }

    public void setBookState(BookStateEnum bookState) {
        this.bookState = this.bookState;
    }



    @Override
    public String toString() {
        return String.format("%-15s %-6d\n", "BookID:", bookID) +
                String.format("%-15s %-50s\n","Title:", title) +
                String.format("%-15s %-50s\n", "Author:", author) +
                String.format("%-15s %-10s\n", "BookState:", bookState.name());
    }

    public Book createBook(Scanner console) {

        String title ,author;
        console.useDelimiter(";|\r?\n|\r");
        System.out.println("Provide title:");
        title = console.next();
        System.out.println("Provide author:");
        author = console.next();

        Book book = new Book(title, author);

        return book;
    }
}