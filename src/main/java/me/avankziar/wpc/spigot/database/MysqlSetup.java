package main.java.me.avankziar.wpc.spigot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;

public class MysqlSetup 
{
	private String host;
	private int port;
	private String database;
	private String user;
	private String password;
	private boolean isAutoConnect;
	private boolean isVerifyServerCertificate;
	private boolean isSSLEnabled;
	
	public MysqlSetup(WebPortalConnector plugin, boolean adm, String path)
	{
		if(adm)
		{
			plugin.getLogger().log(Level.INFO, "Using IFH Administration");
		}
		host = adm ? plugin.getAdministration().getHost(path)
				: plugin.getYamlHandler().getConfig().getString("Mysql.Host");
		port = adm ? plugin.getAdministration().getPort(path)
				: plugin.getYamlHandler().getConfig().getInt("Mysql.Port", 3306);
		database = adm ? plugin.getAdministration().getDatabase(path)
				: plugin.getYamlHandler().getConfig().getString("Mysql.DatabaseName");
		user = adm ? plugin.getAdministration().getUsername(path)
				: plugin.getYamlHandler().getConfig().getString("Mysql.User");
		password = adm ? plugin.getAdministration().getPassword(path)
				: plugin.getYamlHandler().getConfig().getString("Mysql.Password");
		isAutoConnect = adm ? plugin.getAdministration().isAutoReconnect(path)
				: plugin.getYamlHandler().getConfig().getBoolean("Mysql.AutoReconnect", true);
		isVerifyServerCertificate = adm ? plugin.getAdministration().isVerifyServerCertificate(path)
				: plugin.getYamlHandler().getConfig().getBoolean("Mysql.VerifyServerCertificate", false);
		isSSLEnabled = adm ? plugin.getAdministration().useSSL(path)
				: plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false);
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
		if(!setupDatabaseIII())
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
			getConnection();
			WebPortalConnector.log.info("Database connection successful!");
			return true;
		} catch(Exception e) 
		{
			WebPortalConnector.log.log(Level.WARNING, "Could not connect to Database!", e);
			return false;
		}		
	}
	
	public Connection getConnection() throws SQLException
	{
		return reConnect();
	}
	
	private Connection reConnect() throws SQLException
	{
		boolean bool = false;
	    try
	    {
	    	// Load new Drivers for papermc
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	bool = true;
	    } catch (Exception e)
	    {
	    	bool = false;
	    } 
	    if (bool == false)
    	{
    		// Load old Drivers for spigot
    		try
    		{
    			Class.forName("com.mysql.jdbc.Driver");
    		}  catch (Exception e) {}
    	}
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        properties.setProperty("autoReconnect", String.valueOf(isAutoConnect));
        properties.setProperty("verifyServerCertificate", String.valueOf(isVerifyServerCertificate));
        properties.setProperty("useSSL", String.valueOf(isSSLEnabled));
        properties.setProperty("requireSSL", String.valueOf(isSSLEnabled));
        //Connect to database
        Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, properties);
        return conn;
	}
	
	private boolean baseSetup(String data) 
	{
		try (Connection conn = getConnection(); PreparedStatement query = conn.prepareStatement(data))
		{
			query.execute();
		} catch (SQLException e) 
		{
			WebPortalConnector.log.log(Level.WARNING, "Could not build data source. Or connection is null", e);
		}
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcParameter` (`id` int AUTO_INCREMENT PRIMARY KEY, `keywords` text, `parameters` text);
	//INSERT INTO `wpcParameter`(`keywords`, `parameters`) VALUES ('salt','XcGAeNgL7YJqJ5r2f5ZTdX3J56Xm5dQ2+r/nlVmIvq81l49NsIeBg1kD')
	
	public boolean setupDatabase0() 
	{
		String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.Type.PARAMETER.getValue()
        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
        		+ " keywords text,"
        		+ " parameters text);";
		baseSetup(data);
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcUser` (`id` int AUTO_INCREMENT PRIMARY KEY, `player_uuid` char(36) NOT NULL UNIQUE, `player_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL, `pw` text, `isadmin` boolean);
	//INSERT INTO `wpcuser`(`player_uuid`, `player_name`, `pw`, `isadmin`) VALUES ('64669849-28f6-45ef-9be3-8eafca765870','Avankziar','8aab21762248d5a216053b9959839664ffdad8186d9207dc0a4f7144e54c0d2b','true')
	//INSERT INTO `wpcuser`(`player_uuid`, `player_name`, `pw`, `isadmin`) VALUES ('1b0d212d-859b-4e11-9cb8-e76380370129','Tragur','eaabbdc862d537c2cef1fa7124083d8a63e336170ae367db683447d990b123e1','false')
	
	public boolean setupDatabaseI() 
	{
		String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.Type.PLUGINUSER.getValue()
        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
        		+ " player_uuid char(36) NOT NULL UNIQUE,"
        		+ " player_name varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,"
        		+ " pw text,"
        		+ " isadmin boolean);";
		baseSetup(data);
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcPlugin` (`id` int AUTO_INCREMENT PRIMARY KEY, `pluginname` text, `aliasname` text, `active` boolean, `pluginversion` text);
	//INSERT INTO `wpcPlugin`(`pluginname`, `aliasname`, `active`, `pluginversion`) VALUES ('AdvancedEconomyPlus', 'Geld-System', '1', `4-2-1`);
	//OLD, '{"TABLEWRAPPER":[{"KEYWORD":"playeraccount","TABLENAME":"secretcrafteconomyplayerdata"},{"KEYWORD":"bankaccount","TABLENAME":"secretcrafteconomybankdata"},{"KEYWORD":"actionlog","TABLENAME":"secretcrafteconomylogger"},{"KEYWORD":"trendlog","TABLENAME":"secretcrafteconomytrend"},{"KEYWORD":"standingorder","TABLENAME":"secretcrafteconomystandigorder"}]}')
	public boolean setupDatabaseII() 
	{
		String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.Type.PLUGINS.getValue()
        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
        		+ " pluginname text,"
        		+ " aliasname text,"
        		+ " active boolean,"
        		+ " pluginversion text);";
		baseSetup(data);
		return true;
	}
	
	//CREATE TABLE IF NOT EXISTS `wpcJavaTaskFromPHP` (`id` int AUTO_INCREMENT PRIMARY KEY, `timestamp_unix` bigint, `pluginname` text, `methodejson` longtext, `isopen` boolean, `wassuccessful` boolean, `errormessage` longtext);
	public boolean setupDatabaseIII() 
	{
		String data = "CREATE TABLE IF NOT EXISTS `" + MysqlHandler.Type.JAVATASK.getValue()
        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
        		+ " timestamp_unix bigint,"
        		+ " pluginname text,"
        		+ " methodejson longtext,"
        		+ " isopen boolean,"
        		+ " wassuccessful boolean,"
        		+ " errormessage longtext);";
		baseSetup(data);
		return true;
	}
}
