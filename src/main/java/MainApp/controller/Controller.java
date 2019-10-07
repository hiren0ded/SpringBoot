package MainApp.controller;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
	 
	 public static LogManager lgmngr = LogManager.getLogManager(); 
	  
	 public static Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	 
	 private final static String url = "jdbc:sqlserver://hackathondb2019.database.windows.net:1433;database=Hackathon;user=wizards@hackathondb2019;password=Password1q2w;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	 
	 Connection connection = null;
	 
	
	 public Connection getConnection()
	 {
		 if(connection !=null)
			 return connection;
		 else
		 {
			 try {
				connection = DriverManager.getConnection(url);
				log.log(Level.INFO, "======================================");
				log.log(Level.INFO, "Connection Successfull");
				log.log(Level.INFO, "======================================");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 
			 return connection;
		 }
	 }
	 
	
	@RequestMapping(value="/")
	public String Hello()
	{
		connection = getConnection();
		return "Hello Wolrd, I am on Azure";
	}
	
	
	@RequestMapping(value="/closeConnection")
	public String close() {
		
		try {
			connection.close();
			return "Closed Successfully";
		}
		catch(Exception e)
		{
			return e + "Error Occured";
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/login", method= RequestMethod.POST, produces="application/json")
	public String Login(@RequestParam String email, @RequestParam String password, @RequestParam String role) {
		
		JSONObject obj = new JSONObject(); 
		String userid = null;
        try {
            connection = getConnection();
        	
        	// Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM MyUSER where Email=? and Password=? and UserRole=?";

            PreparedStatement statement = connection.prepareStatement(selectSql);  
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, role);
            
            ResultSet resultSet = statement.executeQuery();
            
            System.out.println("OK:"+email+" "+password);
            
            boolean success = false;
            if(resultSet.next())
            {
            	userid = resultSet.getString("UserID");
            	success = true;
            }
            
            
            obj.put("success", success);
            if(success)
            	obj.put("userid", userid);
            
        }
        
        catch (Exception e) 
        {
        	log.log(Level.INFO, "Error::"+e);
            e.printStackTrace();
        }
        
        
        return obj.toString();

	}	
	
	@ResponseBody
	@RequestMapping(value="/seller", method= RequestMethod.POST, produces="application/json")
	public String Seller(@RequestParam String sellerid) {
		
		JSONObject jsonobject = new JSONObject(); 
		JSONObject answer = new JSONObject(); 
        JSONArray jsonarray = new JSONArray();
		String userid = null;
        try {
            connection = getConnection();
        	
        	// Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM seller where SellerID=?";

            PreparedStatement statement = connection.prepareStatement(selectSql);  
            statement.setString(1, sellerid);
            
            ResultSet resultSet = statement.executeQuery();
            
            System.out.println("OK:"+sellerid);
            
            boolean success = false;
            
            
            while(resultSet.next())
            {
            	ResultSetMetaData rsmd = resultSet.getMetaData();
            	int columnsNumber = rsmd.getColumnCount();
            	
            	
            	for(int i=1;i<=columnsNumber;i++)
            	{
            		String name = rsmd.getColumnName(i);
            		jsonobject.put(name, resultSet.getObject(i).toString());
            	}
            	
            	jsonarray.put(jsonobject);
            	jsonobject = new JSONObject();
            }
            
            
            jsonarray.put(jsonobject);
            
            answer.put("Result", jsonarray);
            
            
            
        }
        
        catch (Exception e) 
        {
        	log.log(Level.INFO, "Error::"+e);
            e.printStackTrace();
        }
        
        
        return answer.toString();

	}
	
	@RequestMapping(value="/test")
	public String data() throws SQLException
	{
		connection = getConnection();
		String ans = "Data........";
        try {
            
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
        
        
        return ans;
	}

}
