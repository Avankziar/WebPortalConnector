package main.java.me.avankziar.wpc.spigot.assistance;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.wpc.general.ConvertHandler;
import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.wpc.spigot.event.JavaPHPTaskEvent;
import main.java.me.avankziar.wpc.spigot.objects.JavaTask;
import main.java.me.avankziar.wpc.spigot.objects.PluginSettings;

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
		runJavaPHPTask();
		return true;
	}
	
	private void runJavaPHPTask()
	{
		if(!PluginSettings.settings.isMainServer())
		{
			return;
		}
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int openTaskCount = plugin.getMysqlHandler().countWhereID(Type.JAVATASK, "`isopen` = ?", true);
				if(openTaskCount <= 0)
				{
					return;
				}
				ArrayList<JavaTask> openTasks = ConvertHandler.convertListIII(
						plugin.getMysqlHandler().getAllListAt(Type.JAVATASK, "`id`", false, "`isopen` = ?", true));
				runJavaPHPTasks(openTasks);
			}
		}.runTaskTimerAsynchronously(plugin, 0L, 20L*PluginSettings.settings.getJavaTaskCheck());
	}
	
	private void runJavaPHPTasks(ArrayList<JavaTask> openTasks)
	{
		if(openTasks == null || openTasks.isEmpty())
		{
			return;
		}
		new BukkitRunnable()
		{
			int i = 0;
			@Override
			public void run()
			{
				if(i >= openTasks.size())
				{
					cancel();
					return;
				}
				JavaTask jt = openTasks.get(i);
				Bukkit.getPluginManager().callEvent(new JavaPHPTaskEvent(jt.getId(), jt.getPluginName(), jt.getTaskJson()));
				i++;
			}
		}.runTaskTimerAsynchronously(plugin, 0L, 2L);
	}
}
