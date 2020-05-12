import java.util.*;

public class Member {
    private int memberID;
    private String name;
    private String address;
    private int phone;


    public Member(String name, String address, int phone) {
        setName(name);
        setAddress(address);
        setPhone(phone);
    }

    public int getMemberID(){//needs to be modified
        return this.memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
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
    public String toString() {// to be polished
        return "Member{" +
                "memberID=" + memberID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                '}';
    }

    public void updateBorrowInfo() {
        System.out.println("Which information do you wish to modify: 1. name / 2. address / 3. phone");

        Scanner console = new Scanner(System.in);
        int choice = console.nextInt();

        while(choice < 0 && choice > 3) {
            switch (choice) {
                case 1:
                    System.out.println("Provide new name: ");
                    String name = console.next();
                    setName(name);
                    System.out.println("Update executed.");
                    break;
                case 2:
                    System.out.println("Provide new address: ");
                    String address = console.next();
                    setAddress(address);
                    System.out.println("Update executed.");
                    break;
                case 3:
                    System.out.println("Provide new phone: ");
                    int phone = console.nextInt();
                    setPhone(phone);
                    System.out.println("Update executed.");
                    break;
            }
        }


    }


}


