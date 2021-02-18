package main.java.me.avankziar.wpc.spigot.permission;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;

public class BypassPermission
{
	public static String REGISTERADMIN = "wpc.bypass.registeradmin";
	
	public static void init(WebPortalConnector plugin)
	{
		REGISTERADMIN = plugin.getYamlHandler().getCommands().getString("Bypass.RegisterAdmin");
	}
}
