import java.util.ArrayList;

public class Book {
    private int book_ID;
    private int cat_ID;
    private String author;
    private String publisher;
    private String title;
    private int ISBN;
    private int year;
    private String location;
    private ArrayList<BookCopy> copies;

    public Book(int book_ID, int cat_ID, String author, String publisher, String title, int ISBN, int year, String location, ArrayList<BookCopy> copies) {
        copies = new ArrayList<>();
        this.book_ID = book_ID;
        this.cat_ID = cat_ID;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
        this.ISBN = ISBN;
        this.year = year;
        this.location = location;
        this.copies = copies;
    }

    public Book(int book_ID, ArrayList<BookCopy> copies) {
        copies = new ArrayList<>();
        this.book_ID = book_ID;
        this.copies = copies;
    }

    public boolean makeReservation(Book a) {
        for ( BookCopy copy : copies) {
            if (copy.getState().equals("Free")){
                System.out.println("Uw reservatie is gemaakt");
                return true;
            }
        }
        return false;
    }
}
