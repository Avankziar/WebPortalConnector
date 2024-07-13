package main.java.me.avankziar.spigot.wpc.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import main.java.me.avankziar.spigot.wpc.database.MysqlHandable;
import main.java.me.avankziar.spigot.wpc.database.MysqlHandler;

public class PluginObject implements MysqlHandable
{
	private String pluginName;
	private String aliasName;
	private boolean active;
	
	public PluginObject() {}
	
	public PluginObject(String pluginName, String aliasName, boolean active)
	{
		setPluginName(pluginName);
		setAliasName(aliasName);
		setActive(active);
	}

	public String getPluginName()
	{
		return pluginName;
	}

	public void setPluginName(String pluginName)
	{
		this.pluginName = pluginName;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getAliasName()
	{
		return aliasName;
	}

	public void setAliasName(String aliasName)
	{
		this.aliasName = aliasName;
	}
	
	@Override
	public boolean create(Connection conn, String tablename)
	{
		try
		{
			String sql = "INSERT INTO `" + tablename
					+ "`(`pluginname`, `aliasname`, `active`) " 
					+ "VALUES(?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getPluginName());
	        ps.setString(2, getAliasName());
	        ps.setBoolean(3, isActive());
	        int i = ps.executeUpdate();
	        MysqlHandler.addRows(MysqlHandler.QueryType.INSERT, i);
	        return true;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not create a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return false;
	}

	@Override
	public boolean update(Connection conn, String tablename, String whereColumn, Object... whereObject)
	{
		try
		{
			String sql = "UPDATE `" + tablename
					+ "` SET `playername` = ?, `aliasname` = ?, `active` = ?" 
					+ " WHERE "+whereColumn;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getPluginName());
	        ps.setString(2, getAliasName());
	        ps.setBoolean(3, isActive());
			int i = 4;
			for(Object o : whereObject)
			{
				ps.setObject(i, o);
				i++;
			}			
			int u = ps.executeUpdate();
			MysqlHandler.addRows(MysqlHandler.QueryType.UPDATE, u);
			return true;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not update a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return false;
	}

	@Override
	public ArrayList<Object> get(Connection conn, String tablename, String orderby, String limit, String whereColumn, Object... whereObject)
	{
		try
		{
			String sql = "SELECT * FROM `" + tablename
				+ "` WHERE "+whereColumn+" ORDER BY "+orderby+limit;
			PreparedStatement ps = conn.prepareStatement(sql);
			int i = 1;
			for(Object o : whereObject)
			{
				ps.setObject(i, o);
				i++;
			}
			
			ResultSet rs = ps.executeQuery();
			MysqlHandler.addRows(MysqlHandler.QueryType.READ, rs.getMetaData().getColumnCount());
			ArrayList<Object> al = new ArrayList<>();
			while (rs.next()) 
			{
				al.add(new PluginObject(
	        			rs.getString("pluginname"),
	        			rs.getString("aliasname"),
	        			rs.getBoolean("active")));
			}
			return al;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not get a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return new ArrayList<>();
	}
	
	public static ArrayList<PluginObject> convert(ArrayList<Object> arrayList)
	{
		ArrayList<PluginObject> l = new ArrayList<>();
		for(Object o : arrayList)
		{
			if(o instanceof PluginObject)
			{
				l.add((PluginObject) o);
			}
		}
		return l;
	}
}