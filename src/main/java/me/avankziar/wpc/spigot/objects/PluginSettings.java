package main.java.me.avankziar.wpc.spigot.objects;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.assistance.SHA256Cryption;
import main.java.me.avankziar.wpc.spigot.database.YamlHandler;

public class PluginSettings
{
	private boolean bungee;
	private boolean isMainServer;
	private boolean debug;
	private String cryptsalt;
	private boolean sendNewPlayerAInfo;
	private String webURL;
	private int javaTaskCheck;
	private LinkedHashMap<String, String> commands = new LinkedHashMap<>(); //To save commandstrings
	
	public static PluginSettings settings;
	
	public PluginSettings(){}
	
	public PluginSettings(boolean bungee, boolean isMainServer, boolean debug,
			String cyptSalt, String webURL,
			boolean sendNewPlayerAInfo,
			int javaTaskCheck)
	{
		setBungee(bungee);
		setMainServer(isMainServer);
		setDebug(debug);
		setSendNewPlayerAInfo(sendNewPlayerAInfo);
		setWebURL(webURL);
		setJavaTaskCheck(javaTaskCheck);
	}
	
	public static void initSettings(WebPortalConnector plugin)
	{
		YamlHandler yh = plugin.getYamlHandler();
		boolean bungee = plugin.getYamlHandler().getConfig().getBoolean("Bungee", false);
		boolean isMainServer = plugin.getYamlHandler().getConfig().getBoolean("IsMainServer", false);
		boolean debug = yh.getConfig().getBoolean("DebugMode", false);
		String cryptSalt = yh.getConfig().getString("CryptSalt", SHA256Cryption.generateSalt());
		boolean sendNewPlayerAInfo = yh.getConfig().getBoolean("SendNewPlayerAInfo", true);
		String webURL = yh.getConfig().getString("WebURL", "https://yourweb.de./blub.php");
		int javaTaskCheck = yh.getConfig().getInt("JavaTaskCheck", 20);
		settings = new PluginSettings(bungee, isMainServer, debug,
				cryptSalt, webURL,
				sendNewPlayerAInfo,
				javaTaskCheck);
		plugin.getLogger().info("Plugin Settings init...");
	}
	
	public static void debug(String s)
	{
		if(PluginSettings.settings != null && PluginSettings.settings.isDebug())
		{
			if(WebPortalConnector.getPlugin() != null)
			{
				WebPortalConnector.getPlugin().getLogger().info(s);
			}
		}
	}
	
	public static void debug(Player player, String s)
	{
		if(PluginSettings.settings != null && PluginSettings.settings.isDebug())
		{
			if(player != null)
			{
				player.sendMessage(s);
			}
		}
	}
	
	public boolean isBungee()
	{
		return bungee;
	}

	public void setBungee(boolean bungee)
	{
		this.bungee = bungee;
	}

	public boolean isDebug()
	{
		return debug;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	public String getCommands(String key)
	{
		return commands.get(key);
	}

	public void setCommands(LinkedHashMap<String, String> commands)
	{
		this.commands = commands;
	}
	
	public void addCommands(String key, String commandString)
	{
		if(commands.containsKey(key))
		{
			commands.replace(key, commandString);
		} else
		{
			commands.put(key, commandString);
		}
	}

	public boolean isSendNewPlayerAInfo()
	{
		return sendNewPlayerAInfo;
	}

	public void setSendNewPlayerAInfo(boolean sendNewPlayerAInfo)
	{
		this.sendNewPlayerAInfo = sendNewPlayerAInfo;
	}

	public String getCryptSalt()
	{
		return cryptsalt;
	}

	public void setCryptSalt(String cryptsalt)
	{
		this.cryptsalt = cryptsalt;
	}

	public String getWebURL()
	{
		return webURL;
	}

	public void setWebURL(String webURL)
	{
		this.webURL = webURL;
	}

	public boolean isMainServer()
	{
		return isMainServer;
	}

	public void setMainServer(boolean isMainServer)
	{
		this.isMainServer = isMainServer;
	}

	public int getJavaTaskCheck()
	{
		return javaTaskCheck;
	}

	public void setJavaTaskCheck(int javaTaskCheck)
	{
		this.javaTaskCheck = javaTaskCheck;
	}
}
