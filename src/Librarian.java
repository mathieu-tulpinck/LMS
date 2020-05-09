import db.LibrarianDAO;
import gui.LoginGUI;

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
        LoginGUI.showLoginScreen();



        /*LibrarianDAO ldao = new LibrarianDAO();
        ldao.verifyUserPassword("ej","elias123");
        ldao.loadLibrarian("ej","elias123");*/
    }
}
