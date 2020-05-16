package csvimport;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.StringUtils;
import com.opencsv.CSVReader;

public class CSVLoader {

    private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
    private static final String TABLE_REGEX = "\\$\\{table\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    private Connection connection;

    public CSVLoader(Connection connection) {
        this.connection = connection;
    }

    public void loadCSV(String csvFile, String tableName) throws Exception {

        CSVReader csvReader;
        if(this.connection == null) {
            throw new Exception("Not a valid connection.");
        }
        try {
            csvReader = new CSVReader(new FileReader(csvFile));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error occured while executing file. " + e.getMessage());
        }

        String[] headerRow = csvReader.readNext();

        if (headerRow == null) {
            throw new FileNotFoundException( "No columns defined in given CSV file." + "Please check the CSV file format.");
        }

        String questionmarks = StringUtils.repeat("?,", headerRow.length);
        questionmarks = (String) questionmarks.subSequence(0, questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        System.out.println("Query: " + query);

        String[] nextLine;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.connection;
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);

            final int batchSize = 1000;
            int count = 0;
            while ((nextLine = csvReader.readNext()) != null) {

                if (nextLine != null) {
                    int index = 1; //op index 0 staan de titels
                    for (String string : nextLine) {
                        ps.setString(index++, string);
                    }
                    ps.addBatch();
                    System.out.println(ps);
                }
                if (++count % batchSize == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch(); // insert remaining records
            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            throw new Exception("Error occured while loading data from file to database. " + e.getMessage());
        } finally {
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();

            csvReader.close();
        }
    }

}
