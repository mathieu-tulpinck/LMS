import java.util.GregorianCalendar;

public class Book {

    private int bookID;
    private String title;
    private String author;

    public Book(String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
    }

    public Book(int bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {// to be polished.
        return "Book{" +
                "book_ID=" + bookID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public boolean issue(Member member) {

        return true;
    }

}


