package main.java.me.avankziar.general.wpc;

import java.util.ArrayList;

import main.java.me.avankziar.spigot.wpc.objects.JavaTask;
import main.java.me.avankziar.spigot.wpc.objects.WebPortalUser;

public class ConvertHandler
{
	public static ArrayList<WebPortalUser> convertListI(ArrayList<?> list)
	{
		ArrayList<WebPortalUser> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof WebPortalUser)
			{
				el.add((WebPortalUser) o);
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
