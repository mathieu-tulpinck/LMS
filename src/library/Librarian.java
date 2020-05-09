package library;

import db.BookDAO;


public class Librarian {

    private int employeeID;
    private String name;

    public Librarian(int employeeID, String name, String address, int phone) {
        setEmployeeID(employeeID);
        setName(name);
    }

    public int getEmployeeID(){
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void printInfo() {
        System.out.println("employeeID = " + employeeID);
        System.out.println("name = " + name);
    }

    public static void main(String[] args) {
      //  LoginGUI.showLoginScreen();


    //BookCategory cat3 = new BookCategory(3,"SF");
        Book b1 = new Book(1,1,"Jos","Lannoo","Komaan",12345,2001,"A3");
        BookDAO bdao = new BookDAO();
        bdao.addBook(b1);

    BookCategory cat3 = new BookCategory(1,"Actie");
    BookCategory cat2 = new BookCategory(2,"Fantasie");

    //BookDAO bda1 = new BookDAO();
    //bda1.addCategory(cat2);

        /*LibrarianDAO ldao = new LibrarianDAO();
        ldao.verifyUserPassword("ej","elias123");
        ldao.loadLibrarian("ej","elias123");*/
    }
}
