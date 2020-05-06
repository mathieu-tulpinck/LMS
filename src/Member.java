public class Member {
    private int membershipID;
    private String name;
    private String address;
    private int phone;

    public Member(int membershipID, String name, String address, int phone) {
        setMembershipID(membershipID);
        setName(name);
        setAddress(address);
        setPhone(phone);
    }

    public int getMembershipID(){
        return this.membershipID;
    }

    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
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
        return "Member{" +
                "membershipID=" + membershipID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                '}';
    }
}


