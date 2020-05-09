import java.util.ArrayList;
Class.forName()

public class Book {
    private int book_ID;
    private int cat_ID;
    private String author;
    private String publisher;
    private String title;
    private int ISBN;
    private int year;
    private String location;
    private String condition;
    private BookState state;



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
