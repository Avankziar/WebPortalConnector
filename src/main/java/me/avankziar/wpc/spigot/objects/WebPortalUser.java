package main.java.me.avankziar.wpc.spigot.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import main.java.me.avankziar.wpc.spigot.database.MysqlHandable;
import main.java.me.avankziar.wpc.spigot.database.MysqlHandler;

public class WebPortalUser implements MysqlHandable
{
	private String uuid;
	private String name;
	private String password;
	private boolean admin;
	
	public WebPortalUser(){}
	
	public WebPortalUser(String uuid, String name, String password, boolean admin)
	{
		setUUID(uuid);
		setName(name);
		setPassword(password);
		setAdmin(admin);
	}
	
	public String getUUID()
	{
		return uuid;
	}

	public void setUUID(String uuid)
	{
		this.uuid = uuid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isAdmin()
	{
		return admin;
	}

	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}
	
	@Override
	public boolean create(Connection conn, String tablename)
	{
		try
		{
			String sql = "INSERT INTO `" + tablename
					+ "`(`player_uuid`, `player_name`, `pw`, `isadmin`) " 
					+ "VALUES(?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getUUID());
	        ps.setString(2, getName());
	        ps.setString(3, getPassword());
	        ps.setBoolean(4, isAdmin());
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
					+ "` SET `player_uuid` = ?, `player_name` = ?, `pw` = ?, `isadmin` = ?" 
					+ " WHERE "+whereColumn;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getUUID());
	        ps.setString(2, getName());
	        ps.setString(3, getPassword());
	        ps.setBoolean(4, isAdmin());
			int i = 5;
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
				al.add(new WebPortalUser(
	        			rs.getString("player_uuid"),
	        			rs.getString("player_name"),
	        			rs.getString("pw"),
	        			rs.getBoolean("isadmin")));
			}
			return al;
		} catch (SQLException e)
		{
			this.log(Level.WARNING, "SQLException! Could not get a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return new ArrayList<>();
	}
	
	public static ArrayList<WebPortalUser> convert(ArrayList<Object> arrayList)
	{
		ArrayList<WebPortalUser> l = new ArrayList<>();
		for(Object o : arrayList)
		{
			if(o instanceof WebPortalUser)
			{
				l.add((WebPortalUser) o);
			}
		}
		return l;
	}
}