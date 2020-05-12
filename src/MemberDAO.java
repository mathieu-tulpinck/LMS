import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO extends BaseDAO {

    public void addMember(Member m) {
        try (Connection c = getConn()) {
            Statement s = c.createStatement();
            int result = s.executeUpdate("insert into Test values(1)");
            if (result > 0)
                System.out.println("Success");
            else System.out.println("Failure");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.out.println("Failure!");
        }
    }
}


