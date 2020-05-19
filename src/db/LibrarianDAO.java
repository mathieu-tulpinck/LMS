package db;

import java.sql.*;

import library.Librarian;
import security.*;

public class LibrarianDAO extends BaseDAO {

    public boolean verifyUserPassword(String username, String password) {
        try (Connection c = getConn()) {
            Statement s = c.createStatement();
            String stringQuery = "SELECT password FROM Librarian WHERE username = '" + username + "'";
            //System.out.println(stringQuery);
            ResultSet rs = s.executeQuery(stringQuery);
            while (rs.next()) {
                String saltedPassword = rs.getString("password");
                PasswordAuthentication pwa = new PasswordAuthentication(10);
                return pwa.authenticate(password, saltedPassword);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Something went wrong, please try again");
            return false;
        }
        return false;
    }

    public int addLibrarian(Librarian librarian) {

        int primaryKey = 0;

        PasswordAuthentication pwa = new PasswordAuthentication(10);
        String tokenSaltedPassword = pwa.hash(librarian.getPassword());


        String query = "INSERT INTO Librarian" +
                "(FirstName, LastName, Username, Password)" +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = getConn();//try-with-resources statement
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, librarian.getFirstName());
            statement.setString(2, librarian.getLastName());
            statement.setString(3, librarian.getUsername());
            statement.setString(4, tokenSaltedPassword);

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    primaryKey = resultSet.getInt(1);
                }
            }

            return primaryKey;

        } catch (SQLException throwables) {
            System.out.println("Librarian not created, something went wrong along the way!");
            throwables.printStackTrace();

            return 0;
        }
    }
}