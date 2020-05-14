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

    public Member(int memberID, String name, String address, int phone) {
        setMemberID(memberID);
        setName(name);
        setAddress(address);
        setPhone(phone);
    }

    public int getMemberID() {//needs to be modified
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

    public boolean issue(Book book) {

        return true;
    }
}



