package db;

import library.Book;
import library.BookCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDAO extends BaseDAO {

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

    public void addBook(Book boek)
    {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            int result = s.executeUpdate("insert into Book values("+boek.getBook_ID() +" ,"+ boek.getCat_ID() +" ,'"+boek.getAuthor() +"','"+boek.getPublisher() +"','"+boek.getTitle() +"',"+boek.getISBN() +" ,"+boek.getYear() +" ,'"+boek.getLocation() +"')");
            if(result>0)
                System.out.println("Gelukt!");
            else System.out.println("MISLUKT!");
        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("MISLUKT JONGE!");
        }
    }
}


 /*  public Library.Book(int book_ID, String title) {
        this.book_ID = book_ID;
        this.title = title;
            }*/