package library;

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

    public Member(String firstName, String lastName, String address, int phone) {
        this.membershipType = MembershipEnum.NORMAL;
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhone(phone);
        startDateMembership = new GregorianCalendar();
        endDateMembership = new GregorianCalendar();
        endDateMembership.add(GregorianCalendar.YEAR, DURATION);
    }

    public Member(int memberID, String mb, GregorianCalendar startDateMembership, GregorianCalendar endDateMembership) {
        this.memberID = memberID;
        this.startDateMembership = startDateMembership;
        this.endDateMembership = endDateMembership;
        System.out.println(mb);
        switch (mb) {
            case "STUDENT":
                this.membershipType = MembershipEnum.STUDENT;
                break;
            case "SENIOR":
                this.membershipType = MembershipEnum.SENIOR;
                break;
            case "JUNIOR":
                this.membershipType = MembershipEnum.JUNIOR;
                break;
            case "NORMAL":
            default:
                this.membershipType = MembershipEnum.NORMAL;
                break;
        }
    }

    public Member(int memberID, MembershipEnum membershipType, String name, String address, int phone, GregorianCalendar startDate, GregorianCalendar endDate) {
        this.membershipType = membershipType;
        setMemberID(memberID);
        setLastName(name);
        setAddress(address);
        setPhone(phone);
        startDateMembership = startDate;
        endDateMembership = endDate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-6d\n", "Member ID: ", memberID) +
                String.format("%-20s %-50s\n", "First Name:", firstName) +
                String.format("%-20s %-50s\n", "Last Name:", lastName) +
                String.format("%-20s %-50s\n", "Address:", address) +
                String.format("%-20s %-50s\n", "Password:", phone) +
                String.format("%-20s %-50s\n", "Start membership:", startDateMembership.getTime()) +
                String.format("%-20s %-50s\n", "End membership:", endDateMembership.getTime());

    }

    public Member createMember(Scanner console) {

        System.out.println("Provide first name");
        String firstName = console.next();

        System.out.println("Provide last name");
        String lastName = console.next();

        System.out.println("Provide address");
        String address = console.next();

        System.out.println("Provide phone");
        int phone = console.nextInt();

        Member member = new Member(firstName, lastName, address, phone);

        return member;
    }

    // method used to determine the Membership Type of a Member
    public void chooseMembershipType(Scanner console, Member member) {

        int choice;
        System.out.println("Provide type of membership: 1 = junior, 2 = student, 3 = senior");// user input should be restricted to enums
        choice = console.nextInt();
        while (!(choice > 0) || !(choice < 4)) {
            System.out.println("Input invalid. Try again");
            choice = console.nextInt();
        }
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

    // method returns true if the membership of a Member is still valid
    public boolean checkMembershipValidity(Member borrower) {
        GregorianCalendar currentDate = new GregorianCalendar();

        if (borrower.getEndDateMembership().compareTo(currentDate) > 0) {
            System.out.println("Membership valid.");
            return true;
        } else {
            System.out.println("Membership expired. Ask member whether membership should be renewed");
            return false;
        }

    }
}