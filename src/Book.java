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

    public Book(int book_ID, int cat_ID, String author, String publisher, String title, int ISBN, int year, String location, String condition, BookState state) {
        this.book_ID = book_ID;
        this.cat_ID = cat_ID;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
        this.ISBN = ISBN;
        this.year = year;
        this.location = location;
        this.condition = condition;
        this.state = state;
    }

    public Book(int book_ID, String title, BookState state) {
        this.book_ID = book_ID;
        this.title = title;
        this.state = state;
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

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

 /*   public void addBook(Book b) {
        b.setBook_ID(1);
        b.setTitle("Kunanjango");
        b.setBookState("Free");
    }*/
}
