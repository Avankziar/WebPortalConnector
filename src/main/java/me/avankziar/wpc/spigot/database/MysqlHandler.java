package main.java.me.avankziar.wpc.spigot.database;

import java.util.ArrayList;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.database.tables.TableI;
import main.java.me.avankziar.wpc.spigot.database.tables.TableII;
import main.java.me.avankziar.wpc.spigot.database.tables.TableIII;

public class MysqlHandler implements TableI, TableII, TableIII
{
	public enum Type
	{
		PLUGINUSER, PLUGINS, JAVATASK;
	}
	
	private WebPortalConnector plugin;
	public static String tableNameI = "wpcUser";
	public static String tableNameII = "wpcPlugin";
	public static String tableNameIII = "wpcJavaTaskFromPHP";
	
	public MysqlHandler(WebPortalConnector plugin) 
	{
		this.plugin = plugin;
	}
	
	public boolean exist(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.existI(plugin, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.existII(plugin, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.existIII(plugin, whereColumn, whereObject);
		}
		return false;
	}
	
	public boolean create(Type type, Object object)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.createI(plugin, object);
		case PLUGINS:
			return TableII.super.createII(plugin, object);
		case JAVATASK:
			return TableIII.super.createIII(plugin, object);
		}
		return false;
	}
	
	public boolean updateData(Type type, Object object, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.updateDataI(plugin, object, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.updateDataII(plugin, object, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.updateDataIII(plugin, object, whereColumn, whereObject);
		}
		return false;
	}
	
	public Object getData(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getDataI(plugin, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.getDataII(plugin, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.getDataIII(plugin, whereColumn, whereObject);
		}
		return null;
	}
	
	public boolean deleteData(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.deleteDataI(plugin, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.deleteDataII(plugin, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.deleteDataIII(plugin, whereColumn, whereObject);
		}
		return false;
	}
	
	public int lastID(Type type)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.lastIDI(plugin);
		case PLUGINS:
			return TableII.super.lastIDII(plugin);
		case JAVATASK:
			return TableIII.super.lastIDIII(plugin);
		}
		return 0;
	}
	
	public int countWhereID(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.countWhereIDI(plugin, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.countWhereIDII(plugin, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.countWhereIDIII(plugin, whereColumn, whereObject);
		}
		return 0;
	}
	
	public ArrayList<?> getList(Type type, String orderByColumn,
			boolean desc, int start, int quantity, String whereColumn, Object...whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getListI(plugin, orderByColumn, start, quantity, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.getListII(plugin, orderByColumn, start, quantity, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.getListIII(plugin, orderByColumn, start, quantity, whereColumn, whereObject);
		}
		return null;
	}
	
	public ArrayList<?> getTop(Type type, String orderByColumn, boolean desc, int start, int end)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getTopI(plugin, orderByColumn, start, end);
		case PLUGINS:
			return TableII.super.getTopII(plugin, orderByColumn, start, end);
		case JAVATASK:
			return TableIII.super.getTopIII(plugin, orderByColumn, start, end);
		}
		return null;
	}
	
	public ArrayList<?> getAllListAt(Type type, String orderByColumn,
			boolean desc, String whereColumn, Object...whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getAllListAtI(plugin, orderByColumn, whereColumn, whereObject);
		case PLUGINS:
			return TableII.super.getAllListAtII(plugin, orderByColumn, whereColumn, whereObject);
		case JAVATASK:
			return TableIII.super.getAllListAtIII(plugin, orderByColumn, whereColumn, whereObject);
		}
		return null;
	}
}
