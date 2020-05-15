import java.util.*;

public class Member {

    public final int DURATION = 1;

    private int memberID;
    private MembershipEnum membershipType;
    private String firstName;
    private String lastName;
    private String address;
    private int phone;
    private String password;
    private GregorianCalendar startDateMembership;
    private GregorianCalendar endDateMembership;


    public Member() {

    }

    public Member(String name, String address, int phone) {
        this.membershipType = MembershipEnum.NORMAL;
        setLastName(name);
        setAddress(address);
        setPhone(phone);
        startDateMembership = new GregorianCalendar();
        endDateMembership = new GregorianCalendar();
        endDateMembership.add(GregorianCalendar.YEAR, DURATION);
    }

    public Member(int memberID, String name, String address, int phone) {
        this.membershipType = MembershipEnum.NORMAL;
        setMemberID(memberID);
        setLastName(name);
        setAddress(address);
        setPhone(phone);
        startDateMembership = new GregorianCalendar();
        endDateMembership = new GregorianCalendar();
        endDateMembership.add(GregorianCalendar.YEAR, DURATION);
    }

    public int getMemberID() {//needs to be modified
        return this.memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public MembershipEnum getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipEnum membershipType) {
        this.membershipType = membershipType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public GregorianCalendar getStartDateMembership() {
        return startDateMembership;
    }

    public GregorianCalendar getEndDateMembership() {
        return endDateMembership;
    }

    @Override
    public String toString() {
        return "Member{" +
                "DURATION=" + DURATION +
                ", memberID=" + memberID +
                ", membershipType=" + membershipType.name() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", startDateMembership=" + startDateMembership.getTime() +
                ", endDateMembership=" + endDateMembership.getTime() +
                '}';
    }

    public Member createMember(Scanner console) {


        System.out.println("Provide last name");
        String name = console.next();

        System.out.println("Provide address");
        String address = console.next();

        System.out.println("Provide phone");
        int phone = console.nextInt();

        Member member = new Member(name, address, phone);

        return member;
    }

    public void chooseMembershipType(Scanner console, Member member) {

        int choice;
        System.out.println("Provide type of membership:");// user input should be restricted to enums
        choice = console.nextInt();
        switch (choice) {
            case 1:
                member.membershipType = MembershipEnum.JUNIOR;
                break;
            case 2:
                member.membershipType = MembershipEnum.STUDENT;
                break;
            case 3:
                member.membershipType = MembershipEnum.SENIOR;
                break;
        }
    }
}




