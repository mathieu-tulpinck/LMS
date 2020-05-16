package db;

import library.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDAO extends BaseDAO {

    public void addBook(Book b) {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            int result = s.executeUpdate("insert into Book values("+b.getBook_ID() +" ,"+ b.getCat_ID() +" ,'"+b.getAuthor() +"','"+b.getPublisher() +"','"+b.getTitle() +"',"+b.getISBN() +" ,"+b.getYear() +" ,'"+b.getLocation() +"')");
            if(result>0)
                System.out.println("Gelukt!");
            else System.out.println("MISLUKT!");
        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("MISLUKT JONGE!");
        }
    }
}