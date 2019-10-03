package MainApp.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@RequestMapping(value="/")
	public String Hello()
	{
		return "Hello Wolrd, I am on Azure";
	}
	
	@RequestMapping(value="/data")
	public String data() throws SQLException
	{
		String ans = "DATA...........\n";
		// Connect to database
        String hostName = "your_server.database.windows.net"; // update me
        String dbName = "your_database"; // update me
        String user = "your_username"; // update me
        String password = "your_password"; // update me
        String url = "jdbc:sqlserver://hackathondb2019.database.windows.net:1433;database=Hackathon;user=wizards@hackathondb2019;password=Password1q2w;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();
            System.out.println("Successful connection - Schema: " + schema);

            System.out.println("Query data example:");
            System.out.println("=========================================");

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Persons";

            	Statement statement = connection.createStatement();
            	ResultSet resultSet = statement.executeQuery(selectSql);

                // Print results from select statement
                System.out.println("Top 20 categories:");
                while (resultSet.next())
                {
                	ans+= resultSet.getString(1) + " " + resultSet.getString(2) + " " +resultSet.getString(3);
                    System.out.println(resultSet.getString(1) + " "
                        + resultSet.getString(2));
                }
                
            
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally {
        	if(connection!=null)
        	{
        		connection.close();
        	}
        }
        
        
        return ans;
	}

}
