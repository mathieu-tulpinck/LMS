package library;l

public class Librarian {

    private int librarianID;
    private String name;

    public Librarian(int employeeID, String name, String address, int phone) {
        setEmployeeID(employeeID);
        setName(name);
    }

    public int getEmployeeID(){
        return this.librarianID;
    }

    public void setEmployeeID(int employeeID) {
        this.librarianID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void printInfo() {
        System.out.println("employeeID = " + librarianID);
        System.out.println("name = " + name);
    }
}
