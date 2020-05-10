package library;

import db.BookDAO;
import db.BookCategoryDAO;

public class Library {
        private static final int NULL = 0;

        public static void main(String[] args) {
                //  LoginGUI.showLoginScreen();

                //Book b5 = new Book(NULL, 1, "Gill Lewis", "Lannoo", "Kunanjango", 8544, 1998, "A24");
                //BookDAO bdao = new BookDAO();
                //bdao.addBook(b5);

                //BookCategory cat1 = new BookCategory(1,"Actie");
                //BookCategory cat2 = new BookCategory(2,"Fantasie");
                //BookCategory cat3 = new BookCategory(3,"SF");
                BookCategory cat4 = new BookCategory(4,"Jeugd");
                BookCategoryDAO bcdao = new BookCategoryDAO();
                bcdao.addCategory(cat4);

                /*LibrarianDAO ldao = new LibrarianDAO();
                ldao.verifyUserPassword("ej","elias123");
                ldao.loadLibrarian("ej","elias123");*/
                }
}


//import java.util.*;

// class used to coordinate business logic
/*public class Library {
    private ArrayList<Library.Member> memberList;
    private ArrayList<db.Library.Book> catalog;

    public final int borrowDuration = 0;// constants regarding working of library. To be initialized.
    public final int reservationDuration = 0;

    public Library() {// constructor design to be modified to match singleton pattern
        memberList = new ArrayList<Library.Member>();
        catalog = new ArrayList<db.Library.Book>();
    }

    public Library.Member addMember(String name, String address, int phone) {
        Library.Member member = new Library.Member(name, address, phone);


        return member;
    }
    public boolean insertMember(Library.Member member) {
        memberList.add(member);

    }

}
*/