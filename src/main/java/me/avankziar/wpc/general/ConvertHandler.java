package main.java.me.avankziar.wpc.general;

import java.util.ArrayList;

import main.java.me.avankziar.wpc.spigot.objects.JavaTask;
import main.java.me.avankziar.wpc.spigot.objects.PluginUser;

public class ConvertHandler
{
	public static ArrayList<PluginUser> convertListI(ArrayList<?> list)
	{
		ArrayList<PluginUser> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof PluginUser)
			{
				el.add((PluginUser) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	public static ArrayList<JavaTask> convertListIII(ArrayList<?> list)
	{
		ArrayList<JavaTask> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof JavaTask)
			{
				el.add((JavaTask) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
}
