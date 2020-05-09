package library;

import java.util.*;

public class Member {
    private int membershipID;
    private String name;
    private String address;
    private int phone;

    static int currentMembershipID = 0;

    public Member(String name, String address, int phone) {
        currentMembershipID++;
        this.membershipID = currentMembershipID;
        setName(name);
        setAddress(address);
        setPhone(phone);
    }

    public int getMembershipID(){
        return this.membershipID;
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

    public void printInfo() {
        System.out.println("membershipID = " + membershipID);
        System.out.println("name = " + name);
        System.out.println("address = " + address);
        System.out.println("phone = " + phone);
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


