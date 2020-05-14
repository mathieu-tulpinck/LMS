package library;

import java.util.*;

public class Membership {

    public final int DURATION = 1;

    private int membershipID;
    private int memberID;
    private MembershipType membershipType;
    private int price;
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;

    public Membership(MembershipType membershipType) {
        this.memberID = 0;
        this.membershipType = membershipType;
        if (this.membershipType == membershipType.JUNIOR) {
            setPrice(5);
        } else if (this.membershipType == membershipType.STUDENT) {
            setPrice(10);
        } else if (this.membershipType == membershipType.NORMAL) {
            setPrice(15);
        } else if (this.membershipType == membershipType.SENIOR) {
            setPrice(10);
        }
        this.startDate = new GregorianCalendar();
        this.endDate = new GregorianCalendar();
        this.endDate.add(GregorianCalendar.YEAR, DURATION);
    }

    public int getMembershipID() {
        return membershipID;
    }

    public Membership(int membershipID, int memberID, MembershipType membershipType, int price, GregorianCalendar startDate, GregorianCalendar endDate) {
        this.membershipID = membershipID;
        this.memberID = memberID;
        this.membershipType = membershipType;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }
}