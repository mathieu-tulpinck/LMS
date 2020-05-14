package library;

public class Book {
    private int book_ID;
    private int cat_ID;
    private String author;
    private String publisher;
    private String title;
    private int ISBN;
    private int year;
    private String location;

    public Book(int book_ID, int cat_ID, String author, String publisher, String title, int ISBN, int year, String location) {
        this.book_ID = book_ID;
        this.cat_ID = cat_ID;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
        this.ISBN = ISBN;
        this.year = year;
        this.location = location;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public int getBook_ID() {
        return book_ID;
    }

    public void setBook_ID(int book_ID) {
        this.book_ID = book_ID;
    }

    public int getCat_ID() {
        return cat_ID;
    }

    public void setCat_ID(int cat_ID) {
        this.cat_ID = cat_ID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean issue(Member member){

        return true;
    }
}