package MainApp.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.util.logging.*; 

@RestController
public class Controller {
	
	 private final static Logger LOGGER =  
             Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	 

	
	@RequestMapping(value="/")
	public String Hello()
	{
		return "Hello Wolrd, I am on Azure";
	}
	
	@RequestMapping(value="/data")
	public String data() throws SQLException
	{
		LogManager lgmngr = LogManager.getLogManager(); 
		  
        Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME); 
		
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
            log.log(Level.INFO, "Successful connection - Schema: " + schema);
            
            log.log(Level.INFO, "Query data example:");
            log.log(Level.INFO, "=========================================");

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Persons";

            	Statement statement = connection.createStatement();
            	ResultSet resultSet = statement.executeQuery(selectSql);

                // Print results from select statement
                System.out.println("Top 20 categories:");
                while (resultSet.next())
                {
                	ans+= resultSet.getString(1) + " " + resultSet.getString(2) + " " +resultSet.getString(3);
                	log.log(Level.INFO,resultSet.getString(1) + " "
                        + resultSet.getString(2));
                }
                
                System.out.println("----------==============-------------------");
            
        }
        
        catch (Exception e) 
        {
        	log.log(Level.INFO, "Error::"+e);
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
