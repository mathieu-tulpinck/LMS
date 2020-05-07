import java.util.*;

// class used to coordinate business logic
public class Library {
    private ArrayList<Member> memberList;
    private ArrayList<Book> catalog;

    public final int borrowDuration = 0;// constants regarding working of library. To be initialized.
    public final int reservationDuration = 0;

    public Library() {// constructor design to be modified to match singleton pattern
        memberList = new ArrayList<Member>();
        catalog = new ArrayList<Book>();
    }

    public Member addMember(String name, String address, int phone) {
        Member member = new Member(name, address, phone);


        return member;
    }
    public boolean insertMember(Member member) {
        memberList.add(member);

    }






}
