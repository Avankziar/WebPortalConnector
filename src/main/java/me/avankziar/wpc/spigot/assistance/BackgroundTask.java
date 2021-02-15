package main.java.me.avankziar.wpc.spigot.assistance;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;

public class BackgroundTask
{
	private static WebPortalConnector plugin;
	
	public BackgroundTask(WebPortalConnector plugin)
	{
		BackgroundTask.plugin = plugin;
		initBackgroundTask();
	}
	
	public boolean initBackgroundTask()
	{
		
		return true;
	}
}
