package main.java.me.avankziar.spigot.wpc.assistance;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import main.java.me.avankziar.spigot.wpc.WebPortalConnector;
import main.java.me.avankziar.spigot.wpc.database.MysqlHandler;
import main.java.me.avankziar.spigot.wpc.objects.WebPortalUser;

public class Utility
{
	private static WebPortalConnector plugin;
	
	public Utility(WebPortalConnector plugin)
	{
		Utility.plugin = plugin;
	}
	
	public static double getNumberFormat(double d)//FIN
	{
		BigDecimal bd = new BigDecimal(d).setScale(1, RoundingMode.HALF_UP);
		double newd = bd.doubleValue();
		return newd;
	}
	
	public static double getNumberFormat(double d, int scale)
	{
		BigDecimal bd = new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP);
		double newd = bd.doubleValue();
		return newd;
	}
	
	public static String convertUUIDToName(String uuid) throws IOException
	{
		String name = null;
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.PLUGINUSER, "player_uuid = ?", uuid))
		{
			name = ((WebPortalUser) plugin.getMysqlHandler().getData(MysqlHandler.Type.PLUGINUSER,
					"player_uuid = ?", uuid)).getName();
			return name;
		}
		return null;
	}
	
	public static String convertNameToUUID(String playername) throws IOException
	{
		String uuid = "";
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.PLUGINUSER, "`player_name` = ?", playername))
		{
			uuid = ((WebPortalUser) plugin.getMysqlHandler().getData(MysqlHandler.Type.PLUGINUSER, "`player_name` = ?", playername)).getUUID();
			return uuid;
		}
		return null;
	}
	
	public boolean existMethod(Class<?> externclass, String method)
	{
	    try 
	    {
	    	Method[] mtds = externclass.getMethods();
	    	for(Method methods : mtds)
	    	{
	    		if(methods.getName().equalsIgnoreCase(method))
	    		{
	    	    	return true;
	    		}
	    	}
	    	return false;
	    } catch (Exception e) 
	    {
	    	return false;
	    }
	}
	
	public static String serialised(LocalDateTime dt)
	{
		String MM = "";
		int month = 0;
		if(dt.getMonthValue()<10)
		{
			MM+=month;
		}
		MM += dt.getMonthValue();
		String dd = "";
		int day = 0;
		if(dt.getDayOfMonth()<10)
		{
			dd+=day;
		}
		dd +=dt.getDayOfMonth();
		String hh = "";
		int hour = 0;
		if(dt.getHour()<10)
		{
			hh+=hour;
		}
		hh += dt.getHour();
		String mm = "";
		int min = 0;
		if(dt.getMinute()<10)
		{
			mm+=min;
		}
		mm += dt.getMinute();
		return dd+"."+MM+"."+dt.getYear()+" "+hh+":"+mm;
	}
	
	public static double round(double value, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();
	    try
	    {
	    	BigDecimal bd = BigDecimal.valueOf(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
	    } catch (NumberFormatException e)
	    {
	    	return 0;
	    }
	}
}
