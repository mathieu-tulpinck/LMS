package db;

import library.BookCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BookCategoryDAO extends BaseDAO {

    public void addCategory (BookCategory category)
    {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            int result = s.executeUpdate("insert into Category values("+category.getCategory_ID() +" ,'"+ category.getName() +"')");
            if(result>0)
                System.out.println("Gelukt!");
            else System.out.println("MISLUKT!");
        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("MISLUKT !!!");
        }
    }
}
