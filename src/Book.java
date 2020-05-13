import java.util.GregorianCalendar;

public class Book {
    private int book_ID;
    private String title;
    private String author;
    private Member borrowedBy;
    private GregorianCalendar dueDate;

    public Book(String title, String author) {
        this.book_ID = book_ID;
        this.title = title;
        this.author = author;
        this.borrowedBy = borrowedBy;
        this.dueDate = dueDate;
    }

    public int getBook_ID() {
        return book_ID;
    }

    public void setBook_ID(int book_ID) {
        this.book_ID = book_ID;
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
                "book_ID=" + book_ID +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", borrowedBy=" + borrowedBy +
                ", dueDate=" + dueDate +
                '}';
    }

    public boolean issue(Member member) {

        return true;
    }
}


