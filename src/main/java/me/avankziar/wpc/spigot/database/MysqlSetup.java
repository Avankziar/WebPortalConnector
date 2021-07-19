package main.java.me.avankziar.wpc.spigot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;

public class MysqlSetup 
{
	private WebPortalConnector plugin;
	private Connection conn = null;
	
	public MysqlSetup(WebPortalConnector plugin)
	{
		this.plugin = plugin;
		loadMysqlSetup();
	}
	
	public boolean loadMysqlSetup()
	{
		if(!connectToDatabase())
		{
			return false;
		}
		if(!setupDatabase0())
		{
			return false;
		}
		if(!setupDatabaseI())
		{
			return false;
		}
		if(!setupDatabaseII())
		{
			return false;
		}
		return true;
	}
	
	public boolean connectToDatabase() 
	{
		WebPortalConnector.log.info("Connecting to the database...");
		try 
		{
       	 	//Load Drivers
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().getConfig().getString("Mysql.User"));
            properties.setProperty("password", plugin.getYamlHandler().getConfig().getString("Mysql.Password"));
            properties.setProperty("autoReconnect", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.AutoReconnect", true) + "");
            properties.setProperty("verifyServerCertificate", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.VerifyServerCertificate", false) + "");
            properties.setProperty("useSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            properties.setProperty("requireSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().getConfig().getString("Mysql.Host") 
            		+ ":" + plugin.getYamlHandler().getConfig().getInt("Mysql.Port", 3306) + "/" 
            		+ plugin.getYamlHandler().getConfig().getString("Mysql.DatabaseName"), properties);
           
          } catch (ClassNotFoundException e) 
		{
        	  WebPortalConnector.log.severe("Could not locate drivers for mysql! Error: " + e.getMessage());
            return false;
          } catch (SQLException e) 
		{
        	  WebPortalConnector.log.severe("Could not connect to mysql database! Error: " + e.getMessage());
            return false;
          }
		WebPortalConnector.log.info("Database connection successful!");
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcParameter` (`id` int AUTO_INCREMENT PRIMARY KEY, `keywords` text, `parameters` text);
	//INSERT INTO `wpcParameter`(`keywords`, `parameters`) VALUES ('salt','XcGAeNgL7YJqJ5r2f5ZTdX3J56Xm5dQ2+r/nlVmIvq81l49NsIeBg1kD')
	
	public boolean setupDatabase0() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.tableName0
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " keywords text,"
		        		+ " parameters text);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        WebPortalConnector.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcUser` (`id` int AUTO_INCREMENT PRIMARY KEY, `player_uuid` char(36) NOT NULL UNIQUE, `player_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL, `pw` text, `isadmin` boolean);
	//INSERT INTO `wpcuser`(`player_uuid`, `player_name`, `pw`, `isadmin`) VALUES ('64669849-28f6-45ef-9be3-8eafca765870','Avankziar','8aab21762248d5a216053b9959839664ffdad8186d9207dc0a4f7144e54c0d2b','true')
	//INSERT INTO `wpcuser`(`player_uuid`, `player_name`, `pw`, `isadmin`) VALUES ('1b0d212d-859b-4e11-9cb8-e76380370129','Tragur','eaabbdc862d537c2cef1fa7124083d8a63e336170ae367db683447d990b123e1','false')
	
	public boolean setupDatabaseI() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.tableNameI
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " player_uuid char(36) NOT NULL UNIQUE,"
		        		+ " player_name varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,"
		        		+ " pw text,"
		        		+ " isadmin boolean);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        WebPortalConnector.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcPlugin` (`id` int AUTO_INCREMENT PRIMARY KEY, `pluginname` text, `aliasname` text, `activ` boolean, `tablejson` longtext);
	//INSERT INTO `wpcplugin`(`pluginname`, `aliasname`, `activ`, `tablejson`) VALUES ('AdvancedEconomyPlus', 'Geld-System', 'true', '{"TABLEWRAPPER":[{"KEYWORD":"playeraccount","TABLENAME":"secretcrafteconomyplayerdata"},{"KEYWORD":"bankaccount","TABLENAME":"secretcrafteconomybankdata"},{"KEYWORD":"actionlog","TABLENAME":"secretcrafteconomylogger"},{"KEYWORD":"trendlog","TABLENAME":"secretcrafteconomytrend"},{"KEYWORD":"standingorder","TABLENAME":"secretcrafteconomystandigorder"}]}')
	public boolean setupDatabaseII() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.tableNameII
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " pluginname text,"
		        		+ " aliasname text,"
		        		+ " active boolean,"
		        		+ " tablejson longtext);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        WebPortalConnector.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcJavaTaskFromPHP` (`id` int AUTO_INCREMENT PRIMARY KEY, `timestamp_unix` bigint, `pluginname` text, `methodejson` longtext, `isopen` boolean, `wassuccessful` boolean, `errormessage` longtext);
	public boolean setupDatabaseIII() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.tableNameIII
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " timestamp_unix bigint,"
		        		+ " pluginname text,"
		        		+ " methodejson longtext,"
		        		+ " isopen boolean,"
		        		+ " wassuccessful boolean,"
		        		+ " errormessage longtext);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        WebPortalConnector.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public Connection getConnection() 
	{
		checkConnection();
		return conn;
	}
	
	public void checkConnection() 
	{
		try {
			if (conn == null) 
			{
				WebPortalConnector.log.warning("Connection failed. Reconnecting...");
				reConnect();
			}
			if (!conn.isValid(3)) 
			{
				WebPortalConnector.log.warning("Connection is idle or terminated. Reconnecting...");
				reConnect();
			}
			if (conn.isClosed() == true) 
			{
				WebPortalConnector.log.warning("Connection is closed. Reconnecting...");
				reConnect();
			}
		} catch (Exception e) 
		{
			WebPortalConnector.log.severe("Could not reconnect to Database! Error: " + e.getMessage());
		}
	}
	
	public boolean reConnect() 
	{
		try 
		{            
            long start = 0;
			long end = 0;
			
		    start = System.currentTimeMillis();
		    WebPortalConnector.log.info("Attempting to establish a connection to the MySQL server!");
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().getConfig().getString("Mysql.User"));
            properties.setProperty("password", plugin.getYamlHandler().getConfig().getString("Mysql.Password"));
            properties.setProperty("autoReconnect", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.AutoReconnect", true) + "");
            properties.setProperty("verifyServerCertificate", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.VerifyServerCertificate", false) + "");
            properties.setProperty("useSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            properties.setProperty("requireSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().getConfig().getString("Mysql.Host") 
            		+ ":" + plugin.getYamlHandler().getConfig().getInt("Mysql.Port", 3306) + "/" 
            		+ plugin.getYamlHandler().getConfig().getString("Mysql.DatabaseName"), properties);
		    end = System.currentTimeMillis();
		    WebPortalConnector.log.info("Connection to MySQL server established!");
		    WebPortalConnector.log.info("Connection took " + ((end - start)) + "ms!");
            return true;
		} catch (Exception e) 
		{
			WebPortalConnector.log.severe("Error re-connecting to the database! Error: " + e.getMessage());
			return false;
		}
	}
	
	public void closeConnection() 
	{
		try
		{
			WebPortalConnector.log.info("Closing database connection...");
			conn.close();
			conn = null;
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
