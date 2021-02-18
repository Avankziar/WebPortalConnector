package main.java.me.avankziar.wpc.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.wpc.spigot.event.JavaPHPTaskEndEvent;
import main.java.me.avankziar.wpc.spigot.objects.JavaTask;

public class JavaPHPTaskEndListener implements Listener
{
	private WebPortalConnector plugin;
	
	public JavaPHPTaskEndListener(WebPortalConnector plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJavaPHPTask(JavaPHPTaskEndEvent event)
	{
		int id = event.getMySQLID();
		if(!plugin.getMysqlHandler().exist(Type.JAVATASK, "`id` = ?", id))
		{
			return;
		}
		JavaTask jt = (JavaTask) plugin.getMysqlHandler().getData(Type.JAVATASK, "`id` = ?", id);
		if(!jt.isOpen())
		{
			return;
		}
		jt.setOpen(false);
		jt.setWasSuccessful(event.isWasSuccessful());
		jt.setErrormessage(event.getErrorMessage());
		plugin.getMysqlHandler().updateData(Type.JAVATASK, jt, "`id` = ?", id);
	}
}
