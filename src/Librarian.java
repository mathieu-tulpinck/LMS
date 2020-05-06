public class Librarian {

    private int employeeID;
    private String name;
    private String address;
    private int phone;

    public Librarian(int employeeID, String name, String address, int phone) {
        setEmployeeID(employeeID);
        setName(name);
        setAddress(address);
        setPhone(phone);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                '}';
    }
}
