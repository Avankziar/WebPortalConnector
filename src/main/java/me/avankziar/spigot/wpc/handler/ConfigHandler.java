package main.java.me.avankziar.spigot.wpc.handler;

import main.java.me.avankziar.spigot.wpc.WebPortalConnector;
import main.java.me.avankziar.spigot.wpc.assistance.SHA256Cryption;

public class ConfigHandler
{	
	public boolean isBungee()
	{
		return WebPortalConnector.getPlugin().getYamlHandler().getConfig().getBoolean("Bungee", false);
	}

	public boolean isSendNewPlayerAInfo()
	{
		return WebPortalConnector.getPlugin().getYamlHandler().getConfig().getBoolean("SendNewPlayerAInfo", true);
	}
	
	public String getCryptSalt()
	{
		return WebPortalConnector.getPlugin().getYamlHandler().getConfig().getString("CryptSalt", SHA256Cryption.generateSalt());
	}

	public String getWebURL()
	{
		return WebPortalConnector.getPlugin().getYamlHandler().getConfig().getString("WebURL", "https://yourweb.de./blub.php");
	}

	public boolean isMainServer()
	{
		return WebPortalConnector.getPlugin().getYamlHandler().getConfig().getBoolean("IsMainServer", false);
	}

	public int getJavaTaskCheck()
	{
		return WebPortalConnector.getPlugin().getYamlHandler().getConfig().getInt("JavaTaskCheck", 20);
	}
}
